package com.joaopadilha.dao;

import java.util.List;

import com.joaopadilha.model.Person;

public interface PersonDAO {

	
	Person findPersonByName(String name);
	
	List<Person> listPerson();
	
	
}
