package com.example.tutorserver.assignment;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AssignmentDbSeeder implements CommandLineRunner {

	@Autowired
	private AssignmentRepository assignmentRepository;
	
	public AssignmentDbSeeder(AssignmentRepository assignmentRepository) {
		super();
		this.assignmentRepository = assignmentRepository;
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		/*
		String assignments_directory = "D:/MS3rdYear/SER593/AssignmentList_Server/HelloServer.java";
		File testFile = new File(assignments_directory);
		List<String> testFileContent = Files.readAllLines(testFile.toPath());
		
		Assignment assignment01 = new Assignment(
				"Assignment01",
				1,
				"Write a program to find Sum of given numbers.",
				testFileContent
				);
		Assignment assignment02 = new Assignment(
				"Assignment02",
				1,
				"Write a program to find Product of given numbers.",
				testFileContent
				);
		Assignment assignment03 = new Assignment(
				"Assignment03",
				2,
				"Write a program to find Factorial of given number.",
				testFileContent
				);
		*/
		
		List<String> defaultContent = Arrays.asList("Empty.");
		
		Assignment assignment00 = new Assignment(
				"Assignment00",
				true,
				"FilePath of the assignment in server.",
				defaultContent
				);
		
		// drop all assignments
		this.assignmentRepository.deleteAll();
		
		// add default assignments to db
		List<Assignment> assignments = Arrays.asList(assignment00);
		this.assignmentRepository.save(assignments);
	}
}
