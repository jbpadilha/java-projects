package com.joaopadilha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="person")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Person {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int ID;
	
	@Column(name="name")
	private String name;

	@ManyToOne
	@JoinColumn(name="idrole")	
	private Roles role;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}
	
	
	
}
