package com.joaopadilha.service;

import java.util.List;

import com.joaopadilha.model.Person;

public interface PersonService {
	
	Person findPersonByName(String name);
	
	List<Person> listPerson();
	
	void addPerson(Person person);
	
}
