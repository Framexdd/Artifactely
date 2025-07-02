package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import model.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    // doPost per gestire il login e unire i carrelli
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ottieni i parametri dal form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Lista per gli errori
        List<String> errors = new ArrayList<>();

        // Validazione lato server
        if (email == null || email.isEmpty()) {
            errors.add("L'email è obbligatoria.");
        }
        if (password == null || password.isEmpty()) {
            errors.add("La password è obbligatoria.");
        }

        if (!errors.isEmpty()) {
            // Se ci sono errori, rimandali alla JSP
            request.setAttribute("errors", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Validazione delle credenziali
        UtenteDAO utenteDAO = new UtenteDAO();
        try {
            boolean isValid = utenteDAO.validateLogin(email, password);

            if (isValid) {
                // Recupera l'utente dal database
                Utente utente = utenteDAO.doRetrieveByKey(email);

                // Imposta l'utente nella sessione
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);

                // Carica il carrello dell'utente dal database
                CarrelloDAO carrelloDAO = new CarrelloDAO();
                Carrello carrello = carrelloDAO.doRetrieveByUser(email);
                if (carrello == null) {
                    // Se il carrello non esiste, ne crea uno nuovo
                    carrello = new Carrello();
                    carrello.setEmail(email);
                    carrelloDAO.doSave(carrello);
                }

                // Carica gli elementi del carrello dal database
                CarrelloItemDAO carrelloItemDAO = new CarrelloItemDAO();
                List<CarrelloItem> dbCarrelloItems = carrelloItemDAO.doRetrieveByCarrello(carrello.getIdCarrello());

                // Recupera il carrello dalla sessione (non autenticato)
                List<CarrelloItem> sessionCarrelloItems = (List<CarrelloItem>) session.getAttribute("carrelloItems");

                // unione carrelli
                if (sessionCarrelloItems != null && !sessionCarrelloItems.isEmpty()) {
                    // Creiamo una mappa per facilitare l'unione
                    Map<Integer, CarrelloItem> mergedItemsMap = new HashMap<>();

                    // Aggiungiamo gli elementi del carrello dal database alla mappa
                    for (CarrelloItem dbItem : dbCarrelloItems) {
                        mergedItemsMap.put(dbItem.getIdProdotto(), dbItem);
                    }

                    // Per ogni elemento nel carrello della sessione
                    for (CarrelloItem sessionItem : sessionCarrelloItems) {
                        int idProdotto = sessionItem.getIdProdotto();
                        if (mergedItemsMap.containsKey(idProdotto)) {
                            // Se l'elemento esiste già, sommiamo le quantità
                            CarrelloItem existingItem = mergedItemsMap.get(idProdotto);
                            existingItem.setQuantita(existingItem.getQuantita() + sessionItem.getQuantita());
                            // Aggiorniamo la quantità nel database
                            carrelloItemDAO.doUpdateQuantita(carrello.getIdCarrello(), idProdotto, existingItem.getQuantita());
                        } else {
                            // Se l'elemento non esiste, lo aggiungiamo
                            sessionItem.setIdCarrello(carrello.getIdCarrello());
                            carrelloItemDAO.doSave(sessionItem);
                            mergedItemsMap.put(idProdotto, sessionItem);
                        }
                    }

                    // Aggiorniamo la lista degli elementi del carrello dal database
                    dbCarrelloItems = carrelloItemDAO.doRetrieveByCarrello(carrello.getIdCarrello());

                    // Rimuoviamo il carrello dalla sessione (non autenticato)
                    session.removeAttribute("carrelloItems");
                }

                // Impostiamo il carrello e gli elementi nella sessione
                session.setAttribute("carrello", carrello);
                session.setAttribute("carrelloItems", dbCarrelloItems);

                // Carica gli ordini dell'utente
                OrdineDAO ordineDAO = new OrdineDAO();
                List<Ordine> ordini = ordineDAO.doRetrieveByUser(email);
                session.setAttribute("ordini", ordini);

                // Reindirizza alla home page o dashboard utente
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                // Credenziali non valide
                errors.add("Email o password errati.");
                request.setAttribute("errors", errors);
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
