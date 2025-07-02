package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.*;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrdineDAO ordineDAO = new OrdineDAO();
    private OrdineItemDAO ordineItemDAO = new OrdineItemDAO();
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utente utente = (Utente) request.getSession().getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {

            List<Ordine> ordini = ordineDAO.doRetrieveByUser(utente.getEmail());

            // Per ogni ordine, recuperiamo gli item e i dettagli dei prodotti
            for (Ordine ordine : ordini) {
                List<OrdineItem> items = ordineItemDAO.doRetrieveByOrdine(ordine.getIdOrdine());
                for (OrdineItem item : items) {
                    Prodotto prodotto = prodottoDAO.doRetrieveByKey(item.getIdProdotto());
                    item.setProdotto(prodotto);
                }
                ordine.setItems(items);
            }

            // Imposta gli ordini nella richiesta
            request.setAttribute("ordini", ordini);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);
    }
}
