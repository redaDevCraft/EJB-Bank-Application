package com.bank.beans;

import java.util.List;

import com.bank.types.ClientType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ClientType type;

    @ManyToMany(mappedBy = "clients", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ClientAccount> clientAccounts;

    // Constructors
    public Client() {
    }

    public Client(String nom, String prenom, String email, String password, ClientType type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public List<ClientAccount> getClientAccounts() {
        return clientAccounts;
    }

    public void setClientAccounts(List<ClientAccount> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    // Helper Methods
    public void addClientAccount(ClientAccount clientAccount) {
        clientAccounts.add(clientAccount);
        clientAccount.setClient(this);
    }

    public void removeClientAccount(ClientAccount clientAccount) {
        clientAccounts.remove(clientAccount);
        clientAccount.setClient(null);
    }
}
