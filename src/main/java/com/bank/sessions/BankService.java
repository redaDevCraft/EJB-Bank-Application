package com.bank.sessions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.bank.beans.Client;
import com.bank.beans.ClientAccount;
import com.bank.beans.Compte;
import com.bank.beans.Transaction;
import com.bank.types.CompteType;
import com.bank.types.TransactionType;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Stateless
public class BankService implements BankServiceLocal {

    @PersistenceContext(unitName = "FSTBankPU")
    private EntityManager em;

    // Create
    @Override
    public Client createClient(Client client) {
        em.persist(client);
        return client;
    }

    @Override
    public Compte createCompte(Compte compte) {
        em.persist(compte);
        em.persist(new ClientAccount(null, compte));
        return compte;
    }

    @Override
    @Transactional
    public ClientAccount assignCompteToClient(Long clientId, Long compteId) {
        if (clientId == null || compteId == null) {
            throw new IllegalArgumentException("Client ID and Compte ID must not be null.");
        }

        // Fetch the client by ID
        Client client = findClientById(clientId);
        if (client == null) {
            throw new RuntimeException("Client not found with ID: " + clientId);
        }

        // Check if the client already has the specified account via the join table using JPQL
        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(ca) FROM ClientAccount ca JOIN ca.clients c WHERE ca.compte.id = :compteId AND c.id = :clientId",
                Long.class
        );
        countQuery.setParameter("compteId", compteId);
        countQuery.setParameter("clientId", clientId);
        Long count = countQuery.getSingleResult();

        if (count > 0) {
            throw new RuntimeException("Client already has this account.");
        }

        // Retrieve the ClientAccount associated with the compteId
        TypedQuery<ClientAccount> compteQuery = em.createQuery(
                "SELECT ca FROM ClientAccount ca WHERE ca.compte.id = :compteId",
                ClientAccount.class
        );
        compteQuery.setParameter("compteId", compteId);

        ClientAccount clientAcc;
        try {
            clientAcc = compteQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new RuntimeException("Compte not found with ID: " + compteId);
        }

        // Determine the type of the account
        CompteType type = clientAcc.getCompte().getCompteType();

        if (type == CompteType.PARTAGE) {
            // Check if the shared account already has 10 clients using SIZE()
            TypedQuery<Integer> sharedCountQuery = em.createQuery(
                    "SELECT SIZE(ca.clients) FROM ClientAccount ca WHERE ca.id = :clientAccId",
                    Integer.class
            );
            sharedCountQuery.setParameter("clientAccId", clientAcc.getId());
            Integer sharedClientCount = sharedCountQuery.getSingleResult();

            if (sharedClientCount >= 10) {
                throw new RuntimeException("Shared account cannot have more than 10 clients.");
            }
        } else if (type == CompteType.PRIVE) {
            // Check if the private account already has a client using SIZE()
            TypedQuery<Integer> privateCountQuery = em.createQuery(
                    "SELECT SIZE(ca.clients) FROM ClientAccount ca WHERE ca.id = :clientAccId",
                    Integer.class
            );
            privateCountQuery.setParameter("clientAccId", clientAcc.getId());
            Integer privateClientCount = privateCountQuery.getSingleResult();

            if (privateClientCount >= 1) {
                throw new RuntimeException("Private account can only have one client.");
            }
        }

