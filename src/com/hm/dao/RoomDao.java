package com.hm.dao;

import com.hm.model.Room;
import com.hm.util.DbUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDao {

	DbUtility db = new DbUtility();
	PreparedStatement ps = null;
	Connection con = db.connectDatabase();

	public int findAvailableRoomId(String roomType) {

		try {
			String query = "SELECT roomId FROM room WHERE roomType = ? AND status = 'Available'";
			ps = con.prepareStatement(query);
			ps.setString(1, roomType);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("roomId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}

		return 0;
	}

	public Room getRoomStatus(int roomId) {

		Room room = new Room();
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();

		try {
			String query = "SELECT * FROM room WHERE roomId = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, roomId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				room.setRoomId(rs.getInt("roomId"));
				room.setRoomNumber("roomNumber");
				room.setRoomType("roomType");
				room.setStatus(rs.getString("status"));
				room.setFloor(rs.getString("floor"));
				room.setPrice(rs.getDouble("price"));
				System.out.println("rmdao " + room.getPrice());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}
		return room;
	}

	public int addrooms(Room room) {
		int roomId = -1;
		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();

		String sql = "INSERT INTO Room (roomNumber, floor, roomType, price) VALUES (?, ?, ?, ?)";

		try {

			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, room.getRoomNumber());
			ps.setInt(2, Integer.parseInt(room.getFloor()));
			ps.setString(3, room.getRoomType());
			ps.setDouble(4, room.getPrice());

			ps.executeUpdate();

			con.commit();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys != null && generatedKeys.next()) {
				roomId = generatedKeys.getInt(1);
				System.out.println("Generated room id: " + roomId);
				room.setRoomId(roomId);
			} else {
				System.out.println("room id NOT GENERATED");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeStatement(ps);
			db.closeConnection(con);

		}

		return roomId;

	}
}
