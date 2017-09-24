package com.example.tutorserver.assignmentresult;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AssignmentResults")
public class AssignmentResult {
	@Id
	private String Id;
	private String studentId;
	private String assignmentName;
	private int attemptCount;
	private List<String> outputFile;

	public AssignmentResult() {
	}
	
	public AssignmentResult(String studentId, String assignmentName, int attemptCount, List<String> outputFile) {
		super();
		this.studentId = studentId;
		this.assignmentName = assignmentName;
		this.attemptCount = attemptCount;
		this.outputFile = outputFile;
	}

	public String getId() {
		return Id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	public int getAttemptCount() {
		return attemptCount;
	}
	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
	}
	public List<String> getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(List<String> outputFile) {
		this.outputFile = outputFile;
	}
}
