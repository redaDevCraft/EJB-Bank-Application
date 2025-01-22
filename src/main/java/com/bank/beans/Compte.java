package com.bank.beans;

import java.math.BigDecimal;
import java.util.List;

import com.bank.types.CompteType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "compte")
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private BigDecimal solde;

    @Enumerated(EnumType.STRING)
    private CompteType compteType; // PARTICULIER, PROFESSIONNEL, or BANQUIER

    @OneToMany(mappedBy = "compte", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientAccount> clientAccounts;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions;

    // Constructors
    public Compte() {
    }

    public Compte(String numero, BigDecimal solde, CompteType type) {
        this.numero = numero;
        this.solde = solde;
        this.compteType = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public CompteType getCompteType() {
        return compteType;
    }

    public void setCompteType(CompteType compteType) {
        this.compteType = compteType;
    }

    public List<ClientAccount> getClientAccounts() {
        return clientAccounts;
    }

    public void setClientAccounts(List<ClientAccount> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Helper Methods
    public void addClientAccount(ClientAccount clientAccount) {
        clientAccounts.add(clientAccount);
        clientAccount.setCompte(this);
    }

    public void removeClientAccount(ClientAccount clientAccount) {
        clientAccounts.remove(clientAccount);
        clientAccount.setCompte(null);
    }

    public long getSharedClientCount() {
        return clientAccounts.stream().filter(ca -> ca.getClient() != null).count();
    }
}
