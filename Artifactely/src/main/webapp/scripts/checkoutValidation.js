document.addEventListener('DOMContentLoaded', function() {
    const metodoPagamentoSelect = document.getElementById('metodoPagamento');
    const cartaInfoDiv = document.getElementById('carta-info');
    const numeroCartaInput = document.getElementById('numeroCarta');

    const numeroCartaPattern = /^\d{13,19}$/;

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

    metodoPagamentoSelect.addEventListener('change', function() {
        if (metodoPagamentoSelect.value === 'carta') {
            cartaInfoDiv.style.display = 'block';
        } else {
            cartaInfoDiv.style.display = 'none';
            numeroCartaInput.value = '';
            resetLabel("numeroCarta", "Numero della Carta:");
        }
    });

    function validateForm() {
        let valid = true;

        // Reset delle etichette prima della validazione
        resetLabel("indirizzoSpedizione", "Indirizzo di Spedizione:");
        resetLabel("metodoPagamento", "Metodo di Pagamento:");
        resetLabel("numeroCarta", "Numero della Carta:");

        // Validazione indirizzo di spedizione
        const indirizzoSpedizione = document.getElementById('indirizzoSpedizione').value.trim();
        if (indirizzoSpedizione === '') {
            showError("indirizzoSpedizione", "Indirizzo di Spedizione: Campo obbligatorio.");
            valid = false;
        }

        // Validazione metodo di pagamento
        if (metodoPagamentoSelect.value === '') {
            showError("metodoPagamento", "Metodo di Pagamento: Campo obbligatorio.");
            valid = false;
        }

        // Validazione numero della carta di credito
        if (metodoPagamentoSelect.value === 'carta') {
            const numeroCarta = numeroCartaInput.value.trim().replace(/\s+/g, '');
            if (!numeroCartaPattern.test(numeroCarta)) {
                showError("numeroCarta", "Numero della Carta: Inserisci un numero di carta valido (13-19 cifre).");
                valid = false;
            }
        }

        return valid;
    }

    // Ascoltatore per la validazione al submit
    document.querySelector('#checkout-form').addEventListener('submit', function(event) {
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
