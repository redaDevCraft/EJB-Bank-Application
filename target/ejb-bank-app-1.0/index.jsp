<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>FSTBank</title>
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
      rel="stylesheet"
    />
    <style>
      /* CSS Reset */
      *,
      *::before,
      *::after {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
      }

      body {
        font-family: "Roboto", sans-serif;
        background: linear-gradient(to right, #6a11cb, #2575fc);
        min-height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #333;
      }

      .container {
        background-color: rgba(255, 255, 255, 0.95);
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
        max-width: 600px;
        width: 100%;
        text-align: center;
      }

      h1 {
        color: #4caf50;
        margin-bottom: 20px;
        font-size: 2.5em;
        text-transform: uppercase;
        letter-spacing: 2px;
      }

      p {
        font-size: 1.2em;
        margin-bottom: 20px;
      }

      ul {
        list-style: none;
        margin-top: 20px;
      }

      li {
        margin: 10px 0;
      }

      a {
        display: inline-block;
        padding: 12px 24px;
        background-color: #4caf50;
        color: white;
        border-radius: 5px;
        text-decoration: none;
        transition: background-color 0.3s, transform 0.3s;
        font-weight: bold;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      }

      a:hover {
        background-color: #45a049;
        transform: translateY(-2px);
        box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
      }

      .error {
        color: #e74c3c;
        margin-top: 20px;
        font-weight: bold;
        font-size: 1.1em;
      }

      @media (max-width: 600px) {
        .container {
          padding: 20px;
        }

        h1 {
          font-size: 2em;
        }

        a {
          padding: 10px 20px;
          font-size: 0.9em;
        }
      }

      /* Additional Styles for Enhanced Aesthetics */
      body::before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: url("https://images.unsplash.com/photo-1600488994033-68ed026d2f8f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1950&q=80")
          no-repeat center center/cover;
        opacity: 0.15;
        z-index: -1;
      }

      a.logout {
        background-color: #f44336;
      }

      a.logout:hover {
        background-color: #da190b;
      }

      /* Smooth Scroll for Better UX */
      html {
        scroll-behavior: smooth;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h1>Welcome to FSTBank</h1>
      <!-- filepath: /c:/Users/sh/Study/ZZbankapp/ejb-bank-app/src/main/webapp/index.jsp -->
      <c:choose>
        <c:when test="${not empty client}">
          <p>
            Welcome, <strong>${client.nom}</strong> (<em>${client.type}</em>)
          </p>
          <ul>
            <c:if test="${client.type == 'BANQUIER'}">
              <li>
                <a
                  href="${pageContext.request.contextPath}/BankServlet?action=dashboard"
                  >Dashboard</a
                >
              </li>
            </c:if>
            <li>
              <a
                href="${pageContext.request.contextPath}/BankServlet?action=listComptes"
                >List Accounts</a
              >
            </li>
            <li>
              <a
                href="${pageContext.request.contextPath}/BankServlet?action=consulterTransactions"
                >Consult Transactions</a
              >
            </li>
            <li>
              <a
                href="${pageContext.request.contextPath}/BankServlet?action=virement"
                >Virement</a
              >
            </li>
            <li>
              <a
                href="${pageContext.request.contextPath}/BankServlet?action=retrait"
                >Retrait</a
              >
            </li>
            <li>
              <a
                href="${pageContext.request.contextPath}/BankServlet?action=depot"
                >Depot</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/logout" class="logout"
                >Logout</a
              >
            </li>
          </ul>
        </c:when>
        <c:otherwise>
          <p>Please <a href="login.jsp">login</a> to access the system.</p>
        </c:otherwise>
      </c:choose>
      <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
      </c:if>
    </div>
  </body>
</html>
