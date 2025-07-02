package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.*;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {
    private static final long serialVersionUID = -86976045570564505L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        ImmagineProdottoDAO immagineProdottoDAO = new ImmagineProdottoDAO();

        try {

            List<Prodotto> prodottiList = prodottoDAO.doRetrieveAllValid();


            for (Prodotto prodotto : prodottiList) {
                List<ImmagineProdotto> immagini = immagineProdottoDAO.doRetrieveByProdotto(prodotto.getIdProdotto());
                prodotto.setImmagini(immagini);
            }


            request.setAttribute("prodottiList", prodottiList);


            RequestDispatcher dispatcher = request.getRequestDispatcher("shop.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
