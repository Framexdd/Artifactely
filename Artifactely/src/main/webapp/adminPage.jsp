<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Pagina Amministratore - Artifactely</title>
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
    <script src="${pageContext.request.contextPath}/scripts/adminValidation.js" defer></script>
</head>
<body>
<jsp:include page="header.jsp" />

<main>
    <c:choose>
        <c:when test="${not empty utente && utente.amministratore}">
            <h1>Pagina Amministratore</h1>

            <!-- Messaggi di errore o successo -->
            <c:if test="${not empty errorMessage}">
                <div class="error-message">
                    <c:out value="${errorMessage}" escapeXml="false" />
                </div>
            </c:if>

            <c:if test="${not empty successMessage}">
                <div class="success-message">
                    <c:out value="${successMessage}" escapeXml="false" />
                </div>
            </c:if>

            <!-- Form per inserire un nuovo prodotto -->
            <div class="form-container">
                <h2>Inserisci Nuovo Prodotto</h2>
                <form id="inserisciProdottoForm" action="${pageContext.request.contextPath}/inserisciProdotto" method="post" enctype="multipart/form-data" onsubmit="return validateInserisciProdotto()">
                    <label for="nomeprodotto" id="nomeprodottoLabel">Nome:</label>
                    <input type="text" id="nomeprodotto" name="nomeprodotto">

                    <label for="descrizione" id="descrizioneLabel">Descrizione:</label>
                    <textarea id="descrizione" name="descrizione" rows="4"></textarea>

                    <label for="prezzo" id="prezzoLabel">Prezzo:</label>
                    <input type="number" id="prezzo" name="prezzo" step="0.01">

                    <label for="immagini" id="immaginiLabel">Immagini:</label>
                    <input type="file" id="immagini" name="immagini" accept="image/jpeg, image/png" multiple>

                    <button type="submit">Inserisci</button>
                </form>
            </div>

            <!-- Form per modificare un prodotto esistente -->
            <div class="form-container">
                <h2>Modifica Prodotto</h2>
                <form id="modificaProdottoForm" action="${pageContext.request.contextPath}/modificaProdotto" method="post" enctype="multipart/form-data" onsubmit="return validateModificaProdotto()">
                    <label for="id_modifica" id="id_modificaLabel">ID Prodotto:</label>
                    <input type="number" id="id_modifica" name="id">

                    <label for="nome_modifica" id="nome_modificaLabel">Nome:</label>
                    <input type="text" id="nome_modifica" name="nome">

                    <label for="descrizione_modifica" id="descrizione_modificaLabel">Descrizione:</label>
                    <textarea id="descrizione_modifica" name="descrizione" rows="4"></textarea>

                    <label for="prezzo_modifica" id="prezzo_modificaLabel">Prezzo:</label>
                    <input type="number" id="prezzo_modifica" name="prezzo" step="0.01">

                    <label for="immagini_modifica" id="immagini_modificaLabel">Aggiungi Immagini:</label>
                    <input type="file" id="immagini_modifica" name="immagini" accept="image/jpeg, image/png" multiple>

                    <button type="submit">Modifica</button>
                </form>
            </div>



            <div class="form-container">
                <h2>Modifica Visibilità Prodotto</h2>
                <form id="modificaVisibilitaProdottoForm" action="${pageContext.request.contextPath}/modificaVisibilitaProdotto" method="post">
                    <label for="id_visibilita" id="id_visibilitaLabel">ID Prodotto:</label>
                    <input type="number" id="id_visibilita" name="id">

                    <label for="valido_visibilita" id="valido_visibilitaLabel">Valido:</label>
                    <input type="checkbox" id="valido_visibilita" name="valido" value="1">

                    <label for="evidenza_visibilita" id="evidenza_visibilitaLabel">In evidenza:</label>
                    <input type="checkbox" id="evidenza_visibilita" name="evidenza" value="1">

                    <button type="submit">Modifica Visibilità</button>
                </form>
            </div>


            <div class="form-container">
                <h2>Visualizza Ordini</h2>
                <form id="visualizzaOrdiniForm" action="${pageContext.request.contextPath}/adminOrdini" method="get">
                    <button type="submit">Visualizza Ordini</button>
                </form>
            </div>

        </c:when>
        <c:otherwise>
            <div class="error-message">
                <p>Devi essere un amministratore per accedere a questa pagina.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>
