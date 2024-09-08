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
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Reservation</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/style.css">
<style>
form {
	max-width: 600px;
	margin: 0 auto;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 8px;
	background-color: #f9f9f9;
}
</style>
</head>

<body>
	<div id="navbar"></div>
	<%
	String customerId = (String) session.getAttribute("user");

	if (customerId != null) {
	%>
	<jsp:include page="components/userNavbar.jsp">
		<jsp:param name="customerId" value="customerId" />
	</jsp:include>
	<%
	}
	%>
	<div class="Outercontainer">
		<h2>Reservation</h2>
		<%=request.getAttribute("status") != null ? request.getAttribute("status") : ""%>
		<%=request.getAttribute("bookingId") != null ? request.getAttribute("bookingId") : ""%>
		<form id="reservationForm"
			action="<%=request.getContextPath()%>/UserController?action=reservationUser"
			method="post">
			<label for="checkin-date">Check-in Date:</label> <input type="date"
				id="checkin-date" name="checkinDate" required> <label
				for="checkout-date">Check-out Date:</label> <input type="date"
				id="checkout-date" name="checkoutDate" required> <label
				for="room-preferences">Room Preferences:</label> <select
				id="room-preferences" name="roomPreferences">
				<option value="single">Single</option>
				<option value="double">Double</option>
				<option value="suite">Suite</option>
			</select> <label for="personal-name">Name:</label> <input type="text"
				id="personal-name" name="name" required> <label
				for="personal-contact">Contact:</label> <input type="text"
				id="personal-contact" name="contact" pattern="\d+" required>
			<input type="hidden" id="customerId" name="customerId"
				value="<%=customerId%>">
			<button type="submit">Submit</button>
		</form>
	</div>
	<div id="reservation-success" style="display: none;">Reservation
		Successful</div>
	<script src="js/reservation.js"></script>
</body>

</html>