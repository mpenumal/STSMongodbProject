package com.example.tutorserver.assignmentresult;

import java.util.ArrayList;

public class TutorHelpCourseData {
	private String courseName;
	private String assignmentName;
	private int studentCount;
	private int successCount;
	private long avgTime;
	//message and count - for all students
	private ArrayList<ErrorCount> runtimeErrorList;
	private ArrayList<ErrorCount> compilationErrorList;
	
	public TutorHelpCourseData() {
	}
	
	public TutorHelpCourseData(String courseName, String assignmentName, int studentCount, int successCount,
			long avgTime, ArrayList<ErrorCount> runtimeErrorList, ArrayList<ErrorCount> compilationErrorList) {
		super();
		this.courseName = courseName;
		this.assignmentName = assignmentName;
		this.studentCount = studentCount;
		this.successCount = successCount;
		this.avgTime = avgTime;
		this.runtimeErrorList = runtimeErrorList;
		this.compilationErrorList = compilationErrorList;
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

	public long getAvgTime() {
		return avgTime;
	}
	public void setAvgTime(long avgTime) {
		this.avgTime = avgTime;
	}

	public ArrayList<ErrorCount> getRuntimeErrorList() {
		return runtimeErrorList;
	}
	public void setRuntimeErrorList(ArrayList<ErrorCount> runtimeErrorList) {
		this.runtimeErrorList = runtimeErrorList;
	}

	public ArrayList<ErrorCount> getCompilationErrorList() {
		return compilationErrorList;
	}
	public void setCompilationErrorList(ArrayList<ErrorCount> compilationErrorList) {
		this.compilationErrorList = compilationErrorList;
	}
}
