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
	public List<Invoice> fetchInvoiceByCustomerId(String customerId) {
		
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
                    invoice.setReservationId(resultSet.getString("reservationId"));
                    invoice.setRoomId(resultSet.getInt("roomId"));
                    invoice.setCustomerId(resultSet.getString("customerid"));
                    invoice.setCustomerName(resultSet.getString("name"));
                    invoices.add(invoice);
                }
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}

		return invoices;
	}
}
