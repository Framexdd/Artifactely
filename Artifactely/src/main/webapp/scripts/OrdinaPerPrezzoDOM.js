let ascending = true;

function toggleSortOrder() {
    // Determina l'ordine in base allo stato corrente
    const order = ascending ? 'asc' : 'desc';


    sortProducts(order);

    // aggiornai l pulsante
    const sortToggleButton = document.getElementById('sort-toggle');
    sortToggleButton.textContent = ascending ? 'Ordina per Prezzo: Discendente' : 'Ordina per Prezzo: Ascendente';

    // toggle dell'ordine per il prossimo click
    ascending = !ascending;
}

function sortProducts(order) {
    const productList = document.getElementById('product-list');
    const products = Array.from(productList.getElementsByClassName('product-item'));


    products.sort((a, b) => {
        const priceA = parseFloat(a.getAttribute('data-price'));
        const priceB = parseFloat(b.getAttribute('data-price'));
        return order === 'asc' ? priceA - priceB : priceB - priceA;
    });

    // Pulisci tutto e rimetti i prodotti
    productList.innerHTML = '';
    products.forEach(product => productList.appendChild(product));
}
