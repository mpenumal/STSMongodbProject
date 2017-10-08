package com.example.tutorserver.assignment;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssignmentController {
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	public AssignmentController(AssignmentRepository assignmentRepository) {
		super();
		this.assignmentRepository = assignmentRepository;
	}

	@RequestMapping("/assignments")
	public List<Assignment> getAllAssignments() {
		return this.assignmentRepository.findAll();
	}
	
	@RequestMapping("/assignments/{courseName}")
	public List<Assignment> findByCourseName(@PathVariable String courseName) {
		return this.assignmentRepository.findByCourseName(courseName);
	}
	
	@RequestMapping("/assignments/{courseName}/{assignmentName}")
	public Assignment findByCourseNameAndAssignmentName(@PathVariable String courseName, @PathVariable String assignmentName) {
		return this.assignmentRepository.findByCourseNameAndAssignmentName(courseName, assignmentName);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/assignments")
	public void saveAssignment(@RequestBody Assignment assignment) {
		
		// Get codeFile from codeFilePath and assign to 'codeFile' variable
		if (assignment.getCodeFilePath() != null && !assignment.getCodeFilePath().equals("")) {
			try {
				File testFile = new File(assignment.getCodeFilePath());
				assignment.setCodeFile(Files.readAllLines(testFile.toPath()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Get answerFile from answerFilePath and assign to 'answerFile' variable
		if (assignment.getAnswerFilePath() != null && !assignment.getAnswerFilePath().equals("")) {
			try {
				File testFile = new File(assignment.getAnswerFilePath());
				assignment.setAnswerFile(Files.readAllLines(testFile.toPath()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Assignment tempAssignment = this.assignmentRepository.findByCourseNameAndAssignmentName(
																assignment.getCourseName(), 
																assignment.getAssignmentName()
																);
		
		if (tempAssignment != null && tempAssignment.getAssignmentName() != null &&
				!tempAssignment.getAssignmentName().isEmpty() && 
				!tempAssignment.getAssignmentName().equals("Assignment00"))
		{
			tempAssignment.setStartDate(assignment.getStartDate());
			tempAssignment.setEndDate(assignment.getEndDate());
			tempAssignment.setFileType(assignment.getFileType());
			tempAssignment.setCodeFilePath(assignment.getCodeFilePath());
			tempAssignment.setCodeFile(assignment.getCodeFile());
			tempAssignment.setAnswerFilePath(assignment.getAnswerFilePath());
			tempAssignment.setAnswerFile(assignment.getAnswerFile());
			
			this.assignmentRepository.save(tempAssignment);
		}
		else
		{
			this.assignmentRepository.save(assignment);
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/assignments/{courseName}/{assignmentName}")
	public void deleteByName(@PathVariable String courseName, @PathVariable String assignmentName) {
		this.assignmentRepository.deleteByCourseNameAndAssignmentName(courseName, assignmentName);
	}
}
