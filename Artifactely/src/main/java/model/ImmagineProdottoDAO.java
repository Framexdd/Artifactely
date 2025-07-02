package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImmagineProdottoDAO {

    public void doSave(ImmagineProdotto immagineProdotto) throws SQLException {
        String sql = "INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, immagineProdotto.getIdProdotto());
            ps.setString(2, immagineProdotto.getImmagine());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    immagineProdotto.setIdImmagine(rs.getInt(1));
                }
            }
        }
    }

    public boolean doDelete(int idImmagine) throws SQLException {
        String sql = "DELETE FROM Immagine_Prodotto WHERE id_immagine = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idImmagine);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<ImmagineProdotto> doRetrieveByProdotto(int idProdotto) throws SQLException {
        String sql = "SELECT * FROM Immagine_Prodotto WHERE id_prodotto = ?";

        List<ImmagineProdotto> immagini = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProdotto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImmagineProdotto immagine = new ImmagineProdotto();
                    immagine.setIdImmagine(rs.getInt("id_immagine"));
                    immagine.setIdProdotto(rs.getInt("id_prodotto"));
                    immagine.setImmagine(rs.getString("immagine"));
                    immagini.add(immagine);
                }
            }
        }

        return immagini;
    }

    public ImmagineProdotto doRetrieveByKey(int idImmagine) throws SQLException {
        String sql = "SELECT * FROM Immagine_Prodotto WHERE id_immagine = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idImmagine);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ImmagineProdotto immagine = new ImmagineProdotto();
                    immagine.setIdImmagine(rs.getInt("id_immagine"));
                    immagine.setIdProdotto(rs.getInt("id_prodotto"));
                    immagine.setImmagine(rs.getString("immagine"));
                    return immagine;
                } else {
                    return null;
                }
            }
        }
    }

    public List<ImmagineProdotto> doRetrieveAll() throws SQLException {
        String sql = "SELECT * FROM Immagine_Prodotto";

        List<ImmagineProdotto> immagini = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ImmagineProdotto immagine = new ImmagineProdotto();
                immagine.setIdImmagine(rs.getInt("id_immagine"));
                immagine.setIdProdotto(rs.getInt("id_prodotto"));
                immagine.setImmagine(rs.getString("immagine"));
                immagini.add(immagine);
            }
        }

        return immagini;
    } 
}
