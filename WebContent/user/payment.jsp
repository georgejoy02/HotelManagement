<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// Cache-Control headers to prevent caching
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", 0); // Proxies

// Use the implicit session object directly
if (session == null || session.getAttribute("user") == null) {
	response.sendRedirect(request.getContextPath() + "/user/login.jsp");
	return;
}
String totalAmount = (String) request.getAttribute("totalAmount");
String reservationId = (String) request.getAttribute("reservationId");
String customerId = (String) request.getAttribute("customerId");
%>

<!DOCTYPE html>
<html>
<head>
<title>Payment Page</title>
<script>
	function validateCardNumber() {
		const cardNumber = document.getElementById("cardNumber").value;
		const cardNumberError = document.getElementById("cardNumberError");

		if (cardNumber.length !== 16 || isNaN(cardNumber)) {
			cardNumberError.textContent = "Please enter a valid 16-digit card number.";
		} else {
			cardNumberError.textContent = "";
		}
	}

	function validateCardHolderName() {
		const cardHolderName = document.getElementById("cardHolderName").value;
		const cardHolderNameError = document
				.getElementById("cardHolderNameError");

		if (cardHolderName.length < 10) {
			cardHolderNameError.textContent = "Card Holder Name must be at least 10 characters.";
		} else {
			cardHolderNameError.textContent = "";
		}
	}

	function validateExpiryDate() {
		const expiryDate = document.getElementById("expiryDate").value;
		const expiryDateError = document.getElementById("expiryDateError");

		if (!expiryDate.match(/^(0[1-9]|1[0-2])\/\d{2}$/)) {
			expiryDateError.textContent = "Please enter a valid expiry date (MM/YY).";
		} else {
			expiryDateError.textContent = "";
		}
	}

	function validateCvv() {
		const cvv = document.getElementById("cvv").value;
		const cvvError = document.getElementById("cvvError");

		if (cvv.length !== 3 || isNaN(cvv)) {
			cvvError.textContent = "Please enter a valid 3-digit CVV.";
		} else {
			cvvError.textContent = "";
		}
	}

	// Attach event listeners to inputs for real-time validation
	document.addEventListener("DOMContentLoaded", function() {
		document.getElementById("cardNumber").addEventListener("input",
				validateCardNumber);
		document.getElementById("cardHolderName").addEventListener("input",
				validateCardHolderName);
		document.getElementById("expiryDate").addEventListener("input",
				validateExpiryDate);
		document.getElementById("cvv").addEventListener("input", validateCvv);
	});
</script>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 20px;
	background-color: #f5f5f5;
}

h2 {
	color: #333;
}

form {
	background: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	max-width: 500px;
	margin: 0 auto;
}

label {
	display: block;
	margin-bottom: 8px;
	font-weight: bold;
	color: #555;
}

input[type="text"], select {
	width: calc(100% - 20px);
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ddd;
	border-radius: 4px;
	font-size: 16px;
	box-sizing: border-box;
}

input[readonly] {
	background: #f0f0f0;
}

button {
	background-color: #4CAF50;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
	display: inline-block;
	text-align: center;
}

button:hover {
	background-color: #45a049;
}

.error {
	color: red;
	font-size: 14px;
	display: block;
	margin-top: -10px;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<h2>Payment Page</h2>

	<form action="<%=request.getContextPath()%>/InvoiceController?action=generateInvoice" method="post">
		<label for="customerID">Customer ID:</label> <input type="text"
			id="customerID" name="customerId" value="<%=customerId%>" readonly><br>
		<label for="reservationId">Reservation ID:</label> <input type="text"
			id="reservationId" name="reservationId" value="<%=reservationId%>"
			readonly><br> <label for="billAmount">Bill
			Amount:</label> <input type="text" id="billAmount" name="billAmount"
			value="<%=totalAmount%>" readonly><br> <label
			for="paymentMode">Mode of Payment:</label> <select id="paymentMode"
			name="paymentMode">
			<option value="debit">Debit</option>
			<option value="credit">Credit</option>
		</select><br> <label for="cardNumber">Card Number:</label> <input
			type="text" id="cardNumber" name="cardNumber" minlength="16"
			maxlength="16"> <span id="cardNumberError" class="error"></span><br>

		<label for="cardHolderName">Card Holder Name:</label> <input
			type="text" id="cardHolderName" name="cardHolderName" minlength="10">
		<span id="cardHolderNameError" class="error"></span><br> <label
			for="expiryDate">Expiry Date (MM/YY):</label> <input type="text"
			id="expiryDate" name="expiryDate" placeholder="MM/YY"> <span
			id="expiryDateError" class="error"></span><br> <label for="cvv">CVV:</label>
		<input type="text" id="cvv" name="cvv" minlength="3" maxlength="3">
		<span id="cvvError" class="error"></span><br>

		<button type="submit">Make Payment</button>
	</form>
</body>
</html>


