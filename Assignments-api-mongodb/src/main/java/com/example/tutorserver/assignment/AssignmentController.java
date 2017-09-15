package com.example.tutorserver.assignment;

import java.io.File;
import java.io.IOException;
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
	
	@RequestMapping("/assignments/{name}")
	public Assignment findByName(@PathVariable String name) {
		return this.assignmentRepository.findByName(name);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/assignments")
	public void saveAssignment(@RequestBody Assignment assignment) {
		
		// Get file from filePath and assign to codeFile
		File testFile = new File(assignment.getFilePath());
		try {
			assignment.setCodeFile(Files.readAllLines(testFile.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assignment tempCodeFile = this.assignmentRepository.findByName(assignment.getName());
		
		if (tempCodeFile != null && tempCodeFile.getName() != null &&
				!tempCodeFile.getName().isEmpty() && !tempCodeFile.getName().equals("Assignment00"))
		{
			tempCodeFile.setCodeFile(assignment.getCodeFile());
			this.assignmentRepository.save(tempCodeFile);
		}
		else
		{
			this.assignmentRepository.save(assignment);
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/assignments/{name}")
	public void deleteByName(@PathVariable String name) {
		this.assignmentRepository.deleteByName(name);
	}
}
