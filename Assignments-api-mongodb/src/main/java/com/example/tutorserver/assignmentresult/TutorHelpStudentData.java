package com.example.tutorserver.assignmentresult;

import java.util.ArrayList;

public class TutorHelpStudentData {
	private String studentId;
	private String courseName;
	private String assignmentName;
	private boolean isSuccessful;
	private int linesOfCode;
	private int runAttemptCount;
	private int debugAttemptCount;
	private String totalTime;
	private String submissionTime;
	private ArrayList<DurationInfo> durationList;
	private ArrayList<PasteInfo> pasteList;
	private ArrayList<ErrorInfo> runtimeErrorList;
	private ArrayList<ErrorInfo> compilationErrorList;
	
	public TutorHelpStudentData() {
	}
	
	public TutorHelpStudentData(String studentId, String courseName, String assignmentName, boolean isSuccessful,
			int linesOfCode, int runAttemptCount, int debugAttemptCount, String totalTime, String submissionTime,
			ArrayList<DurationInfo> durationList, ArrayList<PasteInfo> pasteList, ArrayList<ErrorInfo> runtimeErrorList,
			ArrayList<ErrorInfo> compilationErrorList) {
		super();
		this.studentId = studentId;
		this.courseName = courseName;
		this.assignmentName = assignmentName;
		this.isSuccessful = isSuccessful;
		this.linesOfCode = linesOfCode;
		this.runAttemptCount = runAttemptCount;
		this.debugAttemptCount = debugAttemptCount;
		this.totalTime = totalTime;
		this.submissionTime = submissionTime;
		this.durationList = durationList;
		this.pasteList = pasteList;
		this.runtimeErrorList = runtimeErrorList;
		this.compilationErrorList = compilationErrorList;
	}

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
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

	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public int getLinesOfCode() {
		return linesOfCode;
	}
	public void setLinesOfCode(int linesOfCode) {
		this.linesOfCode = linesOfCode;
	}

	public int getRunAttemptCount() {
		return runAttemptCount;
	}
	public void setRunAttemptCount(int runAttemptCount) {
		this.runAttemptCount = runAttemptCount;
	}

	public int getDebugAttemptCount() {
		return debugAttemptCount;
	}
	public void setDebugAttemptCount(int debugAttemptCount) {
		this.debugAttemptCount = debugAttemptCount;
	}

	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	
	public String getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(String submissionTime) {
		this.submissionTime = submissionTime;
	}

	public ArrayList<DurationInfo> getDurationList() {
		return durationList;
	}
	public void setDurationList(ArrayList<DurationInfo> durationList) {
		this.durationList = durationList;
	}

	public ArrayList<PasteInfo> getPasteList() {
		return pasteList;
	}
	public void setPasteList(ArrayList<PasteInfo> pasteList) {
		this.pasteList = pasteList;
	}

	public ArrayList<ErrorInfo> getRuntimeErrorList() {
		return runtimeErrorList;
	}
	public void setRuntimeErrorList(ArrayList<ErrorInfo> runtimeErrorList) {
		this.runtimeErrorList = runtimeErrorList;
	}

	public ArrayList<ErrorInfo> getCompilationErrorList() {
		return compilationErrorList;
	}
	public void setCompilationErrorList(ArrayList<ErrorInfo> compilationErrorList) {
		this.compilationErrorList = compilationErrorList;
	}
}
