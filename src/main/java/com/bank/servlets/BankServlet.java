package com.bank.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.bank.beans.Client;
import com.bank.beans.ClientAccount;
import com.bank.beans.Compte;
import com.bank.beans.Transaction;
import com.bank.sessions.BankServiceLocal;
import com.bank.types.ClientType;
import com.bank.types.CompteType;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BankServlet")
public class BankServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private BankServiceLocal bankService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Client loggedInClient = (Client) session.getAttribute("client");

        try {
            if (null != action) {
                switch (action) {
                    case "createClient":
                        if (loggedInClient != null && loggedInClient.getType() == ClientType.BANQUIER) {
                            String nom = request.getParameter("nom");
                            String prenom = request.getParameter("prenom");
                            String email = request.getParameter("email");
                            String password = request.getParameter("password");
                            String type = request.getParameter("type");

                            Client client = new Client();
                            client.setNom(nom);
                            client.setPrenom(prenom);
                            client.setEmail(email);
                            client.setPassword(password);
                            client.setType(ClientType.valueOf(type.toUpperCase()));

                            bankService.createClient(client);
                            response.sendRedirect("success.jsp");
                        } else {
                            response.sendRedirect("unauthorized.jsp");
                        }

                    case "createCompte":
                        if (loggedInClient != null && loggedInClient.getType() == ClientType.BANQUIER) {
                            String numero = request.getParameter("numero");
                            BigDecimal solde = new BigDecimal(request.getParameter("solde"));
                            String type = request.getParameter("type");

                            Compte compte = new Compte();
                            compte.setNumero(numero);
                            compte.setSolde(solde);
                            compte.setCompteType(CompteType.valueOf(type.toUpperCase()));

                            bankService.createCompte(compte);

                            response.sendRedirect("success.jsp");
                        } else {
                            response.sendRedirect("unauthorized.jsp");
                        }
                        break;
                    case "assignClientToCompte": {
                        try {
                            Long clientId = Long.valueOf(request.getParameter("clientId"));
                            Long compteId = Long.valueOf(request.getParameter("compteId"));
                            bankService.assignCompteToClient(clientId, compteId);
                            response.sendRedirect("success.jsp");
                        } catch (RuntimeException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                        break;
                    }
                    case "detachCompte": {
                        try {
                            Long clientAccountId = Long.valueOf(request.getParameter("selectedCompte"));
                            Long clientId = Long.valueOf(request.getParameter("clientId")); // Get the selected client ID
                            bankService.detachCompteFromClient(clientAccountId, clientId);
                            response.sendRedirect("success.jsp");
                        } catch (RuntimeException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                        break;
                    }

                    case "makeRetrait": {
                        try {
                            Long id = Long.valueOf(request.getParameter("compteId"));
                            BigDecimal montant = new BigDecimal(request.getParameter("montant"));
                            bankService.retrait(id, montant);
                            response.sendRedirect("success.jsp");
                        } catch (RuntimeException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                        break;
                    }

                    case "makeVirement": {
                        try {
                            Long fromAccountId = Long.valueOf(request.getParameter("fromAccount"));
                            Long toAccountId = Long.valueOf(request.getParameter("toAccount"));
                            BigDecimal montant = new BigDecimal(request.getParameter("amount"));

                            bankService.virement(fromAccountId, toAccountId, montant);
                            response.sendRedirect("success.jsp");
                        } catch (Exception e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    }
                    case "makeDepot": {
                        try {
                            Long compteId = Long.valueOf(request.getParameter("compteId"));
                            BigDecimal montant = new BigDecimal(request.getParameter("montant"));

                            bankService.depot(compteId, montant);
                            response.sendRedirect("success.jsp");
                        } catch (RuntimeException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    }

                    default:
                        break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Client loggedInClient = (Client) session.getAttribute("client");

        try {
            if (null != action) {
                switch (action) {
                    case "listClients" -> {
                        try {
                            List<Client> clients = bankService.getAllClients();
                            request.setAttribute("clients", clients);
                            request.getRequestDispatcher("dashboard/banquier/banquierDashboard.jsp").forward(request, response);
                        } catch (ServletException | IOException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    }
                    case "consulterTransactions" -> {

                        request.setAttribute("transactions", bankService.getTransactionsForClient(loggedInClient.getId()));
                        request.getRequestDispatcher("transaction/consulterTransactions.jsp").forward(request, response);

                    }
                    case "clientTransactions" -> {
                        Long clientId = Long.valueOf(request.getParameter("clientId"));
                        List<Transaction> transactions = bankService.getClientTransactions(clientId);
                        request.setAttribute("transactions", transactions);
                        request.getRequestDispatcher("client/clientTransactions.jsp").forward(request, response);
                    }

                    case "dashboard" -> {
                        try {
                            List<Client> clients = bankService.getAllClients();
                            List<ClientAccount> clientAccounts = bankService.getClientAccountLignes();
                            List<ClientAccount> availableAccounts = bankService.getAvailabClientAccountsAccounts();
                            request.setAttribute("availableAccounts", availableAccounts);
                            request.setAttribute("clientAccounts", clientAccounts);
                            request.setAttribute("clients", clients);
                            request.getRequestDispatcher("dashboard/banquier/banquierDashboard.jsp").forward(request, response);
                        } catch (RuntimeException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                        break;

                    }
                    case "showComptes" -> {
                        try {
                            Long clientId = Long.valueOf(request.getParameter("clientId"));
                            List<Compte> comptes = bankService.getComptesForClient(clientId);
                            request.setAttribute("comptes", comptes);
                            request.getRequestDispatcher("compte/listComptes.jsp").forward(request, response);
                        } catch (RuntimeException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    }
                    case "listComptes" -> {
                        try {
                            List<Compte> comptes = bankService.getComptesForClient(loggedInClient.getId());
                            request.setAttribute("comptes", comptes);
                            request.getRequestDispatcher("compte/listComptes.jsp").forward(request, response);
                        } catch (RuntimeException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    }

                    case "virement" -> {
                        try {
                            List<ClientAccount> availableAccounts = bankService.getAssignedClientAccountLignes();
                            List<ClientAccount> loggedInClientAccounts = bankService.getClientAccounts(loggedInClient.getId());
                            request.setAttribute("availableAccounts", availableAccounts);
                            request.setAttribute("loggedInClientAccounts", loggedInClientAccounts);

                            request.getRequestDispatcher("transaction/virement.jsp").forward(request, response);

                        } catch (ServletException | IOException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("${pageContext.request.contextPath}/error.jsp").forward(request, response);
                        }
                    }
                    case "depot" -> {
                        try {
                            List<ClientAccount> availableAccounts = bankService.getClientAccounts(loggedInClient.getId());
                            request.setAttribute("availableAccounts", availableAccounts);
                            request.getRequestDispatcher("transaction/depot.jsp").forward(request, response);

                        } catch (ServletException | IOException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    }
                    case "retrait" -> {
                        try {
                            List<ClientAccount> availableAccounts = bankService.getClientAccounts(loggedInClient.getId());
                            request.setAttribute("availableAccounts", availableAccounts);
                            request.getRequestDispatcher("transaction/retrait.jsp").forward(request, response);

                        } catch (ServletException | IOException e) {
                            request.setAttribute("errorMessage", e.getMessage());
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    }

                    default -> {
                    }
                }
            }
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
