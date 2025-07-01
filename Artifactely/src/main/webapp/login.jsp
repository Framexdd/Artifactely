<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login - Artifactely</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
    <script src="${pageContext.request.contextPath}/scripts/loginValidation.js" defer></script>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="login-container">
    <h1>Accedi al tuo account</h1>

    <!-- Sezione per gli errori della servlet (form validation lato servlet -->
    <c:if test="${not empty errors}">
        <div class="error-messages">
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
        <label for="email" id="emailLabel">Email:</label>
        <input type="email" id="email" name="email" aria-required="true" />

        <label for="password" id="passwordLabel">Password:</label>
        <input type="password" id="password" name="password" aria-required="true" />

        <button type="submit">Accedi</button>
    </form>

    <p>Non hai un account? <a href="${pageContext.request.contextPath}/signup">Registrati qui</a></p>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
