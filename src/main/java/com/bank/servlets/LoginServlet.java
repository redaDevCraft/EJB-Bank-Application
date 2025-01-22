package com.bank.servlets;

import java.io.IOException;

import com.bank.beans.Client;
import com.bank.sessions.BankServiceLocal;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private BankServiceLocal bankService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Client client = bankService.findClientByEmail(email);

        if (client != null && client.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("client", client);
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Invalid email or password");
        }
    }
}