        // Assign the client to the ClientAccount
        clientAcc.addClient(client);
        em.merge(clientAcc);
        return clientAcc;
    }

    // Read
    @Override
    public Client findClientByName(String nom) {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.nom = :nom", Client.class);
        query.setParameter("nom", nom);
        List<Client> results = query.getResultList();
        if (results.isEmpty()) {
            throw new RuntimeException("Client not found with name: " + nom);
        }
        return results.get(0);
    }

    @Override
    public Client findClientById(Long id) {
        Client client = em.find(Client.class, id);
        if (client == null) {
            throw new RuntimeException("Client not found with ID: " + id);
        }
        return client;
    }

    @Override
    public Client findClientByEmail(String email) {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class);
        query.setParameter("email", email);
        List<Client> results = query.getResultList();
        if (results.isEmpty()) {
            throw new RuntimeException("Client not found with email: " + email);
        }
        return results.get(0);
    }

    @Override
    public List<Client> getAllClients() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    @Override
    public Compte findCompteById(Long id) {
        Compte compte = em.find(Compte.class, id);
        if (compte == null) {
            throw new RuntimeException("Compte not found with ID: " + id);
        }
        return compte;
    }

    @Override
    public List<Compte> getComptesForClient(Long clientId) {
        Client client = em.find(Client.class, clientId);
        if (client == null) {
            throw new RuntimeException("Client not found with ID: " + clientId);
        }

        //work with client account 
        List<ClientAccount> clientAccounts = client.getClientAccounts();

        return clientAccounts.stream().map(ClientAccount::getCompte).toList();

    }

    @Override
    public List<ClientAccount> getAllComptes() {
        return em.createQuery("SELECT ca FROM ClientAccount ca LEFT JOIN FETCH ca.clients", ClientAccount.class)
                .getResultList();
    }

    @Override
    public List<Transaction> getTransactionsForCompte(Compte compte) {
        if (compte.getTransactions().isEmpty()) {
            throw new RuntimeException("No transactions found for compte with ID: " + compte.getId());
        }
        return compte.getTransactions();
    }

    @Override
    public List<Transaction> getTransactionsForClient(Long clientId) {
        Client client = findClientById(clientId);
        List<ClientAccount> clientAccounts = client.getClientAccounts();
        if (clientAccounts.isEmpty()) {
            throw new RuntimeException("No accounts found for client with ID: " + clientId);
        }

        if (clientAccounts.stream().flatMap(ca -> ca.getCompte().getTransactions().stream()).toList().isEmpty()) {
            throw new RuntimeException("No transactions found for client with ID: " + clientId);
        }
        return clientAccounts.stream()
                .flatMap(ca -> ca.getCompte().getTransactions().stream())
                .toList();

    }

    @Override
    public List<Transaction> getClientTransactions(Long clientId
    ) {
        Client client = findClientById(clientId);
        List<ClientAccount> clientAccounts = client.getClientAccounts();
        if (clientAccounts.isEmpty()) {
            throw new RuntimeException("No accounts found for client with ID: " + clientId);
        }
        return clientAccounts.stream()
                .flatMap(ca -> ca.getCompte().getTransactions().stream())
                .toList();
    }

    @Override
    public List<ClientAccount> getClientAccounts(Long clientId
    ) {
        Client client = findClientById(clientId);
        List<ClientAccount> clientAccounts = client.getClientAccounts();
        if (clientAccounts.isEmpty()) {
            throw new RuntimeException("No accounts found for client with ID: " + clientId);
        }
        return clientAccounts;
    }

    @Override
    public List<ClientAccount> getClientAccountLignes() {
        return em.createQuery("SELECT ca FROM ClientAccount ca", ClientAccount.class).getResultList();
    }

    @Override
    public List<ClientAccount> getAssignedClientAccountLignes() {
        List<ClientAccount> assignedAccounts = em.createQuery("SELECT ca FROM ClientAccount ca WHERE ca.client IS NOT NULL AND ca.compte IS NOT NULL", ClientAccount.class).getResultList();
        if (assignedAccounts.isEmpty()) {
            throw new RuntimeException("No accounts found with assigned client and compte");
        }
        return assignedAccounts;
    }

    @Override
    public double consulterSolde(Long id
    ) {
        Compte compte = em.find(Compte.class, id);
        return compte.getSolde().doubleValue();
    }

    @Override
    public List<ClientAccount> getNoClientAccounts() {
        List<ClientAccount> NoClientAccounts = em.createQuery("SELECT ca FROM ClientAccount ca WHERE ca.client IS NULL", ClientAccount.class).getResultList();
        if (NoClientAccounts.isEmpty()) {
            throw new RuntimeException("No accounts found with no client");
        }
        return NoClientAccounts;

    }

    @Override
    public List<ClientAccount> getAvailabClientAccountsAccounts() {
        List<ClientAccount> availableAccounts = em.createQuery("SELECT ca FROM ClientAccount ca WHERE ca.client IS NULL OR (ca.client IS NOT NULL AND ca.compte.compteType = com.bank.types.CompteType.PARTAGE AND SIZE(ca.clients) < 10)", ClientAccount.class).getResultList();

        return availableAccounts;

    }

    // Update
    @Override
    public Compte retrait(Long id, BigDecimal montant
    ) {
        Compte compte = em.find(Compte.class, id);
        if (compte.getSolde().compareTo(montant) >= 0) {
            compte.setSolde(compte.getSolde().subtract(montant));
            em.merge(compte);
        } else {
            throw new RuntimeException("Solde insuffisant");
        }

        // save to transaction also
        Transaction transaction = new Transaction(montant, java.time.LocalDateTime.now(), TransactionType.RETRAIT, compte, null);

        em.persist(transaction);

        return compte;
    }

    @Override
    public Compte depot(Long id, BigDecimal montant
    ) {
        Compte compte = em.find(Compte.class, id);
        if (compte == null) {
            throw new RuntimeException("Compte not found with ID: " + id);
        }

        compte.setSolde(compte.getSolde().add(montant));
        em.merge(compte);

        Transaction transaction = new Transaction(montant, java.time.LocalDateTime.now(), TransactionType.DEPOT, compte, null);

        em.persist(transaction);
        return compte;

    }

    @Override
    public Compte virement(Long id, Long id2,
            BigDecimal montant
    ) {
        Compte compte1 = em.find(Compte.class, id);
        Compte compte2 = em.find(Compte.class, id2);

        if (compte1 == null || compte2 == null) {
            throw new RuntimeException("Compte not found with ID: " + id);
        }
        if (Objects.equals(compte1.getId(), compte2.getId())) {
            throw new RuntimeException("VIREMENT MEME compte IMPOSSIBLE");
        }

        if (compte1.getSolde().compareTo(montant) >= 0) {
            compte1.setSolde(compte1.getSolde().subtract(montant));
            compte2.setSolde(compte2.getSolde().add(montant));
            em.merge(compte1);
            em.merge(compte2);
        } else {
            throw new RuntimeException("Solde insuffisant");
        }
        Transaction transaction = new Transaction(montant, java.time.LocalDateTime.now(), TransactionType.ENVOI, compte1, compte2);

        em.persist(transaction);
        return compte1;

    }

    // Delete
    @Override
    @Transactional
    public void detachCompteFromClient(Long clientAccountId, Long clientId
    ) {
        ClientAccount clientAccount = em.find(ClientAccount.class, clientAccountId);
        if (clientAccount == null) {
            throw new RuntimeException("ClientAccount not found with ID: " + clientAccountId);
        }

        Client client = em.find(Client.class, clientId);
        if (client == null) {
            throw new RuntimeException("Client not found with ID: " + clientId);
        }

        if (!clientAccount.getClients().contains(client)) {
            throw new RuntimeException("Client is not associated with this account.");
        }

        clientAccount.removeClient(client);
        em.merge(clientAccount);
    }

    @Override
    public List<ClientAccount> getClientAccountLignesBut(Long clientId
    ) {
        List<ClientAccount> clientAccountLignesBut = em.createQuery("SELECT ca FROM ClientAccount ca WHERE ca.client.id <> :clientId", ClientAccount.class).setParameter("clientId", clientId).getResultList();
        if (clientAccountLignesBut.isEmpty()) {
            throw new RuntimeException("No accounts found for client with ID: " + clientId);
        }
        return clientAccountLignesBut;
    }

    @Override
    public ClientAccount getClientAccount(Long clientId, Long compteId
    ) {
        TypedQuery<ClientAccount> query = em.createQuery("SELECT ca FROM ClientAccount ca WHERE ca.client.id = :clientId AND ca.compte.id = :compteId", ClientAccount.class);
        ClientAccount clientAccount = query.setParameter("clientId", clientId).setParameter("compteId", compteId).getSingleResult();

        if (clientAccount == null) {
            throw new RuntimeException("No accounts found for client with ID: " + clientId);
        }
        return clientAccount;
    }
}
