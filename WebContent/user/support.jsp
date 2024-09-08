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
<title>Contact Support</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/style.css">
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
		<h2>Contact Support</h2>
		<p>For more details, contact the customer support:</p>
		<p>Contact Number: 123-456-7890</p>
		<p>Email: support@example.com</p>
		<p>Address: 1234 Support St, Gift City, AH 56789</p>
		<form id="feedback-form">
			<label for="feedback">Feedback:</label>
			<textarea id="feedback" rows="4" required></textarea>
			<button type="submit">Submit Feedback</button>
		</form>
		<div id="feedback-success" style="display: none;">Thank you for
			your feedback!</div>
	</div>
	<script src="js/support.js"></script>
</body>

</html>