package com.hm.dao;

import com.hm.model.Room;
import com.hm.util.DbUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDao {

	public int findAvailableRoomId(String roomType) {

		DbUtility db = new DbUtility();
		PreparedStatement ps = null;
		Connection con = db.connectDatabase();
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
				System.out.println("rmdao "+room.getPrice());
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
}
