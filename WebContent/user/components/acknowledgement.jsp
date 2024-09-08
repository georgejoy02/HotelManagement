<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="successModal" class="modal">
		<div class="modal-content">
			<span class="close" onclick="closeModal()">&times;</span>
			<p class="success-message">
				Consumer Registration Successful.<br> User ID:
				<%=request.getAttribute("customerId") != null ? request.getAttribute("customerId") : ""%>
			</p>
		</div>
	</div>
</body>
</html>