package com.joaopadilha.dao;

import java.util.List;

import com.joaopadilha.model.Roles;

public interface RolesDAO {
	
	Roles findRoleByType(String typeRole);
	
	List<Roles> listRoles();
	
	void addRoles(Roles roles) ;
}
