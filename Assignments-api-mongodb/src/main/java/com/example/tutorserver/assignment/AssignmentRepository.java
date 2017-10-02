package com.example.tutorserver.assignment;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository  extends MongoRepository<Assignment, String>{
	public List<Assignment> findByCourseName(String courseName);
	public Assignment findByCourseNameAndAssignmentName(String courseName, String assignmentName);
	public void deleteByCourseNameAndAssignmentName(String courseName, String assignmentName);
}
