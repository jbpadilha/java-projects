package com.joaopadilha.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.joaopadilha.model.User;
import com.joaopadilha.service.UserServiceImpl;

@SpringBootApplication
@RestController
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/users/{role}", method=RequestMethod.GET)
	public ResponseEntity<List<User>> listUser(@PathVariable("role") String role){
		logger.debug("Receiving Data for searching");
		UserServiceImpl userService = new UserServiceImpl();
		List<User> users = userService.findUser(role);
		
		try {
			if(users.size()>0) {
				logger.debug("Everthing OK");
				return new ResponseEntity<List<User>>(users, HttpStatus.OK);
			}
			else {
				logger.debug("Not Found");
				return new ResponseEntity<List<User>>(users, HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			logger.debug("Error"+e.getMessage());
			return new ResponseEntity<List<User>>(users, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/users/", method=RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
		logger.debug("Creating User");
		UserServiceImpl userService = new UserServiceImpl();
		userService.saveUser(user);
		logger.debug("Everthing OK");
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
}
