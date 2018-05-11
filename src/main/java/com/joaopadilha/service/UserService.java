package com.joaopadilha.service;

import java.util.List;

import com.joaopadilha.model.User;

public interface UserService  {
	
	public List<User> findUser(String role);
	
	void saveUser(User user);
}
