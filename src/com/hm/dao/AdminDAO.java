package com.hm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hm.util.PasswordUtils;

import com.hm.util.DbUtility;

public class AdminDAO {

	PasswordUtils psv;
	DbUtility db;
	PreparedStatement ps;
	Connection con;

	public AdminDAO() {
		psv = new PasswordUtils();
		db = new DbUtility();
		ps = null;
		con = db.connectDatabase();
	}

	public boolean validateUser(String username, String password) {

		String query = "SELECT PasswordHash FROM Admin WHERE AdminID = ? ";
		System.out.println("adminDao: " + username);

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, username);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String PasswordHash = rs.getString("PasswordHash");
					return psv.validatePassword(password, PasswordHash);
				} else {
					System.out.println("admin not found.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception
		} finally {

			db.closeStatement(ps);
			db.closeConnection(con);

		}
		return false;
	}

	public boolean validateAdmin(String adminId) {

		String query = "SELECT * FROM Admin WHERE AdminID = ? ";

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, adminId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					return true;
				} else {
					System.out.println("admin not found.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception
		} finally {

			db.closeStatement(ps);
			db.closeConnection(con);

		}

		return false;

	}
}
