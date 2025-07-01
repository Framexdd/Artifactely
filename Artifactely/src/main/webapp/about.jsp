<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Siamo - Artifactely</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/about.css">
    <script> var contextPath = '<%= request.getContextPath() %>'; </script>
</head>
<body>
<jsp:include page="header.jsp" />

<section id="about-hero" style="background: url('<%= request.getContextPath() %>/images/about.webp') center;">
    <h2>Chi Siamo</h2>
    <h3>La nostra missione e i nostri valori</h3>
</section>

<section id="about-content">
    <div class="about-section left">
        <div class="text-content">
            <h2>La Nostra Storia</h2>
            <p>
                Benvenuti su Artifactely, il vostro punto di riferimento per oggetti antichi e autentici artefatti del passato. Fondato con la passione di preservare e diffondere il fascino di epoche lontane, Artifactely offre una selezione curata di pezzi che raccontano storie uniche e ineguagliabili. Dal collezionismo personale di oggetti d’antiquariato, siamo cresciuti fino a creare una comunità di appassionati che amano esplorare la bellezza storica e il valore culturale di ogni oggetto.
            </p>
        </div>
    </div>

    <div class="about-section right">
        <div class="text-content">
            <h2>I Nostri Valori</h2>
            <p>
                La nostra filosofia si basa sull'autenticità, la conservazione e la trasparenza. Ogni pezzo venduto da Artifactely è rigorosamente verificato per garantirne l'autenticità, e ci impegniamo a preservare il valore storico di ogni oggetto, rispettando le sue origini e il significato culturale. Collaboriamo con esperti e collezionisti per assicurarci che gli oggetti venduti mantengano intatto il loro valore, trasmettendo il fascino delle epoche passate ai nostri clienti.
            </p>
        </div>
    </div>

    <div class="about-section left">
        <div class="text-content">
            <h2>Il Nostro Impegno</h2>
            <p>
                Artifactely è dedicato a fornire un’esperienza di acquisto unica e coinvolgente per tutti gli appassionati di storia e antichità. La nostra missione è offrirvi l’opportunità di possedere oggetti che hanno resistito alla prova del tempo e che portano con sé il sapore di culture e civiltà lontane. Ogni acquisto è un viaggio nella storia e un modo per valorizzare l’eredità artistica e culturale. La vostra soddisfazione e la qualità del nostro servizio sono le nostre priorità assolute.
            </p>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp" />
</body>
</html>
