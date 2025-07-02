package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignUpServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Inoltra alla pagina di registrazione
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ottieni i parametri dal form
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confermaPassword = request.getParameter("confermaPassword");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");

        // Lista per gli errori
        List<String> errors = new ArrayList<>();

        // Validazione lato server
        if (email == null || email.isEmpty()) {
            errors.add("L'email è obbligatoria.");
        }
        if (username == null || username.isEmpty()) {
            errors.add("Il nome utente è obbligatorio.");
        }
        if (password == null || password.isEmpty()) {
            errors.add("La password è obbligatoria.");
        }
        if (confermaPassword == null || confermaPassword.isEmpty()) {
            errors.add("La conferma della password è obbligatoria.");
        }
        if (password != null && !password.equals(confermaPassword)) {
            errors.add("Le password non coincidono.");
        }
        if (nome == null || nome.isEmpty()) {
            errors.add("Il nome è obbligatorio.");
        }
        if (cognome == null || cognome.isEmpty()) {
            errors.add("Il cognome è obbligatorio.");
        }

        if (!errors.isEmpty()) {
            // Se ci sono errori, rimandali alla JSP
            request.setAttribute("errors", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Controlla se l'email o l'username sono già registrati
        UtenteDAO utenteDAO = new UtenteDAO();
        try {
            Utente utenteEsistenteEmail = utenteDAO.doRetrieveByKey(email);
            Utente utenteEsistenteUsername = utenteDAO.doRetrieveByUsername(username);

            if (utenteEsistenteEmail != null) {
                errors.add("L'email è già registrata.");
            }
            if (utenteEsistenteUsername != null) {
                errors.add("Il nome utente è già in uso.");
            }

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Crea un nuovo utente
            Utente utente = new Utente();
            utente.setEmail(email);
            utente.setUsername(username);
            utente.setPassword(password); // La password viene hashata nel DAO
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setAmministratore(false);


            utenteDAO.doSave(utente);


            Carrello carrello = new Carrello();
            carrello.setEmail(email);

            CarrelloDAO carrelloDAO = new CarrelloDAO();
            carrelloDAO.doSave(carrello);


            HttpSession session = request.getSession();
            session.setAttribute("utente", utente);

            //unire item sessione con carrello

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

            response.sendRedirect(request.getContextPath() + "/home");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
