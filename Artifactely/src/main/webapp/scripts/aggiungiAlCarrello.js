function aggiungiAlCarrello(idProdotto, quantita) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', contextPath + '/carrello', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

    // Definisci i parametri come stringa URL-encoded
    let params = 'action=add&idProdotto=' + encodeURIComponent(idProdotto) + '&quantita=' + encodeURIComponent(quantita);


    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) { //
            if (xhr.status === 200) { //
                let data = JSON.parse(xhr.responseText);
                if (data.success) {
                    alert('Prodotto aggiunto al carrello!');
                } else {
                    alert('Errore nell\'aggiungere il prodotto al carrello.');
                }
            } else {
                console.error('Errore nella richiesta:', xhr.statusText);
            }
        }
    };

    // Invia la richiesta con i parametri
    xhr.send(params);
}
