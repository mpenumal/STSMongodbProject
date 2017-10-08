package com.example.tutorserver.assignmentresult;

public class ErrorCount {
	private String message;
	private int count;
	private String type;
	
	public ErrorCount() {
	}
	
	public ErrorCount(String message, int count, String type) {
		super();
		this.message = message;
		this.count = count;
		this.type = type;
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

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}