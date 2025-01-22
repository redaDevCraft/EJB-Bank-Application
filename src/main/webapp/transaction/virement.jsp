<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Transfer Money</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
      }
      h1 {
        color: #333;
      }
      form {
        background: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        width: 300px;
      }
      label {
        display: block;
        margin-bottom: 5px;
        color: #555;
      }
      select,
      input[type="number"] {
        width: 100%;
        padding: 8px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
      }
      input[type="submit"] {
        width: 100%;
        padding: 10px;
        background-color: #28a745;
        border: none;
        border-radius: 4px;
        color: white;
        font-size: 16px;
        cursor: pointer;
      }
      input[type="submit"]:hover {
        background-color: #218838;
      }
    </style>
  </head>
  <body>
    <h1>Transfer Money</h1>
    <form action="${pageContext.request.contextPath}/BankServlet" method="post">
      <input type="hidden" name="action" value="makeVirement" />

      <label for="fromAccount">From Account:</label>
      <select id="fromAccount" name="fromAccount" required>
        <c:forEach var="account" items="${loggedInClientAccounts}">
          <option value="${account.compte.id}">
            ${account.compte.numero} - ${account.compte.compteType}
          </option>
        </c:forEach>
      </select>

      <label for="toAccount">To Account:</label>
      <select id="toAccount" name="toAccount" required>
        <c:forEach var="account" items="${availableAccounts}">
        <%-- wait until a fromaccount is selected  --%>
          <c:if test="${account.compte.id != fromAccount}">
            <option value="${account.compte.id}">
              ${account.compte.numero} - ${account.compte.compteType}
            </option>
          </c:if>
        </c:forEach>
      </select>

      <label for="amount">Amount:</label>
      <input type="number" id="amount" name="amount" step="0.01" required />

      <button type="submit">Transfer</button>
    </form>
  </body>
</html>
