package com.joaopadilha.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.joaopadilha.model.Roles;

public class RolesDAOImplement {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addRoles(Roles roles) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		if (roles != null) {
			try {
				session.save(roles);
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				session.close();
			}

		}
		
	}
	public List<Roles> findAllRoles() {
		List<Roles> roles = new ArrayList<Roles>();
		Session session = sessionFactory.openSession();
		String sql = "select r.id, r.typeRole from Roles";
		Query<Roles> query = session.createQuery(sql);
		roles = query.list();
		return roles;
	}
	
}
