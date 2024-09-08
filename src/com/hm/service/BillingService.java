package com.hm.service;

import com.hm.dao.BillingDAO;

import java.util.Iterator;
import java.util.List;
import com.hm.model.Invoice;
import com.hm.model.Invoice.Status;
import java.time.Instant;
import java.sql.Timestamp;
import com.hm.dao.RoomDao;
import com.hm.model.Room;

public class BillingService {
	private BillingDAO billingDAO = new BillingDAO();

	// Method to retrieve the bill based on customer ID
	public List<Invoice> getBillsByCustomerId(String customerId) {
		List<Invoice> nonPaidList = billingDAO.fetchInvoiceByCustomerId(customerId);
		for (Iterator<Invoice> iterator = nonPaidList.iterator(); iterator.hasNext();) {
			Invoice invoice = (Invoice) iterator.next();
			
			Timestamp timestamp = Timestamp.from(Instant.now());
			invoice.setInvoiceDate(timestamp);
			
			RoomDao rm = new RoomDao();
			System.out.println("invoiceRomId "+invoice.getRoomId());
			Room room = rm.getRoomStatus(invoice.getRoomId());
			double amount = room.getPrice();
			System.out.println("roomPrice"+room.getPrice());
			invoice.setRoomCharges(amount);
			
			invoice.setStatus(Status.UNPAID);
			invoice.setAdditionalCharges(invoice.calcAdditionalCharges(amount));
			invoice.setTotalAmount(invoice.calcTotalAmount(amount));
			System.out.println("{"+invoice.getAdditionalCharges()+","+invoice.getRoomCharges()+","+invoice.getTotalAmount()+"}");

		}
		return nonPaidList;
	}
}
