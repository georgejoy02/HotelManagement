package com.hm.service;

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

	public String generateInvoice(String reservationId, String customerId) {
		String outputDirectory = "";

		String hotelName = "Hotel Taj";
		String customerName = "John Doe";
		String mobile = "9876543210";
		String email = "john.doe@example.com";
		String address = "123, Main Street, City";
		int bookingId = 45678;
		Date checkInDate = new Date();
		Date checkOutDate = new Date();
		String roomType = "Deluxe";
		int roomId = 101;
		double roomCharges = 5000;
		double additionalCharges = 2000;
		String transactionId = "TXN987654321";

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
			contentStream.showText("Booking ID: " + bookingId);
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
			contentStream.showText("Room Charges: ₹" + roomCharges);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Additional Service Charges: ₹" + additionalCharges);
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("Total: ₹" + (roomCharges + additionalCharges));
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
			document.save(filePath.toFile());
			document.close();
			contentStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} 
		if(filePath!=null) {
			return filePath.toString();
		}

		return null;
	}
}
