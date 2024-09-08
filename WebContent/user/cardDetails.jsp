<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String username = (String) session.getAttribute("user");
if (username == null) {
	response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Enter Card Details</title>
<script>
	function validatePayment() {
		// Validate card details before making payment
		var cardNumber = document.getElementById("cardNo").value;
		var cardHolder = document.getElementById("cardHolder").value;
		var expiryDate = document.getElementById("expiryDate").value;
		var cvv = document.getElementById("cvv").value;

		if (cardNumber.length != 16 || cardHolder.length < 10
				|| cvv.length != 3) {
			alert("Please enter valid card details.");
			return false;
		}
		return true;
	}

	function makePayment() {
		if (validatePayment()) {
			alert("Payment Successful. Transaction ID: TXN12345678");
			window.location.href = 'invoice.jsp';
		}
	}
</script>
</head>
<body>
	<h3>Enter Your Credit Card Details</h3>

	<form id="paymentForm">
		<label>Credit Card: </label><br /> <label>Card No:</label> <input
			type="number" id="cardNo" min="16" required /><br /> <br /> <label>Card
			Holder Name:</label> <input type="text" id="cardHolder" minlength="10"
			required /><br /> <br /> <label>Expiry Date:</label> <input
			type="text" id="expiryDate" placeholder="MM/YY" required /><br /> <br />
		<label>CVV:</label> <input type="number" id="cvv" min="3" required /><br />
		<br />

		<button type="button" onclick="makePayment()">Make Payment</button>
	</form>
</body>
</html>