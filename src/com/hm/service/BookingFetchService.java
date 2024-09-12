package com.hm.service;

import java.util.List;

import com.hm.dao.ReservationDAO;
import com.hm.model.BookingDetails;

public class BookingFetchService {
	ReservationDAO rd;

	public BookingFetchService() {
		rd = new ReservationDAO();
	}

	public List<BookingDetails> getPreviousBookings(String customerId) {

		return rd.getBookings(customerId, "<");

	}

	public List<BookingDetails> getUpcomingBookings(String customerId) {

		return rd.getBookings(customerId, ">=");

	}
	
	public List<BookingDetails> getBookings(String customerId) {

		return rd.getBookings(customerId, null);

	}
}
