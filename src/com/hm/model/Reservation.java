package com.hm.model;

import java.sql.Date;

public class Reservation {
	private int bookingId;
	private String customerId;
	private Date checkInDate;
	private Date checkOutDate;
	private String roomType;
	private String name;
	private long contact;
	private int roomId;

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	// Getters and Setters
	public int getBookingId() {
		return bookingId;
	}

	public Reservation(int bookingId, String customerId, Date checkInDate, Date checkOutDate, String roomType,
			String name, long contact, int roomId) {
		super();
		this.bookingId = bookingId;
		this.customerId = customerId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomType = roomType;
		this.name = name;
		this.contact = contact;
		this.roomId = roomId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Reservation {" + "bookingId=" + bookingId + ", customerId=" + customerId + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate + ", roomType=" + roomType + ", name=" + name
				+ ", contact=" + contact + ", roomId=" + roomId + '}';
	}

}
