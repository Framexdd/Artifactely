package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.*;

@WebServlet("/ProdottoSpecificato")
public class ProdottoSpecificoServlet extends HttpServlet {
    private static final long serialVersionUID = -8697651045570564505L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idProdottoParam = request.getParameter("id");

        if (idProdottoParam == null || idProdottoParam.isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        try {
            int idProdotto = Integer.parseInt(idProdottoParam);

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            ImmagineProdottoDAO immagineProdottoDAO = new ImmagineProdottoDAO();


            Prodotto prodotto = prodottoDAO.doRetrieveByKey(idProdotto);

            if (prodotto == null || !prodotto.isValido()) {

                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }

            // Immagini del prodotto
            List<ImmagineProdotto> immaginiProdotto = immagineProdottoDAO.doRetrieveByProdotto(idProdotto);


            prodotto.setImmagini(immaginiProdotto);


            request.setAttribute("prodotto", prodotto);


            HttpSession session = request.getSession();
            Utente utente = (Utente) session.getAttribute("utente");
            request.setAttribute("utente", utente);


            RequestDispatcher dispatcher = request.getRequestDispatcher("specificaProdotto.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect(request.getContextPath() + "/error.jsp");
        } catch (SQLException e) {

            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
