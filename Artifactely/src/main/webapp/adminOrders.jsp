<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Ordini - Artifactely</title>
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet">
    <script> var contextPath = '${pageContext.request.contextPath}'; </script>
</head>
<body>
<jsp:include page="header.jsp" />

<main>
    <h1>Gestione Ordini</h1>

    <c:choose>
        <c:when test="${not empty ordini}">
            <c:forEach var="ordine" items="${ordini}">
                <div class="order">
                    <h2>Ordine #${ordine.idOrdine}</h2>
                    <div class="order-info">
                        <p><strong>Data:</strong> <fmt:formatDate value="${ordine.dataOrdineAsDate}" pattern="dd/MM/yyyy HH:mm" /></p>
                        <p><strong>Email Cliente:</strong> ${ordine.email}</p>
                        <p><strong>Totale:</strong> € <fmt:formatNumber value="${ordine.totale}" type="currency" currencySymbol="" /></p>
                        <p><strong>Stato:</strong> ${ordine.stato}</p>
                        <p><strong>Indirizzo di Spedizione:</strong> ${ordine.indirizzoSpedizione}</p>
                        <p><strong>Metodo di Pagamento:</strong> ${ordine.metodoPagamento}</p>
                        <c:if test="${not empty ordine.ultimeQuattroCifre}">
                            <p><strong>Ultime Quattro Cifre:</strong> ${ordine.ultimeQuattroCifre}</p>
                        </c:if>
                    </div>
                    <div class="order-items">
                        <h3>Prodotti Ordinati:</h3>
                        <table>
                            <thead>
                            <tr>
                                <th>Nome Prodotto</th>
                                <th>Quantità</th>
                                <th>Prezzo Unitario</th>
                                <th>Prezzo Totale</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${ordine.items}">
                                <tr>
                                    <td>${item.prodotto.nome}</td>
                                    <td>${item.quantita}</td>
                                    <td>€ <fmt:formatNumber value="${item.prezzoUnitario}" type="currency" currencySymbol="" /></td>
                                    <td>€ <fmt:formatNumber value="${item.prezzoUnitario * item.quantita}" type="currency" currencySymbol="" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Non ci sono ordini da visualizzare.</p>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>
