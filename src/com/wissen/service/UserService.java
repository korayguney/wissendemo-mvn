package com.wissen.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.wissen.model.User;

@Stateless
public class UserService {

	@PersistenceContext
	EntityManager entityManager;

	private static int next_val = 0;

	public void saveUserWithJDBC(User user) {
		// define connection parameter
		Connection conn = null;

		try {

			// create connection via DriverManager
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wissendemo?useSSL=false", "root", "1234");

			// create preparedstatement
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO user(id,firstname,lastname) VALUES (?,?,?);");

			// set variables to query
			psmt.setInt(1, getNextVal(conn));
			psmt.setString(2, user.getFirstname());
			psmt.setString(3, user.getLastname());

			// execute query
			psmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// close connection
				conn.close();
				System.out.println(user +" is saved via JDBC");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void saveUserWithJPA(User user) {
		entityManager.persist(user);
		System.out.println(user +" is saved via JPA");
	}

	private int getNextVal(Connection conn) {

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet resSet = stmt.executeQuery("SELECT next_val FROM hibernate_sequence;");

			while (resSet.next()) {
				next_val = resSet.getInt("next_val");
			}

			stmt.execute("UPDATE hibernate_sequence SET next_val=" + (++next_val) + ";");
		} catch (SQLException e) {
			System.out.println("hibernate_sequence is NOT SET YET!");
		}
		return --next_val;
	}

}
