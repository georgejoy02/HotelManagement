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

label {
	display: block;
	margin-top: 10px;
	font-weight: bold; /* Makes the label stand out */
}

input[type="text"], select {
	width: 100%;
	padding: 10px;
	margin-top: 5px;
	margin-bottom: 15px; /* Add spacing between input fields */
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box; /* Ensures padding is included in the width */
}

input[type="submit"] {
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
	padding: 10px 20px;
	border-radius: 4px;
	display: block;
	margin: 20px auto; /* Center the button */
	width: 100%; /* Make button same width as inputs */
}

input[type="submit"]:hover {
	background-color: #45a049;
}

.rooms_container {
	max-width: 400px;
	margin: 0 auto; /* Center the form */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Add subtle shadow */
	background-color: #fff;
	border-radius: 8px; /* Optional: rounded corners for a softer look */
	padding: 20px; /* Add padding inside the container */
	margin-top: 50px;
}

.error-message {
	color: red;
	font-size: 0.875em; /* Slightly smaller font size for error messages */
	display: block;
	margin-top: 5px; /* Spacing above the error message */
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



	<div class="dashboard-content">
		<%=request.getAttribute("status") != null ? request.getAttribute("status") : ""%>
		<div class="welcome-message">
			<h2>
				Welcome, <span><%=adminId%></span>!
			</h2>

		</div>
		<div class="rooms_container">
			<h2>Room Entry Form</h2>

			<form id="roomForm" action="<%=request.getContextPath()%>/AdminController?action=addRoom" method="post">
				<label for="roomNumber">Room Number:</label> <input type="text"
					id="roomNumber" name="roomNumber" minlength="4" maxlength="4"
					required> <span id="roomNumberError" class="error-message"></span>

				<label for="floor">Floor:</label> <input type="text" id="floor"
					name="floor" minlength="1" maxlength="2" required> <span
					id="floorError" class="error-message"></span> <label for="roomType">Room
					Type:</label> <select id="roomType" name="roomType" required>
					<option value="Suite">Suite</option>
					<option value="Single">Single</option>
					<option value="Double">Double</option>
				</select> <span id="roomTypeError" class="error-message"></span> <label
					for="price">Price:</label> <input type="text" id="price"
					name="price" required> <span id="priceError"
					class="error-message"></span> <input type="submit" value="Submit">
			</form>


		</div>


	</div>

	<script>
document.addEventListener('DOMContentLoaded', function() {
    // Select form elements
    const form = document.getElementById('roomForm');
    const roomNumberInput = document.getElementById('roomNumber');
    const floorInput = document.getElementById('floor');
    const priceInput = document.getElementById('price');

    // Function to validate Room Number
    function validateRoomNumber() {
        const value = roomNumberInput.value;
        const errorSpan = document.getElementById('roomNumberError');
        const regex = /^[a-zA-Z0-9]{4}$/; // 4 characters: numbers or letters
        if (!regex.test(value)) {
            errorSpan.textContent = 'Room Number must be 4 digits or letters.';
            return false;
        } else {
            errorSpan.textContent = '';
            return true;
        }
    }

    // Function to validate Floor
    function validateFloor() {
        const value = floorInput.value;
        const errorSpan = document.getElementById('floorError');
        const number = parseInt(value, 10);
        if (isNaN(number) || number < 0 || number > 10) {
            errorSpan.textContent = 'Floor must be a number between 0 and 10.';
            return false;
        } else {
            errorSpan.textContent = '';
            return true;
        }
    }

    // Function to validate Price
    function validatePrice() {
    const value = priceInput.value;
    const errorSpan = document.getElementById('priceError');

    // Regular expression to match a valid number, optionally with decimals
    const regex = /^[0-9]+(\.[0-9]{1,2})?$/;

    if (!regex.test(value)) {
        errorSpan.textContent = 'Price must be a valid number';
        return false;
    } else {
        errorSpan.textContent = '';
        return true;
    }
}


    // Add event listeners for live validation
    roomNumberInput.addEventListener('input', validateRoomNumber);
    floorInput.addEventListener('input', validateFloor);
    priceInput.addEventListener('input', validatePrice);

    // Validate form on submit
    form.addEventListener('submit', function(event) {
        const isRoomNumberValid = validateRoomNumber();
        const isFloorValid = validateFloor();
        const isPriceValid = validatePrice();

        if (!isRoomNumberValid || !isFloorValid || !isPriceValid) {
            event.preventDefault(); // Prevent form submission if validation fails
        }
    });
});
</script>

	<script
		src="<%=request.getContextPath()%>/static/js/admin/adminDashboard.js"></script>

</body>

</html>