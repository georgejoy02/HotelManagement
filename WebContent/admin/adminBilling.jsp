<%@page import="com.hm.model.BookingDetails"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.hm.model.BookingDetails"%>
<%
// Cache-Control headers to prevent caching
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", 0); // Proxies

// Use the implicit session object directly
if (session == null || session.getAttribute("user") == null) {
	response.sendRedirect(request.getContextPath() + "/admin/adminLogin.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contact Support</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/style.css">
<script type="text/javascript">
	function confirmSub() {
		// Display a confirmation dialog
		var userConfirmed = confirm("Generate Invoice?");
		return userConfirmed;
	}
</script>
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
	String adminId = (String) session.getAttribute("user");

	if (adminId != null) {
	%>
	<jsp:include page="components/adminNavbar.jsp">
		<jsp:param name="adminId" value="adminId" />
	</jsp:include>
	<%
	}
	%>
	<div class="Outercontainer">


		<h1>Customer Reservations</h1>
		<form id="customerForm" method="POST"
			action="<%=request.getContextPath()%>/AdminController?action=fetchBookings">
			<label for="customerId">Enter Customer ID:</label> <input type="text"
				id="customerId" name="customerId" required>
			<button type="submit">Check Reservations</button>
		</form>

		<%
		// Retrieve the list of reservations from request attributes
		List<BookingDetails> reservations = (List<BookingDetails>) request.getAttribute("reservations");
		if (reservations != null && !reservations.isEmpty()) {
		%>
		<div id="reservationResult">
			<h2>Reservation Details</h2>
			<table>
				<tr>
					<th>Customer ID</th>
					<th>Reservation ID</th>
					<th>Payment Status</th>
					<th>Action</th>
				</tr>
				<%
				// Iterate over the list of reservations
				for (BookingDetails reservation : reservations) {
					String reservationId = reservation.getBookingId();
					String status = reservation.getStatus();// Assuming this is "Completed" or "Confirmed"
					String customerId = reservation.getCustomerId();
				%>
				<tr>
					<td><%=customerId%></td>
					<td><%=reservationId%></td>

					<%
					if (status.equalsIgnoreCase("confirmed")) {
					%><td>Pending</td>
					<%
					} else if (status.equalsIgnoreCase("completed")) {
					%><td>Done</td>
					<%
					} else {
					%><td>nil</td>
					<%
					}
					%>

					<td>
						<%
						if ("Completed".equalsIgnoreCase(status)) {
						%> <!-- Hidden form to show invoice -->
						<form
							action="<%=request.getContextPath()%>/InvoiceController?action=fetchInvoice"
							method="post" target="_blank" style="display: inline;">
							<input type="hidden" name="reservationId"
								value="<%=reservationId%>"> <input type="hidden"
								name="customerId" value="<%=customerId%>">
							<button type="submit" style="background-color: green">Show
								Invoice</button>
						</form> <%
 } else if ("Confirmed".equalsIgnoreCase(status)) {
 %> <!-- Hidden form to generate invoice -->
						<form
							action="<%=request.getContextPath()%>/InvoiceController?action=generateInvoice"
							method="post" target="_blank" style="display: inline;"
							onsubmit="return confirmSub();">
							<input type="hidden" name="reservationId"
								value="<%=reservationId%>"> <input type="hidden"
								name="customerId" value="<%=customerId%>">
							<button type="submit" style="background-color: red">Generate
								Invoice</button>
						</form> <%
 } else {
 %> cancelled<%
 }
 %>
					</td>
				</tr>
				<%
				}
				%>
			</table>
		</div>
		<%
		} else {
		%>
		<p>No reservations found for the given Customer ID.</p>
		<%
		}
		%>

	</div>
</body>

</html>