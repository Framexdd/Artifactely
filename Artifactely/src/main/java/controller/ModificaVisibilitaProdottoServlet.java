/* DEPRECATO
package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

@WebServlet("/modificaVisibilitaProdotto")
public class ModificaVisibilitaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verifica che l'utente sia un amministratore
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null || !utente.isAmministratore()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        request.setCharacterEncoding("UTF-8");


        String idStr = request.getParameter("id");
        String validoStr = request.getParameter("valido");
        String evidenzaStr = request.getParameter("evidenza");


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

        // Aggiorna i campi di visibilità
        boolean valido = validoStr != null && (validoStr.equals("1") || validoStr.equalsIgnoreCase("true") || validoStr.equalsIgnoreCase("on"));
        prodotto.setValido(valido);

        boolean inEvidenza = evidenzaStr != null && (evidenzaStr.equals("1") || evidenzaStr.equalsIgnoreCase("true") || evidenzaStr.equalsIgnoreCase("on"));
        prodotto.setInEvidenza(inEvidenza);

        try {
            // Aggiorna il prodotto nel database
            prodottoDAO.updateVisibility(prodotto);

            // Redirect alla pagina admin con messaggio di successo
            request.setAttribute("successMessage", "Visibilità del prodotto modificata con successo!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPage.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore nell'aggiornamento della visibilità del prodotto.", e);
        }
    }
}
*/
