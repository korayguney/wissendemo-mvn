package com.wissen.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.wissen.model.User;

@Stateless
public class UserService {

	@PersistenceContext
	EntityManager entityManager;

	public void saveUserWithJDBC(User user) {
		// create connection
		Connection conn = null;
		
		try {

			// create connection
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/wissendemo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "1234");

			// create statement
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO user(firstname,lastname) VALUES (?,?);");

			// set variables to query
			psmt.setString(1, user.getFirstname());
			psmt.setString(2, user.getLastname());

			// execute query
			psmt.execute();

			// close connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void saveUserWithJPA(User user) {
		entityManager.persist(user);
	}

}
