package model;

import java.sql.*;
import java.time.LocalDateTime;

public class CarrelloDAO {

    public void doSave(Carrello carrello) throws SQLException {
        String sql = "INSERT INTO Carrello (email) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, carrello.getEmail());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    carrello.setIdCarrello(rs.getInt(1));
                }
            }
        }
    }


    public Carrello doRetrieveByUser(String email) throws SQLException {
        String sql = "SELECT * FROM Carrello WHERE email = ? ORDER BY data_creazione DESC LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carrello carrello = new Carrello();
                    carrello.setIdCarrello(rs.getInt("id_carrello"));
                    carrello.setEmail(rs.getString("email"));
                    return carrello;
                } else {
                    return null;
                }
            }
        }
    }

    public boolean doDelete(int idCarrello) throws SQLException {
        String sql = "DELETE FROM Carrello WHERE id_carrello = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrello);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
