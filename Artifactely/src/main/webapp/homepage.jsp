<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Artifactely</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homepage.css">
    <script>
        var contextPath = '<%= request.getContextPath() %>';
    </script>
    <!-- Inclusione degli script che utilizzano contextPath -->
    <script src="${pageContext.request.contextPath}/scripts/shop.js" defer></script>
    <script src="${pageContext.request.contextPath}/scripts/aggiungiAlCarrello.js" defer></script>
</head>
<body>
<jsp:include page="header.jsp" />

<section id="hero">
    <h2>Qualità Garantita</h2>
    <h3>Prezzi convenienti su ogni prodotto</h3>
    <button type="button" onclick="window.location.href=contextPath + '/shop.jsp'">Acquista Ora</button>
</section>

<section id="prodotti">
    <h2>Prodotti in evidenza</h2>
    <div class="product-list">
        <c:forEach var="prodotto" items="${prodottiInEvidenza}">
            <div class="product-item">
                <a href="${pageContext.request.contextPath}/ProdottoSpecificato?id=${prodotto.idProdotto}" class="product-link">
                    <div class="product-content">
                        <!-- Immagine -->
                        <c:choose>
                            <c:when test="${not empty prodotto.immagini}">
                                <img src="${pageContext.request.contextPath}/images/prodotti/${prodotto.immagini[0].immagine}" alt="${prodotto.nome}">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/images/prodotti/default.png" alt="Immagine non disponibile">
                            </c:otherwise>
                        </c:choose>

                        <!-- Nome prodotto -->
                        <div class="product-item-name">
                            <h2>${prodotto.nome}</h2>
                        </div>

                        <!-- Descrizione e prezzo -->
                        <p>${prodotto.descrizione}</p>
                        <span>€ ${prodotto.prezzo}</span>
                    </div>
                </a>
                <!-- Form fuori dal link cliccabile -->
                <form onsubmit="event.preventDefault(); aggiungiAlCarrello(${prodotto.idProdotto}, 1);" class="cart-form">
                    <button type="submit">Aggiungi al carrello</button>
                </form>
            </div>
        </c:forEach>
    </div>
</section>

<jsp:include page="footer.jsp" />

</body>
</html>
