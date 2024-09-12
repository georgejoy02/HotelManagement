package com.hm.service;

import com.hm.dao.BillingDAO;
import com.hm.model.Invoice;
import com.hm.util.Utility;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class InvoiceService {
	private ServletContext servletContext;

	public InvoiceService(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String fetchInvoiceService(String customerId,int reservationId) {
		String invoicePath=null;
		BillingDAO bd = new BillingDAO();
		invoicePath=bd.fetchInvoice(customerId, reservationId);
		return invoicePath;
	}

	public String generateInvoice(int reservationId, String customerId) {
		
		
		Utility crDir = new Utility();
		String outputDirectory = crDir.createOutputDirectory(servletContext.getRealPath("/invoices"));
		BillingDAO bd = new BillingDAO();
		Invoice invoice = bd.fetchBillDetails(reservationId, customerId);// is null
		if (invoice == null) {
			System.out.println("InvoiceService: invoice null");
			return null;
		}
		String hotelName = "Hotel Taj";
		String customerName = invoice.getCustomerName();
		String mobile = invoice.getMobile();
		String email = invoice.getEmail();
		String address = invoice.getAddress();
		Date checkInDate = invoice.getCheckInDate();
		Date checkOutDate = invoice.getCheckOutDate();
		String roomType = invoice.getRoomType();
		int roomId = invoice.getRoomId();
		double roomCharges = invoice.getRoomCharges();
		double additionalCharges = invoice.getAdditionalCharges();
		Utility txUtil = new Utility();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timestampFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String currentTimestamp = timestampFormat.format(new Date());

		String transactionId = txUtil.generateTransactionID(customerId, reservationId, customerName, mobile, email,
				address, checkInDate, checkOutDate, roomType, roomId, roomCharges, additionalCharges);
		if (transactionId != null) {
			boolean status = bd.updatePaymentStatus(reservationId, roomId);
			if (!status) {
				return null;
			}
		} else {
			return null;
		}

		invoice.setTransactionId(transactionId);
		String invoicePath=null;

		Path filePath = null;
		PDPageContentStream contentStream;
		String fileName;

		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		try {

			document.addPage(page);

			contentStream = new PDPageContentStream(document, page);

			// Add hotel name (Header)
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 750);
			contentStream.showText(hotelName);
			contentStream.endText();

			// Add "Invoice" title
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 720);
			contentStream.showText("INVOICE");
			contentStream.endText();

			// Draw a line below the title for separation
			contentStream.moveTo(100, 710);
			contentStream.lineTo(500, 710);
			contentStream.stroke();

			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 700);
			contentStream.showText("Generated on: " + currentTimestamp); // Add timestamp here
			contentStream.endText();

			// Section: Customer Information
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 680);
			contentStream.showText("Customer Information:");
			contentStream.endText();

			// Customer details in the next lines
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 660);
			contentStream.showText("Name: " + customerName);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Mobile: " + mobile);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Email: " + email);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Customer ID: " + customerId);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Address: " + address);
			contentStream.endText();

			// Section: Booking Details
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 560);
			contentStream.showText("Booking Details:");
			contentStream.endText();

			// Booking and Room details
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 540);
			contentStream.showText("Booking ID: " + reservationId);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Check-in Date: " + dateFormat.format(checkInDate));
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Check-out Date: " + dateFormat.format(checkOutDate));
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Room Type: " + roomType);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Room ID: " + roomId);
			contentStream.endText();

			// Section: Charges Breakdown
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 440);
			contentStream.showText("Charges:");
			contentStream.endText();

			// Charges details
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 420);
			contentStream.showText("Room Charges: " + roomCharges + " INR");
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Additional Service Charges: " + additionalCharges);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Total: " + (roomCharges + additionalCharges) + " INR");
			contentStream.endText();

			// Section: Payment Information
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 320);
			contentStream.showText("Payment Information:");
			contentStream.endText();

			// Payment status and transaction details
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 300);
			contentStream.showText("Payment Status: Successful");
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Transaction ID: " + transactionId);
			contentStream.endText();

			// Section: Reservation Information
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 240);
			contentStream.showText("Reservation Information:");
			contentStream.endText();

			// Reservation details
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(100, 220);
			contentStream.showText("Reservation ID: " + reservationId);
			contentStream.endText();
			fileName = "invoice_" + customerId + "_" + reservationId + ".pdf";
			filePath = Paths.get(outputDirectory, fileName);
			contentStream.close();
			document.save(filePath.toFile());
			document.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (filePath != null) {
			 invoicePath= bd.saveInvoiceMetadata(customerId,reservationId,filePath.toString());
		}

		return invoicePath;
	}
}
