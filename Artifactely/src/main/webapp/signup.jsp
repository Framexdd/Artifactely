<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Registrazione - Artifactely</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
    <script src="${pageContext.request.contextPath}/scripts/signupValidation.js" defer></script>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="signup-container">
    <h1>Crea un nuovo account</h1>

    <!-- Sezione per gli errori della servlet (form validation lato servlet) -->
    <c:if test="${not empty errors}">
        <div class="error-messages">
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/signup" method="post" id="signupForm">
        <label for="email" id="emailLabel">Email:</label>
        <input type="email" id="email" name="email" />

        <label for="username" id="usernameLabel">Nome utente:</label>
        <input type="text" id="username" name="username"  />

        <label for="password" id="passwordLabel">Password:</label>
        <input type="password" id="password" name="password"  />

        <label for="confermaPassword" id="confermaPasswordLabel">Conferma Password:</label>
        <input type="password" id="confermaPassword" name="confermaPassword"  />

        <label for="nome" id="nomeLabel">Nome:</label>
        <input type="text" id="nome" name="nome"  />

        <label for="cognome" id="cognomeLabel">Cognome:</label>
        <input type="text" id="cognome" name="cognome"  />

        <button type="submit">Registrati</button>
    </form>

    <p>Hai già un account? <a href="${pageContext.request.contextPath}/login">Accedi qui</a></p>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
