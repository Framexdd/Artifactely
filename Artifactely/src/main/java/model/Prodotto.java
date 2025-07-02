package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Prodotto implements Serializable {
    private int idProdotto;
    private String nome;
    private String descrizione;
    private BigDecimal prezzo;
    private boolean valido;
    private boolean inEvidenza;
    private List<ImmagineProdotto> immagini;
    // Getters e Setters
    public int getIdProdotto() { return idProdotto; }
    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public BigDecimal getPrezzo() { return prezzo; }
    public void setPrezzo(BigDecimal prezzo) { this.prezzo = prezzo; }

    public boolean isValido() { return valido; }
    public void setValido(boolean valido) { this.valido = valido; }

    public boolean isInEvidenza() { return inEvidenza; }
    public void setInEvidenza(boolean inEvidenza) { this.inEvidenza = inEvidenza; }

    public List<ImmagineProdotto> getImmagini() { return immagini; }
    public void setImmagini(List<ImmagineProdotto> immagini) { this.immagini = immagini; }
}
