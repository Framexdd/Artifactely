document.addEventListener('DOMContentLoaded', function() {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    function showError(id, message) {
        const label = document.getElementById(id + "Label");
        label.textContent = message;
        label.style.color = "red";
    }

    function resetLabel(id, originalText) {
        const label = document.getElementById(id + "Label");
        label.textContent = originalText;
        label.style.color = ""; // Colore di default
    }

    function validateForm() {
        let valid = true;

        // Reset delle etichette prima della validazione
        resetLabel("email", "Email:");
        resetLabel("password", "Password:");

        // Validazione email
        const emailInput = document.getElementById('email');
        const emailValue = emailInput.value.trim();
        if (emailValue === "" || !emailPattern.test(emailValue)) {
            showError("email", "Email: Inserisci una email valida.");
            valid = false;
        }

        // Validazione password (non vuota)
        const passwordInput = document.getElementById('password');
        const passwordValue = passwordInput.value.trim();
        if (passwordValue === "") {
            showError("password", "Password: La password è obbligatoria.");
            valid = false;
        }

        return valid;
    }

    // Ascoltatore per la validazione al submit
    document.querySelector('#loginForm').addEventListener('submit', function(event) {
        if (!validateForm()) {
            event.preventDefault(); // Blocca l'invio del form se non valido
        } else {
            // Cancella eventuali errori server-side se la validazione client-side è superata
            const errorMessages = document.querySelector('.error-messages');
            if (errorMessages) {
                errorMessages.innerHTML = '';
            }
        }
    });
});
