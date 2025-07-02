package model;

import java.io.Serializable;

public class CarrelloItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idCarrello;
    private int idProdotto;
    private int quantita;

    // Getters e Setters
    public int getIdCarrello() { return idCarrello; }
    public void setIdCarrello(int idCarrello) { this.idCarrello = idCarrello; }

    public int getIdProdotto() { return idProdotto; }
    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
}
