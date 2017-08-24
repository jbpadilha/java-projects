package com.joaopadilha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joaopadilha.model.Person;
import com.joaopadilha.model.Roles;
import com.joaopadilha.service.PersonService;
import com.joaopadilha.service.RolesService;


@RestController
public class CrudPersonRoles {
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private RolesService rolesService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}
	
	@RequestMapping(value = "/person/new", method = RequestMethod.POST)
	public ResponseEntity<Void> addPerson(@RequestBody Person person, UriComponentsBuilder ucb) {
			personService.addPerson(person);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucb.path("/person/{id}").buildAndExpand(person.getID()).toUri());
			return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/roles/new", method = RequestMethod.POST)
	public ResponseEntity<Void> addRoles(@RequestBody Roles role, UriComponentsBuilder ucb) {
			rolesService.addRoles(role);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucb.path("/roles/{id}").buildAndExpand(role.getIdRole()).toUri());
			return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/listPerson", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> listAllPerson() throws JsonProcessingException {
		List<Person> persons = personService.listPerson();
		if (persons.isEmpty()) {
			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listRoles", method = RequestMethod.GET)
	public ResponseEntity<List<Roles>> listAllRoles() throws JsonProcessingException {
		List<Roles> roles = rolesService.listRoles();
		if (roles.isEmpty()) {
			return new ResponseEntity<List<Roles>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Roles>>(roles, HttpStatus.OK);
	}
	
}
