document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signupForm");

    signupForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Blocca l'invio del modulo per la convalida

        const email = document.getElementById("email").value.trim();
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;
        const confermaPassword = document.getElementById("confermaPassword").value;
        const nome = document.getElementById("nome").value.trim();
        const cognome = document.getElementById("cognome").value.trim();

        let valid = true;


        function showError(id, message) {
            const label = document.getElementById(id + "Label");
            label.textContent = message;
            label.style.color = "red";
            valid = false;
        }


        function resetLabel(id, originalText) {
            const label = document.getElementById(id + "Label");
            label.textContent = originalText;
            label.style.color = ""; // Colore di default
        }


        resetLabel("email", "Email:");
        resetLabel("username", "Nome utente:");
        resetLabel("password", "Password:");
        resetLabel("confermaPassword", "Conferma Password:");
        resetLabel("nome", "Nome:");
        resetLabel("cognome", "Cognome:");

        // Validazione email
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if ((email === "") || (!emailPattern.test(email))) {
            showError("email", "Email: Formato email non valido");
        }

        // Validazione username (niente spazi, minimo 3 caratteri)
        const usernamePattern = /^\S{3,}$/;
        if ((username === "") || (!usernamePattern.test(username))) {
            showError("username", "Nome utente: Minimo 3 caratteri senza spazi");
        }

        // Validazione password (almeno 8 caratteri, almeno una lettera e un numero)
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        if ((password === "") || (!passwordPattern.test(password))) {
            showError("password", "Password: Minimo 8 caratteri, con lettere e numeri");
        }

        // Conferma password
        if ((confermaPassword === "") || (password !== confermaPassword)) {
            showError("confermaPassword", "Conferma Password: Le password non coincidono");
        }

        // Validazione nome (niente spazi e almeno 2 caratteri)
        const namePattern = /^[A-Za-z]{2,}$/;
        if ((nome === "") || (!namePattern.test(nome))) {
            showError("nome", "Nome: Minimo 2 lettere, senza spazi o numeri");
        }

        // Validazione cognome (niente spazi e almeno 2 caratteri)
        if ((cognome === "") || (!namePattern.test(cognome))) {
            showError("cognome", "Cognome: Minimo 2 lettere, senza spazi o numeri");
        }

        // se tutto e' andato a buon fine invia
        if (valid) {
            signupForm.submit();
        }
    });
});
