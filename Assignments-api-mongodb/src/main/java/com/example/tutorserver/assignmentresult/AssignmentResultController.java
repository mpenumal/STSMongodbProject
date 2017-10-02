package com.example.tutorserver.assignmentresult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssignmentResultController {
	
	@Autowired
	private AssignmentResultRepository assignmentResultRepository;
	private static String openByDefault = "Open_By_Default";
	private static String editorActivatedAction = "Editor_Activate_Action";
	private static String editorDeactivatedAction = "Editor_Deactivate_Action";
	private static String pasteAction = "Paste_Action";
	private static String runAction = "Run_Action";
	private static String debugAction = "Debug_Action";
	private static String compilationError = "Compilation_Error";
	private static String runtimeError = "Runtime_Error";
	
	public AssignmentResultController(AssignmentResultRepository assignmentResultRepository) {
		super();
		this.assignmentResultRepository = assignmentResultRepository;
	}

	@RequestMapping("/assignmentResults")
	public List<AssignmentResult> getAllAssignmentResults() {
		return this.assignmentResultRepository.findAll();
	}
	
	@RequestMapping("/assignmentResults/{studentId}")
	public List<AssignmentResult> findByStudentId(@PathVariable String studentId) {
		return this.assignmentResultRepository.findByStudentId(studentId);
	}
	
	@RequestMapping("/assignmentResults/studentSpecific/{courseName}/{studentId}")
	public List<AssignmentResult> findByCourseNameAndStudentId(@PathVariable String courseName,@PathVariable String studentId) {
		return this.assignmentResultRepository.findByCourseNameAndStudentId(courseName, studentId);
	}
	
	@RequestMapping("/assignmentResults/assignmentSpecific/{assignmentName}/{courseName}")
	public List<AssignmentResult> findByAssignmentNameAndCourseName(@PathVariable String assignmentName, @PathVariable String courseName) {
		return this.assignmentResultRepository.findByAssignmentNameAndCourseName(assignmentName, courseName);
	}
	
	@RequestMapping("/assignmentResults/{assignmentName}/{courseName}/{studentId}")
	public AssignmentResult findByAssignmentNameAndCourseNameAndStudentId(@PathVariable String assignmentName, @PathVariable String courseName, @PathVariable String studentId) {
		return this.assignmentResultRepository.findByAssignmentNameAndCourseNameAndStudentId(assignmentName, courseName, studentId);
	}
	
	@RequestMapping("/assignmentResults/studentView/{studentId}/{courseName}")
	public List<TutorHelpStudentData> getStudentView(@PathVariable String studentId, @PathVariable String courseName) {
		List<AssignmentResult> arList = this.assignmentResultRepository.findByCourseNameAndStudentId(courseName, studentId);
		List<TutorHelpStudentData> studentDataList = new ArrayList<>();
		if (arList != null && !arList.isEmpty()) {
			for (AssignmentResult ar : arList) {
				TutorHelpStudentData studentData = ExtractStudentDataFromAssignmentResult(ar.getOutputFile());
				studentData.setStudentId(ar.getStudentId());
				studentData.setCourseName(ar.getCourseName());
				studentData.setAssignmentName(ar.getAssignmentName());
				studentDataList.add(studentData);
			}
		}
		return studentDataList;
	}
	
	private TutorHelpStudentData ExtractStudentDataFromAssignmentResult(List<String> output) {
		TutorHelpStudentData studentData = new TutorHelpStudentData();
		
		String prevAction = "";
		String prevDateTime = "";
		String prevFileName = "";
		HashMap<String, String> activeEditorList = new HashMap<>();
		
		for (int i = 0; i < output.size(); i++) {
			if (output.get(i).equals(runAction)) {
				studentData.setLinesOfCode(Integer.parseInt(output.get(i+1).split(Pattern.quote("|"))[1]));
				studentData.setRunAttemptCount(studentData.getRunAttemptCount() + 1);
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = runAction;
				i += 2;
			}
			else if (output.get(i).equals(debugAction)) {
				studentData.setDebugAttemptCount(studentData.getDebugAttemptCount() + 1);
				prevDateTime = output.get(i+1).split(Pattern.quote("|"))[1];
				prevAction = debugAction;
				i += 1;
			}
			else if (output.get(i).equals(openByDefault)) {				
				prevFileName = output.get(i+1).split(Pattern.quote("|"))[1];
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = openByDefault;
				
				if (!activeEditorList.containsKey(prevFileName)) {
					activeEditorList.put(prevFileName, prevDateTime);
				}
				
				i += 2;
			}
			else if (output.get(i).equals(pasteAction)) {
				ArrayList<PasteInfo> piList = studentData.getPasteList();
				if (piList == null || piList.isEmpty()) {
					piList = new ArrayList<>();
				}
				PasteInfo pi = new PasteInfo();
				pi.setFile(output.get(i+1).split(Pattern.quote("|"))[1]);
				System.out.println("pastedLinesText:"+ output.get(i+2).split(Pattern.quote("|"))[0]);
				System.out.println("pastedLinesValue:"+ output.get(i+2).split(Pattern.quote("|"))[1]);
				pi.setCount(Integer.parseInt(output.get(i+2).split(Pattern.quote("|"))[1]));
				pi.setDate(output.get(i+3).split(Pattern.quote("|"))[1]);
				
				piList.add(pi);
				
				studentData.setPasteList(piList);
				
				prevFileName = pi.getFile();
				prevDateTime = pi.getDate();
				prevAction = pasteAction;
				i += 3;
			}
			else if (output.get(i).equals(editorActivatedAction)) {
				prevFileName = output.get(i+1).split(Pattern.quote("|"))[1];
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = editorActivatedAction;
				
				if (!activeEditorList.containsKey(prevFileName)) {
					activeEditorList.put(prevFileName, prevDateTime);
				}
				
				i += 2;
			}
			else if (output.get(i).equals(editorDeactivatedAction)) {
				ArrayList<DurationInfo> diList = studentData.getDurationList();
				if (diList == null || diList.isEmpty()) {
					diList = new ArrayList<>();
				}
				DurationInfo di = new DurationInfo();
				di.setFileName(output.get(i+1).split(Pattern.quote("|"))[1]);
				
				if (activeEditorList.containsKey(di.getFileName())) {
					String startTime = activeEditorList.get(di.getFileName());
					activeEditorList.remove(di.getFileName());
					
					di.setFileStartTime(startTime);
					di.setFileEndTime(output.get(i+2).split(Pattern.quote("|"))[1]);
					diList.add(di);
					studentData.setDurationList(diList);
				}
				
				prevFileName = di.getFileName();
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = editorDeactivatedAction;
				i += 2;
			}
			else if (output.get(i).equals(compilationError)) {
				ArrayList<ErrorInfo> ceList = studentData.getCompilationErrorList();
				if (ceList == null || ceList.isEmpty()) {
					ceList = new ArrayList<>();
				}
				ErrorInfo ce = new ErrorInfo();
				ce.setMessage(output.get(i+1).split(Pattern.quote("|"))[1]);
				ce.setFile(output.get(i+2).split(Pattern.quote("|"))[1]);
				ce.setLineNumber(output.get(i+3).split(Pattern.quote("|"))[1]);
				ceList.add(ce);
				studentData.setCompilationErrorList(ceList);
				
				prevAction = compilationError;
				i += 5;
			}
			else if (output.get(i).startsWith("Exception") && !output.get(i).contains("compilation")) {
				ArrayList<ErrorInfo> reList = studentData.getRuntimeErrorList();
				if (reList == null || reList.isEmpty()) {
					reList = new ArrayList<>();
				}
				ErrorInfo re = new ErrorInfo();
				re.setMessage(output.get(i) + output.get(i+1));
				reList.add(re);
				studentData.setRuntimeErrorList(reList);
				
				prevAction = runtimeError;
				i += 1;
			}
		}
		
		if (!prevAction.equals(runAction)) {
			studentData.setSuccessful(false);
		}
		else {
			//TODO: Calculate TotalTime - add prevdateTime if prevAction=RUN/DEBUG
			ArrayList<DurationInfo> diList = studentData.getDurationList();
			if (diList == null || diList.isEmpty()) {
				diList = new ArrayList<>();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			long seconds = 0, minutes = 0, hours = 0;
			for (int i = 0; i < diList.size(); i++) {
				try {
					Date startDate = sdf.parse(diList.get(i).getFileStartTime());
					Date endDate = sdf.parse(diList.get(i).getFileEndTime());
					
					seconds += (endDate.getTime() - startDate.getTime())/1000;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (seconds > 0) {
				hours = seconds / 3600;
				minutes = (seconds % 3600) / 60;
				seconds = seconds % 60;
			}
			studentData.setTotalTime(twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds));
		}
		
		return studentData;
	}
	
	private String twoDigitString(long number) {
	    if (number == 0) {
	        return "00";
	    }
	    if (number / 10 == 0) {
	        return "0" + number;
	    }
	    return String.valueOf(number);
	}
	
	@RequestMapping("/assignmentResults/courseView/{studentId}/{courseName}")
	public List<TutorHelpCourseData> getCourseView(String studentId, String courseName) {
		List<TutorHelpCourseData> courseDataList = new ArrayList<>();
		
		return courseDataList;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/assignmentResults")
	public void saveAssignmentResult(@RequestBody AssignmentResult assignmentResult) {
		AssignmentResult tempResult = this.assignmentResultRepository.findByAssignmentNameAndCourseNameAndStudentId(
																assignmentResult.getAssignmentName(), 
																assignmentResult.getCourseName(), 
																assignmentResult.getStudentId()
																);
		
		if (tempResult != null && tempResult.getStudentId()!=null &&
				!tempResult.getStudentId().isEmpty() && !tempResult.getStudentId().equals("0000000000"))
		{
			List<String> tempOutput = tempResult.getOutputFile();
			tempOutput.add("");
			tempOutput.addAll(assignmentResult.getOutputFile());
			tempResult.setOutputFile(tempOutput);
			this.assignmentResultRepository.save(tempResult);
		}
		else
		{
			this.assignmentResultRepository.save(assignmentResult);
		}
	}
}
