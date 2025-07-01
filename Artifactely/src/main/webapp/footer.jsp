<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/css/footer.css" rel="stylesheet" type="text/css">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
</head>
<body>
<footer id="footer">
    <p>Contattaci a: <a href="mailto:info@artifactely.com">info@artifactely.com</a> | <a href="tel:+0123456789">+01 2345 6789</a></p>
    <p>Seguici sui social:</p>
    <div class="social-icons">
        <a href="#" aria-label="Facebook">
            <img src="${pageContext.request.contextPath}/images/icons/facebook.png" alt="Facebook">
        </a>
        <a href="#" aria-label="Instagram">
            <img src="${pageContext.request.contextPath}/images/icons/instagram.png" alt="Instagram">
        </a>
        <a href="#" aria-label="LinkedIn">
            <img src="${pageContext.request.contextPath}/images/icons/linkedin.png" alt="LinkedIn">
        </a>
        <a href="#" aria-label="Twitter">
            <img src="${pageContext.request.contextPath}/images/icons/twitter.png" alt="Twitter">
        </a>
    </div>
</footer>
</body>
</html>
