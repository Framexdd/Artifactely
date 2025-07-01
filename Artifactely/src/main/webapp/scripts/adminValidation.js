document.addEventListener('DOMContentLoaded', function() {
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


    window.validateInserisciProdotto = function() {
        let valid = true;

        resetLabel("nomeprodotto", "Nome:");
        resetLabel("descrizione", "Descrizione:");
        resetLabel("prezzo", "Prezzo:");
        resetLabel("immagini", "Immagini:");

        const nome = document.getElementById('nomeprodotto').value.trim();
        if (nome === '') {
            showError("nomeprodotto", "Nome: Campo obbligatorio.");
            valid = false;
        }

        const descrizione = document.getElementById('descrizione').value.trim();
        if (descrizione === '') {
            showError("descrizione", "Descrizione: Campo obbligatorio.");
            valid = false;
        }

        const prezzo = document.getElementById('prezzo').value.trim();
        if (prezzo === '' || isNaN(prezzo) || parseFloat(prezzo) <= 0) {
            showError("prezzo", "Prezzo: Inserisci un valore valido maggiore di zero.");
            valid = false;
        }

        const immagini = document.getElementById('immagini').files.length;
        if (immagini === 0) {
            showError("immagini", "Immagini: Devi caricare almeno un'immagine.");
            valid = false;
        }

        return valid;
    }


    window.validateModificaProdotto = function() {
        let valid = true;

        resetLabel("id_modifica", "ID Prodotto:");
        resetLabel("prezzo_modifica", "Prezzo:");

        const id = document.getElementById('id_modifica').value.trim();
        if (id === '' || isNaN(id) || parseInt(id) <= 0) {
            showError("id_modifica", "ID Prodotto: Inserisci un ID valido.");
            valid = false;
        }

        const prezzo = document.getElementById('prezzo_modifica').value.trim();
        if (prezzo !== '' && (isNaN(prezzo) || parseFloat(prezzo) <= 0)) {
            showError("prezzo_modifica", "Prezzo: Inserisci un valore valido maggiore di zero.");
            valid = false;
        }

        return valid;
    }
});
