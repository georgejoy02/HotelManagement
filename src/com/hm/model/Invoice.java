package com.hm.model;

import java.sql.Timestamp;

public class Invoice {
	public enum Status {
		PAID, UNPAID
	}

	private String reservationId;
	private String customerId;
	private int roomId;

	private String customerName;
	private double roomCharges;
	private double additionalCharges;
	private double totalAmount;
	private Timestamp invoiceDate;
	private Status status;

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getRoomCharges() {
		return roomCharges;
	}

	public void setRoomCharges(double roomCharges) {
		this.roomCharges = roomCharges;
	}

	public double calcAdditionalCharges(double roomCharges) {
//tax
		return roomCharges * 0.18;
	}

	public double getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(double additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public double getTotalAmount() {

		return totalAmount;
	}

	public double calcTotalAmount(double roomCharges) {
		totalAmount = calcAdditionalCharges( roomCharges) + roomCharges;
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
