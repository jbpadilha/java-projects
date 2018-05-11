package com.joaopadilha.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	
	@Id
	private int id;
	
	private String name;
	
	private String role;
	
	public User() {
		
	}
	
	public User(int id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return String.format("User[id=%s,name='%s',role='%s']", id,name,role);
	}
		
}
