package com.example.tutorserver.assignment;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Assignments")
public class Assignment {
	@Id
	private String Id;
	private String assignmentName;
	private String courseName;
	private String fileType;
	private String startDate;
	private String endDate;
	private String codeFilePath;
	private String answerFilePath;
	private List<String> codeFile;
	private List<String> answerFile;

	public Assignment() {
	}
	
	public Assignment(String assignmentName, String courseName, String fileType, String startDate, 
			String endDate, String codeFilePath, List<String> codeFile, String answerFilePath, List<String> answerFile) {
		super();
		this.assignmentName = assignmentName;
		this.courseName = courseName;
		this.fileType = fileType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.codeFilePath = codeFilePath;
		this.codeFile = codeFile;
		this.answerFilePath = answerFilePath;
		this.answerFile = answerFile;
	}
	
	public String getId() {
		return Id;
	}

	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public List<String> getCodeFile() {
		return codeFile;
	}
	public void setCodeFile(List<String> codeFile) {
		this.codeFile = codeFile;
	}

	public String getCodeFilePath() {
		return codeFilePath;
	}
	public void setCodeFilePath(String codeFilePath) {
		this.codeFilePath = codeFilePath;
	}

	public String getAnswerFilePath() {
		return answerFilePath;
	}
	public void setAnswerFilePath(String answerFilePath) {
		this.answerFilePath = answerFilePath;
	}

	public List<String> getAnswerFile() {
		return answerFile;
	}
	public void setAnswerFile(List<String> answerFile) {
		this.answerFile = answerFile;
	}
}
