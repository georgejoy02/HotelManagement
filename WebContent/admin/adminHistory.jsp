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
	<jsp:include page="components/adminNavbar.jsp">
		<jsp:param name="adminId" value="adminId" />
	</jsp:include>
	<%
	}
	%>
	<div class="Outercontainer"></div>
</body>

</html>