package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.ImmagineProdotto;
import model.ImmagineProdottoDAO;
import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

@WebServlet("/inserisciProdotto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB per file
        maxRequestSize = 1024 * 1024 * 50    // 50 MB totali
)
public class InserisciProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProdottoDAO prodottoDAO = new ProdottoDAO();
    private ImmagineProdottoDAO immagineProdottoDAO = new ImmagineProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verifica che l'utente sia un amministratore
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null || !utente.isAmministratore()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Imposta l'encoding per gestire i caratteri speciali
        request.setCharacterEncoding("UTF-8");

        // Recupera i dati dal form
        String nome = request.getParameter("nomeprodotto");
        String descrizione = request.getParameter("descrizione");
        String prezzoStr = request.getParameter("prezzo");
        String evidenzaStr = request.getParameter("evidenza");

        // Inizializza variabili per gestire gli errori
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder();

        // Validazione dei campi
        if (nome == null || nome.trim().isEmpty()) {
            hasErrors = true;
            errorMessage.append("Il nome del prodotto è obbligatorio.<br>");
        }

        if (descrizione == null || descrizione.trim().isEmpty()) {
            hasErrors = true;
            errorMessage.append("La descrizione è obbligatoria.<br>");
        }

        BigDecimal prezzo = null;
        if (prezzoStr == null || prezzoStr.trim().isEmpty()) {
            hasErrors = true;
            errorMessage.append("Il prezzo è obbligatorio.<br>");
        } else {
            try {
                prezzo = new BigDecimal(prezzoStr);
                if (prezzo.compareTo(BigDecimal.ZERO) <= 0) {
                    hasErrors = true;
                    errorMessage.append("Il prezzo deve essere maggiore di zero.<br>");
                }
            } catch (NumberFormatException e) {
                hasErrors = true;
                errorMessage.append("Il prezzo non è valido.<br>");
            }
        }

        boolean inEvidenza = "1".equals(evidenzaStr) || "true".equalsIgnoreCase(evidenzaStr) || "on".equalsIgnoreCase(evidenzaStr);

        // Gestione delle immagini
        List<String> imageFileNames = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getName().equals("immagini") && part.getSize() > 0) {
                String contentType = part.getContentType();
                if (!contentType.startsWith("image/")) {
                    hasErrors = true;
                    errorMessage.append("Uno dei file caricati non è un'immagine valida.<br>");
                } else {
                    String extension = getFileExtension(part.getSubmittedFileName());
                    String fileName = UUID.randomUUID().toString() + "." + extension;
                    imageFileNames.add(fileName);

                    // Percorso per salvare l'immagine
                    String uploadPath = getServletContext().getRealPath("") + File.separator + "images" + File.separator + "prodotti";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    // Salva il file sul server
                    part.write(uploadPath + File.separator + fileName);
                }
            }
        }

        if (imageFileNames.isEmpty()) {
            hasErrors = true;
            errorMessage.append("È obbligatorio caricare almeno un'immagine del prodotto.<br>");
        }

        // Se ci sono errori, mostra un messaggio all'amministratore
        if (hasErrors) {
            request.setAttribute("errorMessage", errorMessage.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPage.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Crea un nuovo oggetto Prodotto
        Prodotto prodotto = new Prodotto();
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setValido(true); // Il prodotto è valido di default
        prodotto.setInEvidenza(inEvidenza);

        try {
            // Salva il prodotto nel database
            prodottoDAO.doSave(prodotto);

            // Salva le immagini nel database
            for (String fileName : imageFileNames) {
                ImmagineProdotto immagineProdotto = new ImmagineProdotto();
                immagineProdotto.setIdProdotto(prodotto.getIdProdotto());
                immagineProdotto.setImmagine(fileName);
                immagineProdottoDAO.doSave(immagineProdotto);
            }

            // Redirect alla pagina admin con messaggio di successo
            request.setAttribute("successMessage", "Prodotto inserito con successo!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPage.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore nel salvataggio del prodotto.", e);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        } else {
            return "";
        }
    }
}
