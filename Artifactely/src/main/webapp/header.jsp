<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Artifactely</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet" type="text/css">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
    <script src="${pageContext.request.contextPath}/scripts/contatti.js" defer></script>
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/home">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
    </a>
    <nav class="main-nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/home" class="selected">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/shop">Negozio</a></li>
            <li><a href="${pageContext.request.contextPath}/about.jsp">About</a></li>
            <li><a id="contactLink" href="javascript:void(0);">Contatti</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/carrello">
                    <img src="${pageContext.request.contextPath}/images/icons/cart-icon.png" alt="Carrello" class="icon">
                </a>
            </li>
            <c:choose>
                <c:when test="${not empty utente}">
                    <li>
                        <a href="${pageContext.request.contextPath}/user">
                            <img src="${pageContext.request.contextPath}/images/icons/user-icon.png" alt="Profilo Utente" class="icon">
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="${pageContext.request.contextPath}/login">
                            <img src="${pageContext.request.contextPath}/images/icons/login-icon.png" alt="Login" class="icon">
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>
</body>
</html>
