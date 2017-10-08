package com.example.tutorserver.assignmentresult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutorserver.assignment.Assignment;
import com.example.tutorserver.assignment.AssignmentRepository;

@RestController
public class AssignmentResultController {
	
	@Autowired
	private AssignmentResultRepository assignmentResultRepository;
	@Autowired
	private AssignmentRepository assignmentRepository;
	private static String openByDefault = "Open_By_Default";
	private static String editorVisibleAction = "Editor_Visible_Action";
	private static String editorClosedAction = "Editor_Closed_Action";
	private static String eclipseClosedAction = "Eclipse_Closed";
	private static String pasteAction = "Paste_Action";
	private static String runAction = "Run_Action";
	private static String debugAction = "Debug_Action";
	private static String compilationError = "Compilation_Error";
	private static String runtimeError = "Runtime_Error";
	private static String answer = "Answer";
	
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
	
	@CrossOrigin
	@RequestMapping("/visualizationView/{courseName}/{assignmentName}")
	public TutorHelpVisualizationData getVisualizationView(@PathVariable String assignmentName, @PathVariable String courseName) {
		TutorHelpVisualizationData visualData = new TutorHelpVisualizationData();
		visualData.setAssignmentName(assignmentName);
		visualData.setCourseName(courseName);
		int successCount = 0;
		ArrayList<ErrorCount> errorList = new ArrayList<>();
		
		List<TutorHelpStudentData> studentDataList = new ArrayList<>();
		List<AssignmentResult> arList = this.assignmentResultRepository.findByAssignmentNameAndCourseName(assignmentName, courseName);
		if (arList != null && !arList.isEmpty()) {
			for (AssignmentResult ar : arList) {
				TutorHelpStudentData studentData = extractStudentDataFromAssignmentResult(ar.getOutputFile(), ar.getCourseName(), ar.getAssignmentName());
				studentData.setStudentId(ar.getStudentId());
				studentData.setCourseName(ar.getCourseName());
				studentData.setAssignmentName(ar.getAssignmentName());
				successCount = studentData.isSuccessful() ? successCount+1 : successCount;
				
				ArrayList<ErrorInfo> compErrors = studentData.getCompilationErrorList();
				if (compErrors != null && !compErrors.isEmpty() && compErrors.size() > 0) {
					for (ErrorInfo err : compErrors) {
						ErrorCount cmpE = new ErrorCount();
						cmpE.setMessage(err.getMessage());
						cmpE.setCount(err.getCount());
						cmpE.setType("compilation");
						
						boolean matched = false;
						for (int i = 0; i < errorList.size(); i++) {
							if (errorList.get(i).getMessage().equals(cmpE.getMessage()) &&
									errorList.get(i).getType().equals("compilation")) {
								errorList.get(i).setCount(errorList.get(i).getCount()+1);
								matched = true;
								break;
							}
						}
						if (!matched) {
							errorList.add(cmpE);
						}
					}
				}
				ArrayList<ErrorInfo> runErrors = studentData.getRuntimeErrorList();
				if (runErrors != null && !runErrors.isEmpty() && runErrors.size() > 0) {
					for (ErrorInfo err : runErrors) {
						ErrorCount runE = new ErrorCount();
						String msg = err.getMessage();
						
						if (msg.contains(":")) {
							msg = msg.split(":")[0];
							if (msg.contains(" ")) {
								String[] msgList = msg.split(" "); 
								msg = msgList[msgList.length - 1];
							}
						}
						
						runE.setMessage(msg);
						runE.setCount(err.getCount());
						runE.setType("runtime");
						
						boolean matched = false;
						for (int i = 0; i < errorList.size(); i++) {
							if (errorList.get(i).getMessage().equals(runE.getMessage()) &&
									errorList.get(i).getType().equals("runtime")) {
								errorList.get(i).setCount(errorList.get(i).getCount()+1);
								matched = true;
								break;
							}
						}
						if (!matched) {
							errorList.add(runE);
						}
					}
				}
				studentDataList.add(studentData);
			}
		}
		
		visualData.setStudentData(studentDataList);
		visualData.setStudentCount(studentDataList.size());
		visualData.setSuccessCount(successCount);
		visualData.setErrorList(errorList);
		
		return visualData;
	}
	
	@CrossOrigin
	@RequestMapping("/studentView/{courseName}/{studentId}")
	public List<TutorHelpStudentData> getStudentView(@PathVariable String studentId, @PathVariable String courseName) {
		List<AssignmentResult> arList = this.assignmentResultRepository.findByCourseNameAndStudentId(courseName, studentId);
		List<TutorHelpStudentData> studentDataList = new ArrayList<>();
		if (arList != null && !arList.isEmpty()) {
			for (AssignmentResult ar : arList) {
				TutorHelpStudentData studentData = extractStudentDataFromAssignmentResult(ar.getOutputFile(),  ar.getCourseName(), ar.getAssignmentName());
				studentData.setStudentId(ar.getStudentId());
				studentData.setCourseName(ar.getCourseName());
				studentData.setAssignmentName(ar.getAssignmentName());
				studentDataList.add(studentData);
			}
		}
		return studentDataList;
	}
	
