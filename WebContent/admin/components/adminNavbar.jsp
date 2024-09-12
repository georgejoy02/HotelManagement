<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<html>
<head>
<meta charset="UTF-8">
<title>"Admin"</title>
</head>
<body>
	<script src="<%=request.getContextPath()%>/static/js/navbar.js"></script>
	<div class="top-bar">
		<button
			onclick="navigate('<%=request.getContextPath()%>/admin/adminDashboard.jsp')">Home</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/admin/adminReservation.jsp')">Reservation</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/admin/adminBilling.jsp')">Billing</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/admin/adminHistory.jsp')">History</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/admin/adminBookings.jsp')">Bookings</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/admin/adminRoomStatus.jsp')">Room
			Status</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/admin/adminSupport.jsp')">Contact
			Support</button>
		<a
			href="<%=request.getContextPath()%>/AdminController?action=logoutAdmin"><button
				class="logoutButton">Logout</button></a>

	</div>
</body>
</html>