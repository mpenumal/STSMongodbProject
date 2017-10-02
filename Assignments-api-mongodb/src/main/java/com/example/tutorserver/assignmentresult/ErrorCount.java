package com.example.tutorserver.assignmentresult;

public class ErrorCount {
	private String message;
	private int count;
	
	public ErrorCount() {
	}
	
	public ErrorCount(String message, int count) {
		super();
		this.message = message;
		this.count = count;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}