package com.example.tutorserver.assignmentresult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DurationInfo {
	private String fileStartTime;
	private String fileEndTime;
	private long timeOnFile;
	private String fileName;

	public DurationInfo() {
	}
	
	public DurationInfo(String fileStartTime, String fileEndTime, String fileName) {
		super();
		this.fileStartTime = fileStartTime;
		this.fileEndTime = fileEndTime;
		this.fileName = fileName;
	}
	
	public String getFileStartTime() {
		return fileStartTime;
	}
	public void setFileStartTime(String fileStartTime) {
		this.fileStartTime = fileStartTime;
	}
	
	public String getFileEndTime() {
		return fileEndTime;
	}
	public void setFileEndTime(String fileEndTime) {
		this.fileEndTime = fileEndTime;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getTimeOnFile() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDate = sdf.parse(this.fileStartTime);
		Date endDate = sdf.parse(this.fileEndTime);
		
		long seconds = (endDate.getTime() - startDate.getTime())/1000;
		
	    this.timeOnFile = seconds; 
		return this.timeOnFile;
	}
}