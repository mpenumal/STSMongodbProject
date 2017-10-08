package com.example.tutorserver.assignmentresult;

import java.util.ArrayList;
import java.util.List;

public class TutorHelpVisualizationData {
	private String courseName;
	private String assignmentName;
	private List<TutorHelpStudentData> studentData;
	private int studentCount;
	private int successCount;
	private ArrayList<ErrorCount> errorList;
	
	public TutorHelpVisualizationData() {
	}
	
	public TutorHelpVisualizationData(String courseName, String assignmentName, int studentCount, int successCount, 
			List<TutorHelpStudentData> studentData, ArrayList<ErrorCount> errorList) {
		super();
		this.courseName = courseName;
		this.assignmentName = assignmentName;
		this.studentData = studentData;
		this.studentCount = studentCount;
		this.successCount = successCount;
		this.errorList = errorList;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public List<TutorHelpStudentData> getStudentData() {
		return studentData;
	}
	public void setStudentData(List<TutorHelpStudentData> studentData) {
		this.studentData = studentData;
	}

	public ArrayList<ErrorCount> getErrorList() {
		return errorList;
	}
	public void setErrorList(ArrayList<ErrorCount> errorList) {
		this.errorList = errorList;
	}
}
