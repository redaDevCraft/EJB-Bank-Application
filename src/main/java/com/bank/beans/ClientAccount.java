package com.bank.beans;

import java.util.ArrayList;
import java.util.List;

import com.bank.types.CompteType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_account")
public class ClientAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "client_id")
    private Client client;
    //meaning many clients can have the same account

    @ManyToMany
    @JoinTable(
            name = "client_account_clients",
            joinColumns = @JoinColumn(name = "client_account_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<Client> clients = new ArrayList<>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "compte_id")
    private Compte compte;

    // Constructors
    public ClientAccount() {
    }

    public ClientAccount(Client client, Compte compte) {
        this.client = client;
        this.compte = compte;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Integer getClientsCount() {
        return clients.size();
    }

    //helpers
    public void addClient(Client client) {
        if (this.compte.getCompteType() == CompteType.PARTAGE) {
            if (this.clients.size() >= 10) {
                throw new RuntimeException("Shared account cannot have more than 10 clients.");
            }
        } else if (this.compte.getCompteType() == CompteType.PRIVE) {
            if (!this.clients.isEmpty()) {
                throw new RuntimeException("Private account can only have one client.");
            }
        }
        this.clients.add(client);
        //and also add to clientaccount
        client.addClientAccount(this);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
        //and also remove from clientaccount
        client.removeClientAccount(this);
    }
}
