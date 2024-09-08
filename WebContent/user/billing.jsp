<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.hm.model.Invoice"%>
<%@ page import="com.hm.service.BillingService"%>
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
<title>Billing</title>
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
		<h2>Billing</h2>
		<table>
			<thead>
				<tr>
					<th>No.</th>
					<th>Reservation ID</th>
					<th>Room Charges</th>
					<th>Additional Charges</th>
					<th>Total Amount</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<%
				BillingService bs = new BillingService();
				List<Invoice> invoices = bs.getBillsByCustomerId(customerId);

				if (invoices != null) {
					int index = 1;
					for (Invoice invoice : invoices) {
						String reservationId = invoice.getReservationId();
						double roomCharges = invoice.getRoomCharges();
						double additionalCharges = invoice.getAdditionalCharges();
						double totalAmount = invoice.getTotalAmount();
				%>
				<tr>
					<td><%=index++%></td>
					<td><%=reservationId%></td>
					<td><%=roomCharges%></td>
					<td><%=additionalCharges%></td>
					<td><%=totalAmount%></td>

					<td>
						<form
							action="<%=request.getContextPath()%>/UserController?action=paymentUser"
							method="post">
							<input type="hidden" name="totalAmount" value="<%=totalAmount%>"
								readonly>
							<button type="submit">Pay</button>
						</form>
					</td>

				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>
	<script src="./js/billing.js"></script>

</body>
</html>