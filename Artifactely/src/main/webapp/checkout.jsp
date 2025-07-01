<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Checkout - Artifactely</title>
    <link href="${pageContext.request.contextPath}/css/checkout.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/scripts/checkoutValidation.js" defer></script>
</head>
<body>
<jsp:include page="header.jsp" />

<main>
    <h1>Checkout</h1>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <div class="checkout-container">
        <div class="order-summary">
            <h2>Riepilogo Ordine</h2>
            <!-- Dettagli del riepilogo dell'ordine -->
            <table>
                <thead>
                <tr>
                    <th>Immagine</th>
                    <th>Prodotto</th>
                    <th>Quantità</th>
                    <th>Prezzo</th>
                    <th>Totale</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="totaleOrdine" value="0" />
                <c:forEach var="item" items="${carrelloItems}">
                    <c:set var="prodotto" value="${prodottiMap[item.idProdotto]}" />
                    <c:set var="totaleItem" value="${prodotto.prezzo * item.quantita}" />
                    <c:set var="totaleOrdine" value="${totaleOrdine + totaleItem}" />

                    <tr style="text-align:center;">
                        <!-- Immagine -->
                        <td>
                            <c:choose>
                                <c:when test="${not empty prodotto.immagini}">
                                    <img src="${pageContext.request.contextPath}/images/prodotti/${prodotto.immagini[0].immagine}" alt="${prodotto.nome}" class="product-image">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/prodotti/default.png" alt="Immagine non disponibile" class="product-image">
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>${prodotto.nome}</td>
                        <td>${item.quantita}</td>
                        <td>€ <fmt:formatNumber value="${prodotto.prezzo}" type="currency" currencySymbol="" /></td>
                        <td>€ <fmt:formatNumber value="${totaleItem}" type="currency" currencySymbol="" /></td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" class="total-label">Totale Ordine:</td>
                    <td class="total-value">€ <fmt:formatNumber value="${totaleOrdine}" type="currency" currencySymbol="" /></td>
                </tr>
                </tfoot>
            </table>
        </div>

        <div class="checkout-form">
            <h2>Dettagli di Spedizione e Pagamento</h2>
            <form action="${pageContext.request.contextPath}/checkout" method="post" id="checkout-form">
                <label for="indirizzoSpedizione" id="indirizzoSpedizioneLabel">Indirizzo di Spedizione:</label>
                <textarea name="indirizzoSpedizione" id="indirizzoSpedizione"></textarea>

                <label for="metodoPagamento" id="metodoPagamentoLabel">Metodo di Pagamento:</label>
                <select name="metodoPagamento" id="metodoPagamento">
                    <option value="paypal">PayPal</option>
                    <option value="carta">Carta di Credito/Debito</option>
                    <option value="bonifico">Bonifico Bancario</option>
                </select>

                <div id="carta-info" style="display: none;">
                    <label for="numeroCarta" id="numeroCartaLabel">Numero della Carta:</label>
                    <input type="text" name="numeroCarta" id="numeroCarta" maxlength="19">
                </div>

                <button type="submit">Completa l'Ordine</button>
            </form>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>
