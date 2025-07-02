package model;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {


    public void doSave(Utente utente) throws SQLException {
        String sql = "INSERT INTO Utente (email, username, password, nome, cognome, amministratore) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getUsername());

            // Hashing della password con BCrypt prima di salvarla nel database
            String hashedPassword = BCrypt.hashpw(utente.getPassword(), BCrypt.gensalt(12));
            ps.setString(3, hashedPassword);

            ps.setString(4, utente.getNome());
            ps.setString(5, utente.getCognome());
            ps.setBoolean(6, utente.isAmministratore());

            ps.executeUpdate();
        }
    }


    public boolean doDelete(String email) throws SQLException {
        String sql = "DELETE FROM Utente WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }


    public Utente doRetrieveByKey(String email) throws SQLException {
        String sql = "SELECT * FROM Utente WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente utente = new Utente();
                    utente.setEmail(rs.getString("email"));
                    utente.setUsername(rs.getString("username"));
                    utente.setPassword(rs.getString("password")); // Otteniamo la password hashata
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setAmministratore(rs.getBoolean("amministratore"));
                    return utente;
                } else {
                    return null;
                }
            }
        }
    }

    public List<Utente> doRetrieveAll() throws SQLException {
        String sql = "SELECT * FROM Utente";

        List<Utente> utenti = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Utente utente = new Utente();
                utente.setEmail(rs.getString("email"));
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setAmministratore(rs.getBoolean("amministratore"));
                utenti.add(utente);
            }
        }

        return utenti;
    }

    public Utente doRetrieveByUsername(String username) throws SQLException {
        String sql = "SELECT email, username, nome, cognome, amministratore FROM Utente WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente utente = new Utente();
                    utente.setEmail(rs.getString("email"));
                    utente.setUsername(rs.getString("username"));
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setAmministratore(rs.getBoolean("amministratore"));
                    return utente;
                } else {
                    return null;
                }
            }
        }
    }


    // Metodo per validare il login (verificare se email e password corrispondono)
    public boolean validateLogin(String email, String password) throws SQLException {
        // Recuperiamo l'utente dal database
        Utente utente = doRetrieveByKey(email);

        if (utente != null) {
            // Verifichiamo che la password fornita corrisponda all'hash nel database
            return BCrypt.checkpw(password, utente.getPassword());
        }

        // Se l'utente non esiste o la password non corrisponde, il login fallisce
        return false;
    }


}
