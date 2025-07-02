package model;

import java.io.Serializable;

public class ImmagineProdotto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idImmagine;
    private int idProdotto;
    private String immagine;

    // Getters e Setters
    public int getIdImmagine() { return idImmagine; }
    public void setIdImmagine(int idImmagine) { this.idImmagine = idImmagine; }

    public int getIdProdotto() { return idProdotto; }
    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }

    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }
}
