package com.bank.beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.types.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = true)
    private BigDecimal montant;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime date;

    @Column(name = "type", nullable = false)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "compte_id", nullable = false)
    private Compte compte;

    @ManyToOne(optional = true)
    @JoinColumn(name = "destinationCompte_id", nullable = true)
    private Compte destinationCompte;

    public Transaction() {
    }

    public Transaction(BigDecimal montant, LocalDateTime date, TransactionType type, Compte compte, Compte destinationCompte) {
        this.montant = montant;
        this.date = date;
        this.type = type;
        this.compte = compte;
        this.destinationCompte = destinationCompte;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Compte getDestinationCompte() {
        return destinationCompte;
    }

    public void setDestinationCompte(Compte destinationCompte) {
        this.destinationCompte = destinationCompte;
    }
}
