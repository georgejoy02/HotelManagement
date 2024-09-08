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
<title>Dashboard</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/style.css">
<style>
.welcome-message {
    position: absolute;
    top: 100px;
    right: 20px;
    text-align: right;
    /* Removed background, padding, border, and box-shadow */
}

.welcome-message h2 {
    font-family: 'Arial', sans-serif; /* Choose a clean, modern font */
    font-size: 24px; /* Increase font size */
    color: #000000; /* Black text */
    letter-spacing: 1px; /* Add spacing between letters */
    font-weight: 600; /* Make the text bold */
    margin: 0;
    padding-top: 15px;
}

.welcome-message h2 span {
    color: #007bff; /* Optionally, highlight the username with a color */
    font-weight: 700; /* Make it stand out with a bold weight */
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



	<div class="dashboard-content">
		<div class="welcome-message">
			<h2>
				Welcome, <span><%=customerId%></span>!
			</h2>
		</div>
		<h3>Recent Activities</h3>
		<ul id="recent-activities">

		</ul>

		<h3>Upcoming Reservations</h3>
		<ul id="upcoming-reservations">

		</ul>

		<h3>Notifications</h3>
		<ul id="notifications">

		</ul>
	</div>


	<script src="<%=request.getContextPath()%>/static/js/user/dashboard.js"></script>

</body>

</html>