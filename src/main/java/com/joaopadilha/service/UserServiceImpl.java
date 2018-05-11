package com.joaopadilha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.joaopadilha.dao.UserDao;
import com.joaopadilha.model.User;

public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> findUser(String role) {
		List<User> listUser = userDao.findUser(role);
		return listUser;
	}

	@Override
	public void saveUser(User user) {
		
		userDao.save(user);
	}

}
