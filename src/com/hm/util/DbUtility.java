package com.hm.util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.hm.constants.*;

public class DbUtility {

	public Connection connectDatabase() {
		Connection connection = null;

		try {
			Class.forName(DBconstants.DRIVER);
			connection = DriverManager.getConnection(DBconstants.DBURL, DBconstants.DBUSER, DBconstants.DBPASSWORD);

		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("ClassNotFoundException");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e);
		}

		return connection;

	}

	public void closeConnection(Connection con) {
		try {
			con.close();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void closeStatement(PreparedStatement ps) {
		try {
			ps.close();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
