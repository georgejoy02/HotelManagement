package com.hm.service;

import com.hm.dao.ReservationDAO;
import com.hm.dao.RoomDao;
import com.hm.model.Reservation;

public class ReservationService {
	public int addReservation(Reservation reservation) {
		RoomDao rm = new RoomDao();
		String roomType = reservation.getRoomType();

		if (roomType.equalsIgnoreCase("Single")) {
			reservation.setRoomType("Single");

		} else if (roomType.equalsIgnoreCase("Double")) {
			reservation.setRoomType("Double");

		} else if (roomType.equalsIgnoreCase("Suite")) {
			reservation.setRoomType("Suite");

		} else {
			System.out.println("rmtype not correct");
			return 0;

		}

		int roomId = rm.findAvailableRoomId(reservation.getRoomType());
		if (roomId == 0) {
			System.out.println("no rroms available");
			return 0;
		}
		reservation.setRoomId(roomId);

		ReservationDAO reservDao = new ReservationDAO();
		System.out.println(reservation);
		reservation.setRoomType(reservation.getRoomType().toLowerCase());

		return reservDao.saveReservation(reservation);
	}
	
}
