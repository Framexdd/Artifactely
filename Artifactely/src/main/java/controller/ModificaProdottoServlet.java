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

@WebServlet("/modificaProdotto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ModificaProdottoServlet extends HttpServlet {
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

        // Encoding per gestire i caratteri speciali
        request.setCharacterEncoding("UTF-8");

        // Recupera i dati (dal form)
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzoStr = request.getParameter("prezzo");
        String validoStr = request.getParameter("valido");
        String evidenzaStr = request.getParameter("evidenza");

        // Variabili per gestire gli errori
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder();

        int idProdotto = 0;
        if (idStr == null || idStr.trim().isEmpty()) {
            hasErrors = true;
            errorMessage.append("L'ID del prodotto è obbligatorio.<br>");
        } else {
            try {
                idProdotto = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                hasErrors = true;
                errorMessage.append("L'ID del prodotto non è valido.<br>");
            }
        }

        // Verifica se il prodotto esiste
        Prodotto prodotto = null;
        if (!hasErrors) {
            try {
                prodotto = prodottoDAO.doRetrieveByKey(idProdotto);
                if (prodotto == null) {
                    hasErrors = true;
                    errorMessage.append("Il prodotto con ID ").append(idProdotto).append(" non esiste.<br>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Errore nel recupero del prodotto.", e);
            }
        }

        if (hasErrors) {
            request.setAttribute("errorMessage", errorMessage.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPage.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Aggiorna i campi del prodotto solo se sono stati forniti
        if (nome != null && !nome.trim().isEmpty()) {
            prodotto.setNome(nome);
        }

        if (descrizione != null && !descrizione.trim().isEmpty()) {
            prodotto.setDescrizione(descrizione);
        }

        if (prezzoStr != null && !prezzoStr.trim().isEmpty()) {
            try {
                BigDecimal prezzo = new BigDecimal(prezzoStr);
                if (prezzo.compareTo(BigDecimal.ZERO) <= 0) {
                    errorMessage.append("Il prezzo deve essere maggiore di zero.<br>");
                    hasErrors = true;
                } else {
                    prodotto.setPrezzo(prezzo);
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Il prezzo non è valido.<br>");
                hasErrors = true;
            }
        }

        boolean valido = validoStr != null && (validoStr.equals("1") || validoStr.equalsIgnoreCase("true") || validoStr.equalsIgnoreCase("on"));
        prodotto.setValido(valido);

        boolean inEvidenza = evidenzaStr != null && (evidenzaStr.equals("1") || evidenzaStr.equalsIgnoreCase("true") || evidenzaStr.equalsIgnoreCase("on"));
        prodotto.setInEvidenza(inEvidenza);

        if (hasErrors) {
            request.setAttribute("errorMessage", errorMessage.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPage.jsp");
            dispatcher.forward(request, response);
            return;
        }



        // Gestione delle nuove immagini
        List<String> imageFileNames = new ArrayList<>();
        boolean hasNewImages = false; //Se non ha immagini diverse semplicemente ignoro
        for (Part part : request.getParts()) { //Il form e' multipart quindi cicliamo e agiamo solo sulle immagini
            if (part.getName().equals("immagini") && part.getSize() > 0) {

                //Cancello le immagini passate

                try {
                    // Recupera tutte le immagini esistenti associate al prodotto
                    List<ImmagineProdotto> immaginiEsistenti = immagineProdottoDAO.doRetrieveByProdotto(prodotto.getIdProdotto());
                    for (ImmagineProdotto immagineProdotto : immaginiEsistenti) {
                        // Elimina il file immagine dal server
                        String imagePath = getServletContext().getRealPath("") + File.separator + "images" + File.separator + "prodotti" + File.separator + immagineProdotto.getImmagine();
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            imageFile.delete();
                        }
                        // Elimina il record dal database
                        immagineProdottoDAO.doDelete(immagineProdotto.getIdImmagine());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new ServletException("Errore nell'eliminazione delle immagini esistenti.", e);
                }

                hasNewImages = true;
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


        if (hasErrors) {
            request.setAttribute("errorMessage", errorMessage.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPage.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Aggiorna il prodotto nel database
            prodottoDAO.doUpdate(prodotto);

            // Salva le nuove immagini nel database
            for (String fileName : imageFileNames) {
                ImmagineProdotto immagineProdotto = new ImmagineProdotto();
                immagineProdotto.setIdProdotto(prodotto.getIdProdotto());
                immagineProdotto.setImmagine(fileName);
                immagineProdottoDAO.doSave(immagineProdotto);
            }

            request.setAttribute("successMessage", "Prodotto modificato con successo!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPage.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore nell'aggiornamento del prodotto.", e);
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
