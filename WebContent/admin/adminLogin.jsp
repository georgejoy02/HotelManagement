<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Login</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/style.css">

<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

h2 {
	text-align: center;
	color: #333;
}

#login-formdiv {
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	max-width: 400px;
	width: 100%;
	margin: 0 auto;
}

label {
	display: block;
	margin-bottom: 8px;
	color: #333;
	font-weight: bold;
}

input[type="text"], input[type="password"] {
	width: 95%;
	padding: 10px;
	padding-right: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 16px;
	margin-bottom: 15px;
}

button {
	width: 100%;
	padding: 10px;
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

button:hover {
	background-color: #45a049;
}

#login-error {
	text-align: center;
	margin-top: 10px;
	color: red;
}

div {
	margin-bottom: 15px;
}

@media ( max-width : 600px) {
	form {
		padding: 15px;
	}
}
</style>
</head>

<body>
	<div id="login-formdiv">
		<h2>Admin Login</h2>
		<%=request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
		<form id="login-form"
			action="<%=request.getContextPath()%>/AdminController?action=loginAdmin"
			method="post">
			<div>
				<label for="login-user-id">Admin ID:</label> <input type="text"
					id="login-user-id" name="adminId" minlength="5" maxlength="20"
					required>
			</div>
			<div>
				<label for="login-password">Password:</label> <input type="password"
					id="login-password" name="password" maxlength="30" required>
			</div>

			<button type="submit">Login</button>

		</form>
		<div id="login-error" style="display: none; color: red;">Invalid
			Admin ID or Password</div>
	</div>
</body>

</html>