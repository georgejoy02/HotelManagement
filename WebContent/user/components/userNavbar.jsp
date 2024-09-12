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
<html>
<head>
<meta charset="UTF-8">
<title>"Customer"</title>
</head>
<body>
<script src="<%=request.getContextPath()%>/static/js/navbar.js"></script>
	<div class="top-bar">
		<button
			onclick="navigate('<%=request.getContextPath()%>/user/dashboard.jsp')">Home</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/user/reservation.jsp')">Reservation</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/user/billing.jsp')">Billing</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/user/history.jsp')">History</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/user/bookings.jsp')">Bookings</button>
		<button
			onclick="navigate('<%=request.getContextPath()%>/user/support.jsp')">Contact
			Support</button>
		<a
			href="<%=request.getContextPath()%>/UserController?action=logoutUser"><button
				class="logoutButton">Logout</button></a>

	</div>
</body>
</html>