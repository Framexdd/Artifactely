<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Conferma Ordine - Artifactely</title>
    <link href="${pageContext.request.contextPath}/css/confermaOrdine.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp" />

<main>
    <h1>Grazie per il tuo ordine!</h1>
    <p>Il tuo ordine è stato ricevuto e sarà elaborato il prima possibile.</p>

    <h2>Dettagli dell'Ordine</h2>
    <c:if test="${not empty ordine}">
        <p>ID Ordine: <strong><c:out value="${ordine.idOrdine}" /></strong></p>
        <p>Data: <strong><fmt:formatDate value="${ordine.dataOrdineAsDate}" pattern="dd/MM/yyyy HH:mm" /></strong></p>
        <p>Totale: <strong>€ <fmt:formatNumber value="${ordine.totale}" type="currency" currencySymbol="" /></strong></p>
    </c:if>
    <c:if test="${empty ordine}">
        <p>Errore nel recupero dei dettagli dell'ordine.</p>
    </c:if>

    <a href="${pageContext.request.contextPath}/shop">Continua lo Shopping</a>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>
