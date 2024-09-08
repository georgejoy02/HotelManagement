<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Hotel iGrand</title>

<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="static/css/user/home.css">
</head>

<body>

	<!-- ---------------------header--------------------------------------------- -->
	<nav class="navbar background">
		<ul class="nav-list">
			<div>
				<img src="static/assets/icon.png" alt="logo" class="logo">
			</div>
			<li><a href="../register.html">Home </a></li>
			<li><a href="../register.html">About</a></li>
			<li><a href="../register.html"> Services</a></li>
			<li><a href="../support.html">Contact Us</a></li>
		</ul>
		<div class="rightnav">
			<a href="user/register.jsp">New User? Register</a>
			<!-- <button onclick="window.location.href='./admin/admin_login.html';" class="btn">Admin</button> -->
			<button onclick="window.location.href='user/login.jsp';" class="btn">Login</button>
		</div>
	</nav>


	<!-- --------------------------body-------------------------------------------- -->

	<section class="background firstSection">
		<div class="box-main">
			<div class="firstHalf">
				<p class="text-big">Hotel iGrand</p>
				<p class="text-small">With ‘Namaste’ as the enduring symbol of
					its brand experience and ‘Responsible Luxury’ as the guiding
					premise, iGrand Hotels are an archetype of the culture and ethos of
					each destination offering authentic, indigenous luxury experiences
					which are in harmony with the environment and society.</p>
			</div>

			<div class="secondHalf">
				<img src="static/assets/int1.jpg" alt="hotel Img">
			</div>

		</div>
	</section>


</body>

</html>


<!-- 

loginUser
userList
reservationList -->