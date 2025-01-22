<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Deposit Money</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
      }
      .container {
        width: 50%;
        margin: auto;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        margin-top: 50px;
      }
      h1 {
        text-align: center;
        color: #4caf50;
      }
      form {
        display: flex;
        flex-direction: column;
      }
      label {
        margin: 10px 0 5px;
      }
      input,
      select,
      button {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
      }
      button {
        margin-top: 20px;
        background-color: #4caf50;
        color: white;
        border: none;
        cursor: pointer;
      }
      button:hover {
        background-color: #45a049;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h1>Deposit Money</h1>
      <form
        action="${pageContext.request.contextPath}/BankServlet"
        method="post"
      >
        <input type="hidden" name="action" value="makeDepot" />

        <label for="compteId">Select Account:</label>
        <select name="compteId" id="compteId" required>
          <c:forEach var="compte" items="${availableAccounts}">
            <option value="${compte.compte.id}">
              ${compte.compte.numero} - ${compte.compte.compteType}
            </option>
          </c:forEach>
        </select>

        <label for="montant">Amount:</label>
        <input type="number" name="montant" id="montant" step="0.01" required />

        <button type="submit">Deposit</button>
      </form>
    </div>
  </body>
</html>
