package com.joaopadilha.service;

import java.util.List;

import com.joaopadilha.model.Roles;

public interface RolesService {
	
	Roles findRoleByType(String typeRole);
	
	List<Roles> listRoles();
	
	void addRoles(Roles role);
}
