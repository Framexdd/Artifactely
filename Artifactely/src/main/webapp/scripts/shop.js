document.addEventListener('DOMContentLoaded', () => {
    // Seleziona tutti i form della pagina prodotti
    document.querySelectorAll('.product-item form').forEach(form => {
        // event listener su ogni form per intercettare la submission
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // Impedisce il comportamento predefinito (refresh della pagina)
            event.stopPropagation(); //Impedicse la redirezione

            // Estrai l'id del prodotto dal form (l'id del form ha la struttura "form-{idProdotto}")
            const idProdotto = this.id.split('-')[1]; // otteniamo la aprte doppo al trattino


            aggiungiAlCarrello(idProdotto);
        });

        form.querySelector('button').addEventListener('click', function(event) {
            event.stopPropagation();
        });
    });
});