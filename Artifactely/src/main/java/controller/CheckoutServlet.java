package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import model.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckoutServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<CarrelloItem> carrelloItems = (List<CarrelloItem>) session.getAttribute("carrelloItems");

        if (carrelloItems == null || carrelloItems.isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        // Recupera le informazioni dettagliate dei prodotti
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Map<Integer, Prodotto> prodottiMap = new HashMap<>();

        for (CarrelloItem item : carrelloItems) {
            try {
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(item.getIdProdotto());
                prodottiMap.put(item.getIdProdotto(), prodotto);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }

        request.setAttribute("prodottiMap", prodottiMap);
        RequestDispatcher dispatcher = request.getRequestDispatcher("checkout.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();


        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null) {
            // Se non è loggato, reindirizza alla pagina di login
            response.sendRedirect(request.getContextPath() + "/login.jsp?redirect=checkout");
            return;
        }


        List<CarrelloItem> carrelloItems = (List<CarrelloItem>) session.getAttribute("carrelloItems");

        if (carrelloItems == null || carrelloItems.isEmpty()) {
            // Se il carrello è vuoto, reindirizza al carrello
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        // Validazione lato servlet checkout
        String indirizzoSpedizione = request.getParameter("indirizzoSpedizione");
        String metodoPagamento = request.getParameter("metodoPagamento");
        String numeroCarta = request.getParameter("numeroCarta");

        if (indirizzoSpedizione == null || indirizzoSpedizione.isEmpty() ||
                metodoPagamento == null || metodoPagamento.isEmpty() ||
                (metodoPagamento.equals("carta") && (numeroCarta == null || !numeroCarta.matches("\\d{13,19}")))) {

            request.setAttribute("errorMessage", "Per favore, compila tutti i campi richiesti con dati validi.");
            doGet(request, response);
            return;
        }

        // Se il metodo di pagamento è carta estraiamo le ultime 4 cifre
        String ultimeQuattroCifre = null;
        if (metodoPagamento.equals("carta")) {
            // Rimuovi eventuali spazi dal numero di carta
            numeroCarta = numeroCarta.replaceAll("\\s+", "");
            // Prendi le ultime 4 cifre
            ultimeQuattroCifre = numeroCarta.substring(numeroCarta.length() - 4);

        }

        // Calcola il totale dell'ordine
        BigDecimal totale = BigDecimal.ZERO;
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<OrdineItem> ordineItems = new ArrayList<>();

        try {
            for (CarrelloItem item : carrelloItems) {
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(item.getIdProdotto());
                BigDecimal prezzoUnitario = prodotto.getPrezzo();
                BigDecimal prezzoTotale = prezzoUnitario.multiply(BigDecimal.valueOf(item.getQuantita()));
                totale = totale.add(prezzoTotale);

                OrdineItem ordineItem = new OrdineItem();
                ordineItem.setIdProdotto(item.getIdProdotto());
                ordineItem.setQuantita(item.getQuantita());
                ordineItem.setPrezzoUnitario(prezzoUnitario);
                ordineItems.add(ordineItem);
            }

            // Crea l'ordine
            Ordine ordine = new Ordine();
            ordine.setEmail(utente.getEmail());
            ordine.setTotale(totale);
            ordine.setStato("In elaborazione");
            ordine.setIndirizzoSpedizione(indirizzoSpedizione);
            ordine.setMetodoPagamento(metodoPagamento);
            ordine.setUltimeQuattroCifre(ultimeQuattroCifre); // Salva solo le ultime 4 cifre
            ordine.setDataOrdine(LocalDateTime.now()); // Imposta la data dell'ordine

            OrdineDAO ordineDAO = new OrdineDAO();
            ordineDAO.doSave(ordine);

            // Salva gli articoli dell'ordine
            OrdineItemDAO ordineItemDAO = new OrdineItemDAO();
            for (OrdineItem ordineItem : ordineItems) {
                ordineItem.setIdOrdine(ordine.getIdOrdine());
                ordineItemDAO.doSave(ordineItem);
            }

            // Svuota
            session.removeAttribute("carrelloItems");

            // Recupera l'ordine appena creato
            Ordine ordineCompletato = ordineDAO.doRetrieveByKey(ordine.getIdOrdine());


            request.setAttribute("ordine", ordineCompletato);


            RequestDispatcher dispatcher = request.getRequestDispatcher("/confermaOrdine.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

}
