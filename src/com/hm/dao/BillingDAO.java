package com.hm.dao;

import com.hm.model.Invoice;
import com.hm.util.DbUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

	public String fetchInvoice(String customerId, int reservationId) {
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();
		String filePath = null;

		String sql = "SELECT invoicePath FROM Invoices WHERE CustomerId = ? AND reservationId = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, customerId);
			ps.setInt(2, reservationId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					filePath = rs.getString("invoicePath");
				} else {
					System.out.println("Invoice metadata not found.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}
		return filePath;
	}
	

	public String saveInvoiceMetadata(String customerId, int reservationId, String filePath) {
		String invoicePath = null;
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();
		int rowsAffected = 0;

		String sql = "INSERT INTO Invoices (customerId, reservationId, invoicePath) VALUES (?, ?, ?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, customerId);
			ps.setInt(2, reservationId);
			ps.setString(3, filePath);

			rowsAffected = ps.executeUpdate();
			if(rowsAffected>0) {
				invoicePath=filePath;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}
		return invoicePath;

	}

	public boolean updatePaymentStatus(int reservationId, int roomId) {
		String updateRoomSql = "UPDATE Room SET status = 'Available' WHERE roomId = ?";
		String updateReservationSql = "UPDATE Reservation SET status = 'completed' WHERE reservationId = ?";

		DbUtility db = new DbUtility();
		Connection con = db.connectDatabase();
		PreparedStatement updateRoomStmt = null;
		PreparedStatement updateReservationStmt = null;
		boolean status = false;

		try {

			con.setAutoCommit(false);

			updateRoomStmt = con.prepareStatement(updateRoomSql);
			updateReservationStmt = con.prepareStatement(updateReservationSql);

			updateRoomStmt.setInt(1, roomId);
			int roomRowsAffected = updateRoomStmt.executeUpdate();

			updateReservationStmt.setInt(1, reservationId);
			int reservationRowsAffected = updateReservationStmt.executeUpdate();

			if (roomRowsAffected > 0 && reservationRowsAffected > 0) {
				con.commit();
				status = true;

			} else {
				con.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			db.closeStatement(updateRoomStmt);
			db.closeStatement(updateReservationStmt);
			db.closeConnection(con);

		}

		return status;

	}

	public Invoice fetchBillDetails(int reservationId, String customerId) {
		Invoice invoice = null;

		String sql = "SELECT r.reservationId, r.checkInDate, r.checkOutDate, r.roomId," + " rm.price, rm.roomType,"
				+ " c.Email, c.MobileNumber, c.CustomerName, c.Address " + "FROM Reservation r "
				+ "JOIN Room rm ON r.roomId = rm.roomId " + "JOIN Customer c ON r.customerId = c.CustomerID "
				+ "WHERE r.reservationId = ? AND c.CustomerID = ?";

		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, reservationId);
			ps.setString(2, customerId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					invoice = new Invoice();
					invoice.setAddress(rs.getString("Address"));
					invoice.setCheckInDate(rs.getDate("checkInDate"));
					invoice.setCheckOutDate(rs.getDate("checkOutDate"));
					invoice.setEmail(rs.getString("Email"));
					invoice.setRoomType(rs.getString("roomType"));
					invoice.setMobile(rs.getString("MobileNumber"));
					invoice.setCustomerName(rs.getString("CustomerName"));
					invoice.setRoomId(rs.getInt("roomId"));
					invoice.setRoomCharges(rs.getDouble("price"));
					invoice.setAdditionalCharges(invoice.calcAdditionalCharges(rs.getDouble("price")));

					invoice.setReservationId(reservationId);
					invoice.setCustomerId(customerId);

				} else {
					System.out.println("Invoice metadata not found for generating pdf.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}
		return invoice;
	}

	public List<Invoice> fetchBillsByCustomerId(String customerId) {

		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();
		List<Invoice> invoices = new ArrayList<Invoice>();

		try {
			String query = "SELECT reservationId,customerid,roomId,name FROM Reservation WHERE customerId = ? AND status=?";
			ps = con.prepareStatement(query);
			ps.setString(1, customerId);
			ps.setString(2, "confirmed");

			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					Invoice invoice = new Invoice();
					invoice.setReservationId(resultSet.getInt("reservationId"));
					invoice.setRoomId(resultSet.getInt("roomId"));
					invoice.setCustomerId(resultSet.getString("customerid"));
					invoice.setCustomerName(resultSet.getString("name"));
					invoices.add(invoice);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}

		return invoices;
	}

}
