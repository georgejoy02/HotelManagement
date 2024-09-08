package com.hm.dao;

import com.hm.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hm.util.DbUtility;

public class UserDAO {
	public int addUser(User user) {

		int rowsAffected = 0;
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();

		try {
			String sql = "INSERT INTO Customer (CustomerID, CustomerName, Email, MobileNumber, Address, PasswordHash,AccountStatus) VALUES (?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getCustomerId());
			ps.setString(2, user.getCustomerName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getMobile());
			ps.setString(5, user.getAddress());
			ps.setString(6, user.getPassword());
			ps.setString(7, "active");

			rowsAffected = ps.executeUpdate();
			con.commit();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}

		return rowsAffected;
	}

	public boolean validateUser(String username, String password) {
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();
		String query = "SELECT * FROM CUSTOMER WHERE CustomerID = ? AND PasswordHash = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);

			try (ResultSet resultSet = ps.executeQuery()) {
				return resultSet.next(); // Returns true if a user is found
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception
		}
		return false;
	}

}
