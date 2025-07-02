package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    public void doSave(Ordine ordine) throws SQLException {
        String sql = "INSERT INTO Ordine (email, data_ordine, totale, stato, indirizzo_spedizione, metodo_pagamento, ultime_quattro_cifre) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ordine.getEmail());
            ps.setTimestamp(2, Timestamp.valueOf(ordine.getDataOrdine()));
            ps.setBigDecimal(3, ordine.getTotale());
            ps.setString(4, ordine.getStato());
            ps.setString(5, ordine.getIndirizzoSpedizione());
            ps.setString(6, ordine.getMetodoPagamento());
            ps.setString(7, ordine.getUltimeQuattroCifre());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ordine.setIdOrdine(rs.getInt(1));
                }
            }
        }
    }

    public Ordine doRetrieveByKey(int idOrdine) throws SQLException {
        String sql = "SELECT * FROM Ordine WHERE id_ordine = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ordine ordine = new Ordine();
                    ordine.setIdOrdine(rs.getInt("id_ordine"));
                    ordine.setEmail(rs.getString("email"));
                    ordine.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                    ordine.setTotale(rs.getBigDecimal("totale"));
                    ordine.setStato(rs.getString("stato"));
                    ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
                    ordine.setUltimeQuattroCifre(rs.getString("ultime_quattro_cifre"));
                    return ordine;
                } else {
                    return null;
                }
            }
        }
    }

    public List<Ordine> doRetrieveByUser(String email) throws SQLException {
        String sql = "SELECT * FROM Ordine WHERE email = ? ORDER BY data_ordine DESC";

        List<Ordine> ordini = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine ordine = new Ordine();
                    ordine.setIdOrdine(rs.getInt("id_ordine"));
                    ordine.setEmail(rs.getString("email"));
                    ordine.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                    ordine.setTotale(rs.getBigDecimal("totale"));
                    ordine.setStato(rs.getString("stato"));
                    ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
                    ordine.setUltimeQuattroCifre(rs.getString("ultime_quattro_cifre"));
                    ordini.add(ordine);
                }
            }
        }

        return ordini;
    }

    public List<Ordine> doRetrieveAll() throws SQLException {
        String sql = "SELECT * FROM Ordine ORDER BY data_ordine DESC";

        List<Ordine> ordini = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setIdOrdine(rs.getInt("id_ordine"));
                ordine.setEmail(rs.getString("email"));
                ordine.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                ordine.setTotale(rs.getBigDecimal("totale"));
                ordine.setStato(rs.getString("stato"));
                ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
                ordine.setUltimeQuattroCifre(rs.getString("ultime_quattro_cifre"));
                ordini.add(ordine);
            }
        }

        return ordini;
    }
}
