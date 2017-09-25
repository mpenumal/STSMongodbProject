package com.example.tutorserver.assignmentresult;

import java.util.List;

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
	
	public AssignmentResultController(AssignmentResultRepository assignmentResultRepository) {
		super();
		this.assignmentResultRepository = assignmentResultRepository;
	}

	@RequestMapping("/assignmentResults")
	public List<AssignmentResult> getAllAssignmentResults() {
		return this.assignmentResultRepository.findAll();
	}
	
	@RequestMapping("/assignmentResults/studentSpecific/{studentId}")
	public List<AssignmentResult> findByStudentId(@PathVariable String studentId) {
		return this.assignmentResultRepository.findByStudentId(studentId);
	}
	
	@RequestMapping("/assignmentResults/assignmentSpecific/{assignmentName}")
	public List<AssignmentResult> findByAssignmentName(@PathVariable String assignmentName) {
		return this.assignmentResultRepository.findByAssignmentName(assignmentName);
	}
	
	@RequestMapping("/assignmentResults/{assignmentName}/{studentId}")
	public AssignmentResult findByAssignmentNameAndStudentId(@PathVariable String assignmentName,@PathVariable String studentId) {
		return this.assignmentResultRepository.findByAssignmentNameAndStudentId(assignmentName, studentId);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/assignmentResults")
	public void saveAssignmentResult(@RequestBody AssignmentResult assignmentResult) {
		AssignmentResult tempResult = this.assignmentResultRepository.findByAssignmentNameAndStudentId(
																assignmentResult.getAssignmentName(), 
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
