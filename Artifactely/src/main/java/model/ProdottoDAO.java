package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public void doSave(Prodotto prodotto) throws SQLException {
        String sql = "INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setBigDecimal(3, prodotto.getPrezzo());
            ps.setBoolean(4, prodotto.isValido());
            ps.setBoolean(5, prodotto.isInEvidenza());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    prodotto.setIdProdotto(rs.getInt(1));
                }
            }
        }
    }

    //Setto valido = 0 invece di rimuovere il prodotto dal database per evitare incoerenze sul database
    public boolean doDelete(int idProdotto) throws SQLException {
        String sql = "UPDATE Prodotto SET valido = 0 WHERE id_prodotto = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProdotto);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }


    public Prodotto doRetrieveByKey(int idProdotto) throws SQLException {
        String sql = "SELECT * FROM Prodotto WHERE id_prodotto = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProdotto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prodotto prodotto = new Prodotto();
                    prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                    prodotto.setNome(rs.getString("nome"));
                    prodotto.setDescrizione(rs.getString("descrizione"));
                    prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                    prodotto.setValido(rs.getBoolean("valido"));
                    prodotto.setInEvidenza(rs.getBoolean("in_evidenza"));

                    ImmagineProdottoDAO immagineDAO = new ImmagineProdottoDAO();
                    List<ImmagineProdotto> immagini = immagineDAO.doRetrieveByProdotto(idProdotto);
                    prodotto.setImmagini(immagini);

                    return prodotto;
                } else {
                    return null;
                }
            }
        }
    }

    public List<Prodotto> doRetrieveAll() throws SQLException {
        String sql = "SELECT * FROM Prodotto";

        List<Prodotto> prodotti = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                prodotto.setValido(rs.getBoolean("valido"));
                prodotto.setInEvidenza(rs.getBoolean("in_evidenza"));
                prodotti.add(prodotto);
            }
        }

        return prodotti;
    }

    //Per la pagina home
    public List<Prodotto> doRetrieveInEvidenza() throws SQLException {
        String sql = "SELECT * FROM Prodotto WHERE in_evidenza = 1 AND valido = 1";

        List<Prodotto> prodotti = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                prodotto.setValido(rs.getBoolean("valido"));
                prodotto.setInEvidenza(rs.getBoolean("in_evidenza"));

                // Recuperiamo le immagini associate al prodotto dalla tabella Immagine_Prodotto
                ImmagineProdottoDAO immagineDAO = new ImmagineProdottoDAO();
                List<ImmagineProdotto> immagini = immagineDAO.doRetrieveByProdotto(prodotto.getIdProdotto());
                prodotto.setImmagini(immagini);

                prodotti.add(prodotto);
            }
        }

        return prodotti;
    }

    public List<Prodotto> doRetrieveAllValid() throws SQLException {
        String sql = "SELECT * FROM Prodotto WHERE valido = 1";

        List<Prodotto> prodotti = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                prodotto.setValido(rs.getBoolean("valido"));
                prodotto.setInEvidenza(rs.getBoolean("in_evidenza"));
                prodotti.add(prodotto);
            }
        }

        return prodotti;
    }

    public void doUpdate(Prodotto prodotto) throws SQLException {
        String sql = "UPDATE Prodotto SET nome = ?, descrizione = ?, prezzo = ?, valido = ?, in_evidenza = ? WHERE id_prodotto = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setBigDecimal(3, prodotto.getPrezzo());
            ps.setBoolean(4, prodotto.isValido());
            ps.setBoolean(5, prodotto.isInEvidenza());
            ps.setInt(6, prodotto.getIdProdotto());

            ps.executeUpdate();
        }
    }

    public void updateVisibility(Prodotto prodotto) throws SQLException {
        String sql = "UPDATE Prodotto SET valido = ?, in_evidenza = ? WHERE id_prodotto = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, prodotto.isValido());
            ps.setBoolean(2, prodotto.isInEvidenza());
            ps.setInt(3, prodotto.getIdProdotto());

            ps.executeUpdate();
        }
    }


}
