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
<title>Invoice</title>
</head>
<body>
	<h2>Payment Successful!</h2>
	<p>
		Thank you,
		<%=username%>. Your payment was successful.
	</p>
	<p>Transaction ID: TXN12345678</p>

	<h3>Invoice Details</h3>
	<p>Bill Amount: 5000.00</p>
	<p>Payment Method: Credit Card</p>
	<p>Card Holder: John Doe</p>
	<p>
		Date:
		<%=new java.util.Date()%></p>

	<button onclick="window.location.href='home.jsp'">Back to Home</button>
</body>
</html>