<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Artifactely</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
</head>
<body>
<jsp:include page="header.jsp" />
<main>
    <section id="error">
        <h1>Oopsie Doopsie!</h1>
        <h2>Qualcosa è andato storto</h2>
        <p>Ci scusiamo per il disagio, un errore inaspettato ha impedito il corretto funzionamento del sito. Please, riprova più tardi, nel mentre prova a tornare alla home.</p>
        <button onclick="window.location.href='homepage.jsp'">Ritorna alla Homepage</button>
    </section>
</main>
<jsp:include page="footer.jsp" />
</body>
</html>
