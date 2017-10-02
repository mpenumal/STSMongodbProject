package com.example.tutorserver.assignmentresult;

public class ErrorInfo {
	private String message;
	private String file;
	private String lineNumber;
	
	public ErrorInfo() {
	}
	
	public ErrorInfo(String message, String file, String lineNumber) {
		super();
		this.message = message;
		this.file = file;
		this.lineNumber = lineNumber;
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

	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
}
