<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Profilo Utente - Artifactely</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo.png">
    <link href="${pageContext.request.contextPath}/css/user.css" rel="stylesheet">
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
<jsp:include page="header.jsp" />

<main>
    <!-- Recupera l'utente dalla sessione -->
    <c:set var="utente" value="${sessionScope.utente}" />

    <c:choose>
        <c:when test="${not empty utente}">
            <div class="user-info">
                <h2>Informazioni dell'account</h2>
                <p>Username: ${utente.username}</p>
                <p>Email: ${utente.email}</p>
                <p>Nome: ${utente.nome}</p>
                <p>Cognome: ${utente.cognome}</p>
                <c:if test="${utente.amministratore}">
                    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/adminPage.jsp'">Admin Page</button>
                </c:if>
            </div>

            <div class="logout-button">
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit">Logout</button>
                </form>
            </div>

            <div class="purchase-history">
                <h2>Cronologia acquisti</h2>
                <div class="orders">
                    <c:choose>
                        <c:when test="${not empty ordini}">
                            <c:forEach var="ordine" items="${ordini}">
                                <div class="order">
                                    <h3>Ordine #${ordine.idOrdine}</h3>
                                    <div class="order-info">
                                        <p><strong>Data:</strong> <fmt:formatDate value="${ordine.dataOrdineAsDate}" pattern="dd/MM/yyyy HH:mm" /></p>
                                        <p><strong>Totale:</strong> € <fmt:formatNumber value="${ordine.totale}" type="currency" currencySymbol="" /></p>
                                        <p><strong>Indirizzo:</strong> ${ordine.indirizzoSpedizione}</p>
                                    </div>
                                    <div class="order-products">
                                        <h3>Prodotti:</h3>
                                        <ul>
                                            <c:forEach var="ordineItem" items="${ordine.items}">
                                                <li>
                                                    <p><strong>Nome:</strong> ${ordineItem.prodotto.nome}</p>
                                                    <p><strong>Prezzo Unitario:</strong> € <fmt:formatNumber value="${ordineItem.prezzoUnitario}" type="currency" currencySymbol="" /></p>
                                                    <p><strong>Quantità:</strong> ${ordineItem.quantita}</p>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="no-orders">
                                <p>Non hai ancora effettuato acquisti.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="error-message">
                <p>Devi effettuare il login per visualizzare questa pagina.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>
