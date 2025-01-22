package com.bank.sessions;

import java.math.BigDecimal;
import java.util.List;

import com.bank.beans.Client;
import com.bank.beans.ClientAccount;
import com.bank.beans.Compte;
import com.bank.beans.Transaction;

import jakarta.ejb.Local;

@Local
public interface BankServiceLocal {

    Client createClient(Client client);

    Compte createCompte(Compte compte);

    List<Compte> getComptesForClient(Long clientId);

    List<ClientAccount> getAllComptes();

    Compte findCompteById(Long id);

    Client findClientByName(String nom);

    Client findClientById(Long id);

    Client findClientByEmail(String email);

    List<Client> getAllClients();

    // New methods for managing ClientAccount
    ClientAccount assignCompteToClient(Long clientId, Long compteId);

    void detachCompteFromClient(Long clientAccountId, Long clientId);

    Compte retrait(Long id, BigDecimal montant);

    Compte depot(Long id, BigDecimal montant);

    Compte virement(Long id, Long id2, BigDecimal montant);

    double consulterSolde(Long id);

    List<Transaction> getTransactionsForCompte(Compte compte);

    List<Transaction> getTransactionsForClient(Long clientId);

    List<Transaction> getClientTransactions(Long clientId);

    List<ClientAccount> getClientAccounts(Long clientId);

    List<ClientAccount> getClientAccountLignes();

    List<ClientAccount> getAssignedClientAccountLignes();

    List<ClientAccount> getClientAccountLignesBut(Long clientId);

    List<ClientAccount> getNoClientAccounts();

    ClientAccount getClientAccount(Long clientId, Long compteId);

    List<ClientAccount> getAvailabClientAccountsAccounts();
}
