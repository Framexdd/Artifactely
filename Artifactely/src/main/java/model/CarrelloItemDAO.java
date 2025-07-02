package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrelloItemDAO {

    public void doSave(CarrelloItem item) throws SQLException {
        String sql = "INSERT INTO CarrelloItem (id_carrello, id_prodotto, quantita) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getIdCarrello());
            ps.setInt(2, item.getIdProdotto());
            ps.setInt(3, item.getQuantita());

            ps.executeUpdate();
        }
    }

    public List<CarrelloItem> doRetrieveByCarrello(int idCarrello) throws SQLException {
        String sql = "SELECT * FROM CarrelloItem WHERE id_carrello = ?";

        List<CarrelloItem> items = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrello);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CarrelloItem item = new CarrelloItem();
                    item.setIdCarrello(rs.getInt("id_carrello"));
                    item.setIdProdotto(rs.getInt("id_prodotto"));
                    item.setQuantita(rs.getInt("quantita"));
                    items.add(item);
                }
            }
        }

        return items;
    }

    public boolean doDelete(int idCarrello, int idProdotto) throws SQLException {
        String sql = "DELETE FROM CarrelloItem WHERE id_carrello = ? AND id_prodotto = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrello);
            ps.setInt(2, idProdotto);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public void doUpdateQuantita(int idCarrello, int idProdotto, int quantita) throws SQLException {
        String sql = "UPDATE CarrelloItem SET quantita = ? WHERE id_carrello = ? AND id_prodotto = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantita);
            ps.setInt(2, idCarrello);
            ps.setInt(3, idProdotto);

            ps.executeUpdate();
        }
    }

    public void doSaveOrUpdate(int idCarrello, int idProdotto, int quantita) throws SQLException {
        String sql = "INSERT INTO CarrelloItem (id_carrello, id_prodotto, quantita) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantita = quantita + ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrello);
            ps.setInt(2, idProdotto);
            ps.setInt(3, quantita);
            ps.setInt(4, quantita);

            ps.executeUpdate();
        }
    }

}
