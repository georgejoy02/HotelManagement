package com.hm.dao;

import com.hm.model.BookingDetails;
import com.hm.model.Reservation;
import com.hm.util.DbUtility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

	int rowsAffected = 0;
	DbUtility db = new DbUtility();
	PreparedStatement ps = null;
	Connection con = db.connectDatabase();

	public int saveReservation(Reservation reservation) {
		try {
			System.out.println("at reservDAO" + reservation);

			int reservationId = -1;

			String query = "INSERT INTO Reservation (customerId, checkInDate, checkOutDate, roomId, roomType, name, contactNumber,status)"
					+ "	VALUES (?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, reservation.getCustomerId());
			ps.setDate(2, reservation.getCheckInDate());
			ps.setDate(3, reservation.getCheckOutDate());
			ps.setInt(4, reservation.getRoomId());
			ps.setString(5, reservation.getRoomType());
			ps.setString(6, reservation.getName());
			ps.setLong(7, reservation.getContact());
			ps.setString(8, "confirmed");
			rowsAffected = ps.executeUpdate();
			con.commit();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys != null && generatedKeys.next()) {
				reservationId = generatedKeys.getInt(1);
				System.out.println("Generated reservation id: " + reservationId);
				reservation.setBookingId(reservationId);
			} else {
				System.out.println("NOT GENERATED");
			}

			if (rowsAffected > 0) {
				System.out.println("insert reservatation afftected");
				query = "UPDATE Room SET status = 'Booked', updated_at = CURRENT_TIMESTAMP WHERE roomId = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, reservation.getRoomId());
				int upadateAffected = ps.executeUpdate();
				if (upadateAffected > 0) {
					System.out.println("update Room afftected");
				}
				con.commit();

			}
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

	public List<BookingDetails> getBookings(String customerId, String operator) {
		List<BookingDetails> prvsBkngs = new ArrayList<BookingDetails>();
		String sql = "SELECT reservationId,status, checkInDate, checkOutDate, roomId, updated_at FROM reservation WHERE customerId = ?";

		if (operator != null && !operator.isEmpty()) {
			sql += " AND checkOutDate " + operator + " ?";
		}
		Date today = new Date(System.currentTimeMillis());

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, customerId);

			if (operator != null && !operator.isEmpty()) {
				ps.setDate(2, today); // Only set this if there's an operator
			}

			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					BookingDetails bd = new BookingDetails();
					bd.setCustomerId(customerId);
					bd.setBookingId(resultSet.getString("reservationId"));
					bd.setStatus(resultSet.getString("status"));
					bd.setCheckInDate(resultSet.getDate("checkInDate"));
					bd.setCheckOutDate(resultSet.getDate("checkOutDate"));
					bd.setBookingDate(resultSet.getTimestamp("updated_at"));
					int roomid = resultSet.getInt("roomid");

					sql = "select roomNumber,price from Room where roomid=?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, roomid);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						bd.setRoomNumber(rs.getString("roomNumber"));
						bd.setTotalAmount(rs.getDouble("price"));
					}

					prvsBkngs.add(bd);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			db.closeStatement(ps);
			db.closeConnection(con);

		}
		return prvsBkngs;
	}
}
