package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        try {
            List<Prodotto> prodottiInEvidenza = prodottoDAO.doRetrieveInEvidenza();
            request.setAttribute("prodottiInEvidenza", prodottiInEvidenza);
            RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
