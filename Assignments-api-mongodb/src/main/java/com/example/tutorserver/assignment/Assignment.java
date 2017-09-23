package com.example.tutorserver.assignment;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Assignments")
public class Assignment {
	@Id
	private String Id;
	private String name;
	private boolean show;
	private String filePath;
	private List<String> codeFile;

	public Assignment() {
	}
	
	public Assignment(String name, boolean show, String filePath, List<String> codeFile) {
		super();
		this.name = name;
		this.show = show;
		this.filePath = filePath;
		this.codeFile = codeFile;
	}
	
	public String getId() {
		return Id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<String> getCodeFile() {
		return codeFile;
	}
	public void setCodeFile(List<String> codeFile) {
		this.codeFile = codeFile;
	}
}
