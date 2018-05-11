package com.joaopadilha.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.joaopadilha.model.User;

@RepositoryRestResource
@Repository
public interface UserDao extends MongoRepository<User, String>{
	
	void saveUser(User user);
	
	List<User> findUser(@Param("role") String role);

}
