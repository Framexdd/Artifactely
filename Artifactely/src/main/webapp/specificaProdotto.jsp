<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>${prodotto.nome} - Dettagli Prodotto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/specificaProdotto.css">
    <script>
        var contextPath = '<%= request.getContextPath() %>';
    </script>
    <script src="${pageContext.request.contextPath}/scripts/aggiungiAlCarrello.js" defer></script>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="product-details-container">
    <c:if test="${not empty prodotto}">
        <div class="product-image">
            <c:choose>
                <c:when test="${not empty prodotto.immagini}">
                    <c:forEach var="immagine" items="${prodotto.immagini}">
                        <img src="${pageContext.request.contextPath}/images/prodotti/${immagine.immagine}" alt="${prodotto.nome}">
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/prodotti/default.png" alt="Immagine non disponibile">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="product-info">
            <c:choose>
                <c:when test="${utente != null && utente.amministratore}">
                    <h1>${prodotto.nome} ID: ${prodotto.idProdotto}</h1>
                </c:when>
                <c:otherwise>
                    <h1>${prodotto.nome}</h1>
                </c:otherwise>
            </c:choose>
            <p>${prodotto.descrizione}</p>
            <span class="price">€ ${prodotto.prezzo}</span>
            <c:choose>
                <c:when test="${prodotto.valido}">
                    <!-- Form per tutti gli utenti, autenticati o meno -->
                    <form onsubmit="event.preventDefault(); aggiungiAlCarrello(${prodotto.idProdotto}, document.getElementById('quantity').value);">
                        <label for="quantity">Quantità:</label>
                        <input type="number" id="quantity" name="quantity" value="1" min="1" max="100">
                        <button type="submit">Aggiungi al carrello</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <h2>${prodotto.nome} non è disponibile</h2>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
