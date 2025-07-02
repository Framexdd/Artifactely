package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineItemDAO {

    public void doSave(OrdineItem item) throws SQLException {
        String sql = "INSERT INTO OrdineItem (id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getIdOrdine());
            ps.setInt(2, item.getIdProdotto());
            ps.setInt(3, item.getQuantita());
            ps.setBigDecimal(4, item.getPrezzoUnitario());

            ps.executeUpdate();
        }
    }

    public List<OrdineItem> doRetrieveByOrdine(int idOrdine) throws SQLException {
        String sql = "SELECT * FROM OrdineItem WHERE id_ordine = ?";

        List<OrdineItem> items = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineItem item = new OrdineItem();
                    item.setIdOrdine(rs.getInt("id_ordine"));
                    item.setIdProdotto(rs.getInt("id_prodotto"));
                    item.setQuantita(rs.getInt("quantita"));
                    item.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
                    items.add(item);
                }
            }
        }

        return items;
    }
}
