package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Ordine {
    private int idOrdine;
    private String email;
    private LocalDateTime dataOrdine;
    private BigDecimal totale;
    private String stato;
    private String indirizzoSpedizione;
    private String metodoPagamento;
    private String ultimeQuattroCifre;
    private List<OrdineItem> items;

    // Getter e setter

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDateTime dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
        this.indirizzoSpedizione = indirizzoSpedizione;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getUltimeQuattroCifre() {
        return ultimeQuattroCifre;
    }

    public void setUltimeQuattroCifre(String ultimeQuattroCifre) {
        this.ultimeQuattroCifre = ultimeQuattroCifre;
    }

    public List<OrdineItem> getItems() {
        return items;
    }

    public void setItems(List<OrdineItem> items) {
        this.items = items;
    }

    // Metodo per ottenere dataOrdine come java.util.Date
    public Date getDataOrdineAsDate() {
        if (dataOrdine != null) {
            return Date.from(dataOrdine.atZone(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }
}
