<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Il Tuo Carrello - Artifactely</title>
    <link href="${pageContext.request.contextPath}/css/cart.css" rel="stylesheet">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
</head>
<body>
<jsp:include page="header.jsp" />

<main>
    <h1>Il Tuo Carrello</h1>

    <c:if test="${not empty carrelloItems}">
        <table>
            <thead>
            <tr>
                <th>Immagine</th>
                <th>Prodotto</th>
                <th>Prezzo</th>
                <th>Quantità</th>
                <th>Totale</th>
                <th>Azioni</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="totaleCarrello" value="0" />
            <c:forEach var="item" items="${carrelloItems}">
                <c:set var="prodotto" value="${prodottiMap[item.idProdotto]}" />
                <c:set var="totaleItem" value="${prodotto.prezzo * item.quantita}" />
                <c:set var="totaleCarrello" value="${totaleCarrello + totaleItem}" />

                <tr>
                    <!-- Colonna Immagine -->
                    <td>
                        <c:choose>
                            <c:when test="${not empty prodotto.immagini}">
                                <img src="${pageContext.request.contextPath}/images/prodotti/${prodotto.immagini[0].immagine}" alt="${prodotto.nome}" class="cart-product-image">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/images/prodotti/default.png" alt="Immagine non disponibile" class="cart-product-image">
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <!-- Colonna Prodotto -->
                    <td>${prodotto.nome}</td>

                    <td>€ <fmt:formatNumber value="${prodotto.prezzo}" type="currency" currencySymbol="" /></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/carrello" method="post" class="update-form">
                            <input type="hidden" name="action" value="update" />
                            <input type="hidden" name="idProdotto" value="${item.idProdotto}" />
                            <input type="number" name="quantita" value="${item.quantita}" min="0" required />
                            <button type="submit">Aggiorna</button>
                        </form>
                    </td>
                    <td>€ <fmt:formatNumber value="${totaleItem}" type="currency" currencySymbol="" /></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/carrello" method="post" class="remove-form">
                            <input type="hidden" name="action" value="remove" />
                            <input type="hidden" name="idProdotto" value="${item.idProdotto}" />
                            <button type="submit">Rimuovi</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="4" class="total-label">Totale Carrello:</td>
                <td colspan="2" class="total-value">€ <fmt:formatNumber value="${totaleCarrello}" type="currency" currencySymbol="" /></td>
            </tr>
            </tfoot>
        </table>
        <div class="checkout">
            <a href="${pageContext.request.contextPath}/checkout" class="checkout-button">Procedi al Checkout</a>
        </div>
    </c:if>

    <c:if test="${empty carrelloItems}">
        <p>Il tuo carrello è vuoto.</p>
        <a href="${pageContext.request.contextPath}/shop">Torna al Negozio</a>
    </c:if>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>
