<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Shop - Artifactely</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <!-- Definizione di contextPath -->
    <script>
        var contextPath = '<%= request.getContextPath() %>';
    </script>
    <!-- Inclusione degli script che utilizzano contextPath -->
    <script src="${pageContext.request.contextPath}/scripts/aggiungiAlCarrello.js" defer></script>
    <script src="${pageContext.request.contextPath}/scripts/OrdinaPerPrezzoDOM.js" defer></script>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="shop-container">
    <div class="header-container">
        <div class="sort-button-container">
            <button id="sort-toggle" onclick="toggleSortOrder()">Ordina per Prezzo: Ascendente</button>
        </div>
        <h1>Benvenuto nel nostro Negozio</h1>
    </div>

    <div class="product-list" id="product-list">
        <c:forEach var="prodotto" items="${prodottiList}">
            <c:if test="${prodotto.valido}">
                <div class="product-item" data-price="${prodotto.prezzo}">
                    <a href="${pageContext.request.contextPath}/ProdottoSpecificato?id=${prodotto.idProdotto}" class="product-link">
                        <c:choose>
                            <c:when test="${not empty prodotto.immagini}">
                                <img src="${pageContext.request.contextPath}/images/prodotti/${prodotto.immagini[0].immagine}" alt="${prodotto.nome}">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/images/prodotti/default.png" alt="Immagine non disponibile">
                            </c:otherwise>
                        </c:choose>
                        <div class="product-item-name">
                            <h2>${prodotto.nome}</h2>
                        </div>
                        <span>€ ${prodotto.prezzo}</span>
                    </a>
                    <!-- Form per aggiungere al carrello -->
                    <form onsubmit="event.preventDefault(); aggiungiAlCarrello(${prodotto.idProdotto}, 1);" class="cart-form">
                        <button type="submit">Aggiungi al carrello</button>
                    </form>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
