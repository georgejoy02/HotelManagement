function closeModal() {
    var modal = document.getElementById('successModal');
    if (modal) {
        modal.style.display = 'none';
		window.location.href = 'user/login.jsp';
    }
}

// Function to close the modal when clicking outside of it
window.onclick = function(event) {
    var modal = document.getElementById('successModal');
    if (modal && event.target === modal) {
        modal.style.display = 'none';
		window.location.href = 'user/login.jsp';
    }
}

//-------------------------------------------------------------------------------

//  validation code
const passwordInput = document.getElementById('password');
const passwordHelp = document.getElementById('passwordHelp');
const confirmPasswordInput = document.getElementById('confirmPassword');
const confirmPasswordHelp = document.getElementById('confirmPasswordHelp');

const customerName = document.getElementById('customerName');
const customerNameHelp = document.getElementById('customerNameHelp');
const customerId = document.getElementById('customerId');
const customerIdHelp = document.getElementById('customerIdHelp');

function validatePasswords() {
	const passwordsMatch = passwordInput.value === confirmPasswordInput.value;
	confirmPasswordHelp.style.display = passwordsMatch ? 'none' : 'block';
}

customerName.addEventListener('input', () => {
	customerNameHelp.style.display = customerName.validity.valid ? 'none' : 'block';

});

customerId.addEventListener('input', () => {
	customerIdHelp.style.display = customerId.validity.valid ? 'none' : 'block';
	
});

const emailHelp=  document.getElementById('emailHelp');
const email=  document.getElementById('email');

email.addEventListener('input', () => {
	emailHelp.style.display = email.validity.valid ? 'none' : 'block';
	
});
passwordInput.addEventListener('input', () => {
	passwordHelp.style.display = passwordInput.validity.valid ? 'none' : 'block';
	validatePasswords();
});

confirmPasswordInput.addEventListener('input', validatePasswords);
