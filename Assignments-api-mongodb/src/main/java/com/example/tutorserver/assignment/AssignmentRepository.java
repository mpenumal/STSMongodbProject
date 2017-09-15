package com.example.tutorserver.assignment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository  extends MongoRepository<Assignment, String>{
	public Assignment findByName(String name);
	public void deleteByName(String name);
}
