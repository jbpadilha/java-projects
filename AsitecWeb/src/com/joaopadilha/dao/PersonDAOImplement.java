package com.joaopadilha.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.joaopadilha.model.Person;

public class PersonDAOImplement {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public void addPerson(Person person) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		if (person != null) {
			try {
				session.save(person);
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				session.close();
			}

		}
		
	}
	
	public List<Person> findAllPerson() {
		List<Person> person = new ArrayList<Person>();
		Session session = sessionFactory.openSession();
		String sql = "select p.id, p.name from Person p INNER JOIN Roles";
		Query<Person> query = session.createQuery(sql);
		person = query.list();
		return person;
	}


}
