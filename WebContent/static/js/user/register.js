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



// Password validation code
const passwordInput = document.getElementById('password');
const passwordHelp = document.getElementById('passwordHelp');
const confirmPasswordInput = document.getElementById('confirmPassword');
const confirmPasswordHelp = document.getElementById('confirmPasswordHelp');

function validatePasswords() {
	const passwordsMatch = passwordInput.value === confirmPasswordInput.value;
	confirmPasswordHelp.style.display = passwordsMatch ? 'none' : 'block';
}

passwordInput.addEventListener('input', () => {
	passwordHelp.style.display = passwordInput.validity.valid ? 'none' : 'block';
	validatePasswords();
});

confirmPasswordInput.addEventListener('input', validatePasswords);
