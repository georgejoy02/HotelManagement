<%@page import="com.hm.service.BookingFetchService"%>
<%@page import="com.hm.dao.ReservationDAO"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.hm.model.BookingDetails"%>
<%@ page import="com.hm.util.Utility"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Date"%>
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
<title>History</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/style.css">
<style>
table {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
	font-size: 16px;
	text-align: left;
}

th, td {
	padding: 12px;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #f4f4f4;
	color: #333;
}

tr:nth-child(even) {
	background-color: #f9f9f9;
}

tr:hover {
	background-color: #f1f1f1;
}

button {
	padding: 8px 16px;
	font-size: 14px;
	color: #fff;
	background-color: #007bff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

button:hover {
	background-color: #0056b3;
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
		<h2>Previous Booking History</h2>
		<table>
			<thead>
				<tr>
					<th>No.</th>
					<th>Reservation ID</th>
					<th>Check-In Date</th>
					<th>Check-Out Date</th>
					<th>Room Number</th>
					<th>Bill Amount</th>
					<th>Booking Date</th>
				</tr>
			</thead>
			<tbody>
				<%
				BookingFetchService bfs = new BookingFetchService();

				List<BookingDetails> bookings = bfs.getPreviousBookings(customerId);

				if (bookings != null) {
					int index = 1;
					for (BookingDetails bd : bookings) {
						String reservationId = bd.getBookingId();
						double totalAmount = bd.getTotalAmount();
						Utility ult = new Utility();
						String BookingDate = ult.formatTimestampToDate(bd.getBookingDate());
						Date checkIN = bd.getCheckInDate();
						Date checkOut = bd.getCheckOutDate();
						String rmNumber = bd.getRoomNumber();
				%>
				<tr>
					<td><%=index++%></td>
					<td><%=reservationId%></td>
					<td><%=checkIN%></td>
					<td><%=checkOut%></td>
					<td><%=rmNumber%></td>
					<td><%=totalAmount%></td>
					<td><%=BookingDate%></td>

					<td></td>

				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>
	<script src="js/history.js"></script>
</body>

</html>