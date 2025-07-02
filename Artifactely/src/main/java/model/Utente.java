package model;

import java.io.Serializable;

public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private boolean amministratore;

    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    //Per motivi di sicurezza potrei eliminare questo metodo, DA RICORDARE
    public void setPassword(String password) { this.password = password; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public boolean isAmministratore() { return amministratore; }
    public void setAmministratore(boolean amministratore) { this.amministratore = amministratore; }
}
