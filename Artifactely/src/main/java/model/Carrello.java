package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Carrello implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idCarrello;
    private String email;

    private LocalDateTime dataCreazione;

    // Getters e Setters
    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }



    public int getIdCarrello() { return idCarrello; }
    public void setIdCarrello(int idCarrello) { this.idCarrello = idCarrello; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
