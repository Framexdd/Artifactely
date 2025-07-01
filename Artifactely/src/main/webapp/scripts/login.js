const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

function validateForm() {
    let valid = true;

    const emailInput = document.getElementById('email');
    const emailError = document.getElementById('errorEmail');
    const emailValue = emailInput.value.trim();
    if (!emailPattern.test(emailValue)) {
        emailError.textContent = "Inserisci una email valida.";
        emailInput.classList.add('error');
        valid = false;
    } else {
        emailError.textContent = "";
        emailInput.classList.remove('error');
    }

    const passwordInput = document.getElementById('password');
    const passwordError = document.getElementById('errorPassword');
    const passwordValue = passwordInput.value.trim();
    if (passwordValue === '') {
        passwordError.textContent = "La password è obbligatoria.";
        passwordInput.classList.add('error');
        valid = false;
    } else {
        passwordError.textContent = "";
        passwordInput.classList.remove('error');
    }

    return valid;
}

document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('form').addEventListener('submit', function(event) {
        if (!validateForm()) {
            event.preventDefault();
        } else {
            // Resetta i messaggi di errore dal server se la validazione lato client passa
            const errorMessages = document.querySelector('.error-messages');
            if (errorMessages) {
                errorMessages.innerHTML = '';
            }
        }
    });
});
