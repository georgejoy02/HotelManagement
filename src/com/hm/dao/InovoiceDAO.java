package com.hm.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hm.util.DbUtility;

public class InovoiceDAO {
	public static int saveInvoiceMetadata(String CustomerId, String reservationId, String filePath) {
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();
		int rowsAffected = 0;
		String sql = "INSERT INTO Invoices (CustomerId, reservationId, invoicePath) VALUES (?, ?, ?)";

		try {

			ps = con.prepareStatement(sql);

			ps.setString(1, CustomerId);
			ps.setString(2, reservationId);
			ps.setString(3, filePath);
			rowsAffected = ps.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}
		return rowsAffected;
	}

	protected String fetchInvoice(String userId, String reservationId) {
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();

		String sql = "SELECT invoicePath FROM Invoices WHERE CustomerId = ? AND reservationId = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, userId);
			ps.setString(2, reservationId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String filePath = rs.getString("invoicePath");
					return filePath;
				} else {
					System.out.println("Invoice metadata not found.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
