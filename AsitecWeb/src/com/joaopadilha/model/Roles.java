package com.joaopadilha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="roles")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Roles {
	
	@Id
	@GeneratedValue
	@Column(name="idrole")
	private int IdRole;
	
	@Column(name="typeroles")
	private String typeRole;

	public int getIdRole() {
		return IdRole;
	}

	public void setIdRole(int idRole) {
		IdRole = idRole;
	}

	public String getTypeRole() {
		return typeRole;
	}

	public void setTypeRole(String typeRole) {
		this.typeRole = typeRole;
	} 
	
	
	
}
