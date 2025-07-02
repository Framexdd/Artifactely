package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Ordine;
import model.OrdineDAO;
import model.OrdineItem;
import model.OrdineItemDAO;
import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

@WebServlet("/adminOrdini")
public class AdminOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrdineDAO ordineDAO = new OrdineDAO();
    private OrdineItemDAO ordineItemDAO = new OrdineItemDAO();
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Verifica che l'utente sia un amministratore
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null || !utente.isAmministratore()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {

            List<Ordine> ordini = ordineDAO.doRetrieveAll();


            for (Ordine ordine : ordini) {
                List<OrdineItem> items = ordineItemDAO.doRetrieveByOrdine(ordine.getIdOrdine());
                for (OrdineItem item : items) {
                    Prodotto prodotto = prodottoDAO.doRetrieveByKey(item.getIdProdotto());
                    item.setProdotto(prodotto);
                }
                ordine.setItems(items);
            }


            request.setAttribute("ordini", ordini);


            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminOrders.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore nel recupero degli ordini.", e);
        }
    }
}