	private TutorHelpStudentData extractStudentDataFromAssignmentResult(List<String> output, String courseName, String assignmentName) {
		TutorHelpStudentData studentData = new TutorHelpStudentData();
		
		Assignment assignment = this.assignmentRepository.findByCourseNameAndAssignmentName(courseName, assignmentName);
		
		String prevAction = "";
		String prevDateTime = "";
		String prevFileName = "";
		HashMap<String, String> activeEditorList = new HashMap<>();
		
		for (int i = 0; i < output.size(); i++) {
			if (output.get(i).equals(openByDefault)) {				
				prevFileName = output.get(i+1).split(Pattern.quote("|"))[1];
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = openByDefault;
				
				if (!activeEditorList.containsKey(prevFileName)) {
					activeEditorList.put(prevFileName, prevDateTime);
				}
				
				i += 2;
			}
			else if (output.get(i).equals(editorVisibleAction)) {
				ArrayList<DurationInfo> diList = studentData.getDurationList();
				if (diList == null || diList.isEmpty()) {
					diList = new ArrayList<>();
				}
				
				prevFileName = output.get(i+1).split(Pattern.quote("|"))[1];
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = editorVisibleAction;
				
				if (!activeEditorList.containsKey(prevFileName)) {
					for ( String fileName : activeEditorList.keySet()) {
						DurationInfo di = new DurationInfo();
						di.setFileName(fileName);
						String startTime = activeEditorList.get(fileName);
						di.setFileStartTime(startTime);
						di.setFileEndTime(prevDateTime);
						diList.add(di);
						studentData.setDurationList(diList);
					}
					
					activeEditorList = new HashMap<>();
					activeEditorList.put(prevFileName, prevDateTime);
				}	
				i += 2;
			}
			else if (output.get(i).equals(editorClosedAction)) {
				ArrayList<DurationInfo> diList = studentData.getDurationList();
				if (diList == null || diList.isEmpty()) {
					diList = new ArrayList<>();
				}
				
				prevFileName = output.get(i+1).split(Pattern.quote("|"))[1];
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = editorClosedAction;
				
				if (activeEditorList.containsKey(prevFileName)) {
					for ( String fileName : activeEditorList.keySet()) {
						DurationInfo di = new DurationInfo();
						di.setFileName(fileName);
						String startTime = activeEditorList.get(fileName);
						di.setFileStartTime(startTime);
						di.setFileEndTime(prevDateTime);
						diList.add(di);
						studentData.setDurationList(diList);
					}
					
					activeEditorList = new HashMap<>();
					activeEditorList.put(prevFileName, prevDateTime);
				}	
				i += 2;
			}
			else if (output.get(i).equals(eclipseClosedAction)) {
				ArrayList<DurationInfo> diList = studentData.getDurationList();
				if (diList == null || diList.isEmpty()) {
					diList = new ArrayList<>();
				}
				
				prevFileName = output.get(i+1).split(Pattern.quote("|"))[1];
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = eclipseClosedAction;
				
				if (activeEditorList.containsKey(prevFileName)) {
					for ( String fileName : activeEditorList.keySet()) {
						DurationInfo di = new DurationInfo();
						di.setFileName(fileName);
						String startTime = activeEditorList.get(fileName);
						di.setFileStartTime(startTime);
						di.setFileEndTime(prevDateTime);
						diList.add(di);
						studentData.setDurationList(diList);
					}
					
					activeEditorList = new HashMap<>();
					activeEditorList.put(prevFileName, prevDateTime);
				}	
				i += 2;
			}
			else if (output.get(i).equals(runAction)) {
				studentData.setLinesOfCode(Integer.parseInt(output.get(i+1).split(Pattern.quote("|"))[1]));
				studentData.setRunAttemptCount(studentData.getRunAttemptCount() + 1);
				prevDateTime = output.get(i+2).split(Pattern.quote("|"))[1];
				prevAction = runAction;
				i += 2;
				if (output.get(i+1) != null && !output.get(i+1).equals("") && 
						!output.get(i+1).equals(assignment.getAnswerFile().get(0))) {
					studentData.setSuccessful(false);
				}
			}
			else if (output.get(i).equals(debugAction)) {
				studentData.setDebugAttemptCount(studentData.getDebugAttemptCount() + 1);
				prevDateTime = output.get(i+1).split(Pattern.quote("|"))[1];
				prevAction = debugAction;
				i += 1;
				if (output.get(i+1) != null && !output.get(i+1).equals("") && 
						!output.get(i+1).equals(assignment.getAnswerFile().get(0))) {
					studentData.setSuccessful(false);
				}
			}
			else if (output.get(i).equals(pasteAction)) {
				ArrayList<PasteInfo> piList = studentData.getPasteList();
				if (piList == null || piList.isEmpty()) {
					piList = new ArrayList<>();
				}
				PasteInfo pi = new PasteInfo();
				pi.setFile(output.get(i+1).split(Pattern.quote("|"))[1]);
				pi.setCount(Integer.parseInt(output.get(i+2).split(Pattern.quote("|"))[1]));
				pi.setDate(output.get(i+3).split(Pattern.quote("|"))[1]);
				
				piList.add(pi);
				
				studentData.setPasteList(piList);
				
				prevFileName = pi.getFile();
				prevDateTime = pi.getDate();
				prevAction = pasteAction;
				i += 3;
			}
			else if (output.get(i).equals(compilationError)) {
				ArrayList<ErrorInfo> ceList = studentData.getCompilationErrorList();
				if (ceList == null || ceList.isEmpty()) {
					ceList = new ArrayList<>();
				}
				ErrorInfo ce = new ErrorInfo();
				ce.setMessage(output.get(i+1).split(Pattern.quote("|"))[1]);
				ce.setFile(output.get(i+2).split(Pattern.quote("|"))[1]);
				ce.setLineNumber(Integer.parseInt(output.get(i+3).split(Pattern.quote("|"))[1]));
				ce.setCount(1);
				
				int count = 0;
				for (int k = 0; k < ceList.size(); k++) {
					if (ceList.get(k).getMessage().equals(ce.getMessage()) &&
							ceList.get(k).getFile().equals(ce.getFile()) && 
							ceList.get(k).getLineNumber() == ce.getLineNumber()) {
						count = ceList.get(k).getCount() + 1;
						ceList.get(k).setCount(count+1);
						break;
					}
				}
				if (count == 0) {
					ceList.add(ce);
				}
				studentData.setCompilationErrorList(ceList);
				studentData.setSuccessful(false);
				
				prevAction = compilationError;
				i += 5;
			}
			else if (output.get(i).startsWith("Exception") && !output.get(i).contains("compilation")) {
				ArrayList<ErrorInfo> reList = studentData.getRuntimeErrorList();
				if (reList == null || reList.isEmpty()) {
					reList = new ArrayList<>();
				}
				ErrorInfo re = new ErrorInfo();
				re.setMessage(output.get(i));
				re.setCount(1);
				
				int count = 0;
				for (int k = 0; k < reList.size(); k++) {
					if (reList.get(k).getMessage().equals(re.getMessage())) {
						count = reList.get(k).getCount() + 1;
						reList.get(k).setCount(count+1);
						break;
					}
				}
				if (count == 0) {
					reList.add(re);
				}
				studentData.setRuntimeErrorList(reList);
				studentData.setSuccessful(false);
				
				prevAction = runtimeError;
				i += 1;
			}
			else if (output.get(i) != null && !output.get(i).equals("") && 
					output.get(i).equals(assignment.getAnswerFile().get(0))) {
				boolean matched = false;
				int k = i;
				for (int j = 0; j < assignment.getAnswerFile().size(); j++) {
					if (assignment.getAnswerFile().get(j).equals("")) {
						continue;
					}
					if (output.size() <= k) {
						matched = false;
						break;
					}
					if (output.get(k).equals("")) {
						k += 1;
						continue;
					}
					if (!assignment.getAnswerFile().get(j).equals(output.get(k))) {
						matched = false;
						break;
					}
					else {
						matched = true;
						k += 1;
					}
				}
				studentData.setSuccessful(matched);
				
				prevAction = answer;
				i = k;
			}
		}
		
		// Setting the final DurationInfo
		ArrayList<DurationInfo> diList1 = studentData.getDurationList();
		if (diList1 == null || diList1.isEmpty()) {
			diList1 = new ArrayList<>();
		}
		
		if (activeEditorList != null && !activeEditorList.isEmpty()) {
			for ( String fileName : activeEditorList.keySet()) {
				DurationInfo di = new DurationInfo();
				di.setFileName(fileName);
				String startTime = activeEditorList.get(fileName);
				di.setFileStartTime(startTime);
				di.setFileEndTime(prevDateTime);
				diList1.add(di);
				studentData.setDurationList(diList1);
			}
			
			activeEditorList = new HashMap<>();
		}

		// Calculate TotalTime - add prevdateTime if prevAction=RUN/DEBUG
		ArrayList<DurationInfo> diList2 = studentData.getDurationList();
		if (diList2 == null || diList2.isEmpty()) {
			diList2 = new ArrayList<>();
		}
		long seconds = 0, minutes = 0, hours = 0;
		for (int i = 0; i < diList2.size(); i++) {
			try {
				seconds += diList2.get(i).getTimeOnFile();
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
		
		// set the submissionTime
		studentData.setSubmissionTime(prevDateTime);
		
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
	public List<TutorHelpVisualizationData> getCourseView(String studentId, String courseName) {
		List<TutorHelpVisualizationData> courseDataList = new ArrayList<>();
		
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
