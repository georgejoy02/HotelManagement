<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Page</title>
    <script>
        function validatePaymentDetails() {
            const cardNumber = document.getElementById("cardNumber").value;
            const cardHolderName = document.getElementById("cardHolderName").value;
            const expiryDate = document.getElementById("expiryDate").value;
            const cvv = document.getElementById("cvv").value;

            if (cardNumber.length !== 16 || isNaN(cardNumber)) {
                alert("Please enter a valid 16-digit card number.");
                return false;
            }
            if (cardHolderName.length < 10) {
                alert("Card Holder Name must be at least 10 characters.");
                return false;
            }
            if (!expiryDate.match(/^(0[1-9]|1[0-2])\/\d{2}$/)) {
                alert("Please enter a valid expiry date (MM/YY).");
                return false;
            }
            if (cvv.length !== 3 || isNaN(cvv)) {
                alert("Please enter a valid 3-digit CVV.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <h2>Payment Page</h2>

    <form action="paymentConfirmation.jsp" method="post" onsubmit="return validatePaymentDetails()">
        <label for="billAmount">Bill Amount:</label>
        <input type="text" id="billAmount" name="billAmount" value="1500.00" readonly><br><br>

        <label for="paymentMode">Mode of Payment:</label>
        <select id="paymentMode" name="paymentMode">
            <option value="debit">Debit</option>
            <option value="credit">Credit</option>
        </select><br><br>

        <label for="cardNumber">Card Number:</label>
        <input type="text" id="cardNumber" name="cardNumber" minlength="16" maxlength="16"><br><br>

        <label for="cardHolderName">Card Holder Name:</label>
        <input type="text" id="cardHolderName" name="cardHolderName" minlength="10"><br><br>

        <label for="expiryDate">Expiry Date (MM/YY):</label>
        <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY"><br><br>

        <label for="cvv">CVV:</label>
        <input type="text" id="cvv" name="cvv" minlength="3" maxlength="3"><br><br>

        <button type="submit">Make Payment</button>
    </form>
</body>
</html>
