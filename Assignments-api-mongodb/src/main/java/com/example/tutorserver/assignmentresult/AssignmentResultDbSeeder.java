package com.example.tutorserver.assignmentresult;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AssignmentResultDbSeeder implements CommandLineRunner {

	@Autowired
	private AssignmentResultRepository assignmentResultRepository;
	
	public AssignmentResultDbSeeder(AssignmentResultRepository assignmentResultRepository) {
		super();
		this.assignmentResultRepository = assignmentResultRepository;
	}

	@Override
	public void run(String... arg0) throws Exception {

		List<String> defaultOutput = Arrays.asList("Empty.");
		// TODO Auto-generated method stub
		AssignmentResult assignRes00 = new AssignmentResult(
				"0000000000",
				"CSE000",
				"Assignment00",
				defaultOutput
				);
		
		// drop all assignments
		//this.assignmentResultRepository.deleteAll();
		
		// add default assignments to db
		List<AssignmentResult> assignmentResults = Arrays.asList(assignRes00);
		this.assignmentResultRepository.save(assignmentResults);
	}
}
