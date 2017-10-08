package com.example.tutorserver.assignmentresult;

public class ErrorInfo {
	private String message;
	private String file;
	private int lineNumber;
	private int count;
	
	public ErrorInfo() {
	}
	
	public ErrorInfo(String message, String file, int lineNumber, int count) {
		super();
		this.message = message;
		this.file = file;
		this.lineNumber = lineNumber;
		this.count = count;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
