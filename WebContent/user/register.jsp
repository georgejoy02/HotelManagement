<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Registration</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/user/register.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/user/acknowledgement.css">

</head>

<body>
	<div class="form-container">
		<h1>Customer Registration</h1>
		<%=request.getAttribute("status") != null ? request.getAttribute("status") : ""%>
		<form id="registrationForm"
			action="<%=request.getContextPath()%>/UserController?action=insertUser"
			method="post">
			<div class="form-group">
				<label for="customerName">Customer Name:</label> <input type="text"
					id="customerName" name="customerName" maxlength="50" required>
			</div>
			<div class="form-group">
				<label for="email">Email:</label> <input type="email" id="email"
					name="email" required>
			</div>
			<div class="form-group">
				<label for="mobile">Mobile Number:</label>
				<div class="mobile-input">
					<select id="countryCode" name="countryCode">
						<option value="+91">+91 (IN)</option>
					</select> <input type="tel" id="mobile" name="mobile" pattern="[0-9]{10}"
						placeholder="Enter 10-digit number" required>
				</div>
			</div>
			<div class="form-group">
				<label for="address">Address:</label> <input type="text"
					id="address" name="address" required>
			</div>

			<h2>Username and Password</h2>
			<div class="form-group">
				<label for="customerId">Customer ID:</label> <input type="text"
					id="customerId" name="customerId" maxlength="20" required>
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input type="password"
					id="password" name="password" maxlength="30"
					pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}" required> <small
					id="passwordHelp" style="display: none;">Password must be
					at least 8 characters long, include one uppercase letter, one
					lowercase letter, and one special character.</small>

			</div>
			<div class="form-group">
				<label for="confirmPassword">Confirm Password:</label> <input
					type="password" id="confirmPassword" name="confirmPassword"
					minlength="5" maxlength="30"
					pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}" required> <small
					id="confirmPasswordHelp" style="display: none;">Passwords
					do not match.</small>
			</div>

			<div class="form-group">
				<input type="submit" value="Register"> <input type="reset"
					value="Reset">
			</div>
		</form>

		<div id="modal-container"></div>
		<%
		// Retrieve the status and userId attributes
		String status = (String) request.getAttribute("status");
		String customerId = (String) request.getAttribute("customerId");

		if (status != null && status == "true") {
		%>
		<jsp:include page="components/acknowledgement.jsp">
			<jsp:param name="customerId" value="<%=customerId%>" />
		</jsp:include>
		<%
		}
		%>

	</div>


	<!-- Load the external JavaScript file after the global variables are defined -->
	<script src="<%=request.getContextPath()%>/static/js/user/register.js"></script>


</body>

</html>