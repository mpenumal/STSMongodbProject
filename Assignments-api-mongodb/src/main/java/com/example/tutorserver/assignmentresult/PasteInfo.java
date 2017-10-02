package com.example.tutorserver.assignmentresult;

public class PasteInfo {
	private int count;
	private String file;
	private String date;

	public PasteInfo() {
	}
	
	public PasteInfo(int count, String file, String date) {
		super();
		this.count = count;
		this.file = file;
		this.date = date;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}