package com.hm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.sql.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.math.BigInteger;

public class Utility {

	public Date convertStringToSQLDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			java.util.Date javaDate = sdf.parse(strDate);
			Date sqlDate = new Date(javaDate.getTime());
			return sqlDate;

		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	public String formatTimestampToDate(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(timestamp);
	}

	public String generateTransactionID(String customerId, int reservationId, String customerName, String mobile,
			String email, String address, java.util.Date checkInDate, java.util.Date checkOutDate, String roomType,
			int roomId, double roomCharges, double additionalCharges) {
		try {
			// Concatenate the values to create a base string
			String baseString = customerId + reservationId + customerName + mobile + email + address
					+ checkInDate.getTime() + checkOutDate.getTime() + roomType + roomId + roomCharges
					+ additionalCharges + System.currentTimeMillis();

			// Use SHA-256 to hash the base string
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(baseString.getBytes());

			// Convert the hash to a hexadecimal string
			BigInteger number = new BigInteger(1, hashBytes);
			String hashText = number.toString(16);

			// Ensure the hashText is at least 15 characters long (padded if needed)
			while (hashText.length() < 15) {
				hashText = "0" + hashText;
			}

			// Return the first 15 characters as the transaction ID
			return "TXN" + hashText.substring(0, 15);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean carddetailsVerify(String paymentMode, String cardNumber, String cardHolderName, String expiryDate,
			String cvv) {

		// Validate card number
		if (cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
			return false;
		}

		// Validate card holder name
		if (cardHolderName == null || cardHolderName.length() < 10) {
			return false;
		}

		// Validate expiry date
		if (expiryDate == null || !expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
			return false;
		}

		// Validate CVV
		if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d+")) {
			return false;
		}
		if (!paymentMode.equalsIgnoreCase("debit") && !paymentMode.equalsIgnoreCase("credit")) {
			System.out.println(paymentMode);
			return false;
		}

		// All validations passed
		return true;
	}

	public String createOutputDirectory(String outputDirectoryPath) {

		File outputDirectory = new File(outputDirectoryPath);

		// Check if the directory exists
		if (!outputDirectory.exists()) {
			// Attempt to create the directory
			boolean wasDirectoryCreated = outputDirectory.mkdirs();
			if (wasDirectoryCreated) {
				System.out.println("Directory created: " + outputDirectoryPath);
				return outputDirectoryPath;
			} else {
				System.err.println("Failed to create directory: " + outputDirectoryPath);
			}
		} else {
			System.out.println("Directory already exists: " + outputDirectoryPath);
			return outputDirectoryPath;
		}
		return null;
	}

}
