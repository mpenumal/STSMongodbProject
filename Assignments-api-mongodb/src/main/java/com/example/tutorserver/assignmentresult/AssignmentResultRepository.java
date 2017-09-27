package com.example.tutorserver.assignmentresult;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentResultRepository  extends MongoRepository<AssignmentResult, String>{
	public List<AssignmentResult> findByStudentId(String studentId);
	public List<AssignmentResult> findByCourseNameAndStudentId(String courseName, String studentId);
	public List<AssignmentResult> findByAssignmentNameAndCourseName(String assignmentName, String courseName);
	public AssignmentResult findByAssignmentNameAndCourseNameAndStudentId(String assignmentName, String courseName, String studentId);
}
