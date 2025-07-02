package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import model.*;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CarrelloServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Visualizza il carrello
        HttpSession session = request.getSession();
        List<CarrelloItem> carrelloItems = (List<CarrelloItem>) session.getAttribute("carrelloItems");

        if (carrelloItems == null) {
            carrelloItems = new ArrayList<>();
            session.setAttribute("carrelloItems", carrelloItems);
        }

        // Recupera le informazioni dettagliate dei prodotti
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Map<Integer, Prodotto> prodottiMap = new HashMap<>();

        for (CarrelloItem item : carrelloItems) {
            try {
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(item.getIdProdotto());

                // Passaggio per permettere di visualizzare l'immagine nel carrello, DA RICORDARE
                ImmagineProdottoDAO immagineDAO = new ImmagineProdottoDAO();
                List<ImmagineProdotto> immagini = immagineDAO.doRetrieveByProdotto(prodotto.getIdProdotto());
                prodotto.setImmagini(immagini);

                prodottiMap.put(item.getIdProdotto(), prodotto);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }

        request.setAttribute("prodottiMap", prodottiMap);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        try {
            if (action == null) {
                response.sendRedirect(request.getContextPath() + "/carrello");
                return;
            }

            switch (action) {
                case "add":
                    aggiungiAlCarrello(request, session, utente);
                    break;
                case "update":
                    aggiornaQuantita(request, session, utente);
                    break;
                case "remove":
                    rimuoviDalCarrello(request, session, utente);
                    break;
                default:
                    break;
            }

            // Se la richiesta è stata fatta via AJAX, rispondi con JSON
            String ajax = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(ajax)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": true}");
            } else {
                response.sendRedirect(request.getContextPath() + "/carrello");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void aggiungiAlCarrello(HttpServletRequest request, HttpSession session, Utente utente) throws SQLException {
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));

        // Validazione lato server
        if (quantita <= 0) {
            return;
        }

        List<CarrelloItem> carrelloItems = (List<CarrelloItem>) session.getAttribute("carrelloItems");
        if (carrelloItems == null) {
            carrelloItems = new ArrayList<>();
        }

        boolean trovato = false;
        for (CarrelloItem item : carrelloItems) {
            if (item.getIdProdotto() == idProdotto) {
                item.setQuantita(item.getQuantita() + quantita);
                trovato = true;
                break;
            }
        }

        if (!trovato) {
            CarrelloItem nuovoItem = new CarrelloItem();
            nuovoItem.setIdProdotto(idProdotto);
            nuovoItem.setQuantita(quantita);
            carrelloItems.add(nuovoItem);
        }

        session.setAttribute("carrelloItems", carrelloItems);

        // Se l'utente è autenticato aggiorna il carrello nel database
        if (utente != null) {
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            Carrello carrello = carrelloDAO.doRetrieveByUser(utente.getEmail());
            if (carrello == null) {
                carrello = new Carrello();
                carrello.setEmail(utente.getEmail());
                carrelloDAO.doSave(carrello);
            }

            CarrelloItemDAO carrelloItemDAO = new CarrelloItemDAO();
            carrelloItemDAO.doSaveOrUpdate(carrello.getIdCarrello(), idProdotto, quantita);
        }
    }

    private void aggiornaQuantita(HttpServletRequest request, HttpSession session, Utente utente) throws SQLException {
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));

        // Validazione servlet
        if (quantita < 0) {
            return;
        }

        List<CarrelloItem> carrelloItems = (List<CarrelloItem>) session.getAttribute("carrelloItems");

        if (carrelloItems != null) {
            Iterator<CarrelloItem> iterator = carrelloItems.iterator();
            while (iterator.hasNext()) {
                CarrelloItem item = iterator.next();
                if (item.getIdProdotto() == idProdotto) {
                    if (quantita == 0) {
                        iterator.remove();
                    } else {
                        item.setQuantita(quantita);
                    }
                    break;
                }
            }
            session.setAttribute("carrelloItems", carrelloItems);
        }


        if (utente != null) {
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            Carrello carrello = carrelloDAO.doRetrieveByUser(utente.getEmail());
            if (carrello != null) {
                CarrelloItemDAO carrelloItemDAO = new CarrelloItemDAO();
                if (quantita == 0) {
                    carrelloItemDAO.doDelete(carrello.getIdCarrello(), idProdotto);
                } else {
                    carrelloItemDAO.doUpdateQuantita(carrello.getIdCarrello(), idProdotto, quantita);
                }
            }
        }
    }


    private void rimuoviDalCarrello(HttpServletRequest request, HttpSession session, Utente utente) throws SQLException {
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

        List<CarrelloItem> carrelloItems = (List<CarrelloItem>) session.getAttribute("carrelloItems");

        if (carrelloItems != null) {
            carrelloItems.removeIf(item -> item.getIdProdotto() == idProdotto);
            session.setAttribute("carrelloItems", carrelloItems);
        }


        if (utente != null) {
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            Carrello carrello = carrelloDAO.doRetrieveByUser(utente.getEmail());
            if (carrello != null) {
                CarrelloItemDAO carrelloItemDAO = new CarrelloItemDAO();
                carrelloItemDAO.doDelete(carrello.getIdCarrello(), idProdotto);
            }
        }
    }

    

}
