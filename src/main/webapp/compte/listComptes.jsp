<!-- filepath: /c:/Users/sh/Study/ZZbankapp/ejb-bank-app/src/main/webapp/compte/listComptes.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Accounts</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
      }
      h1 {
        background-color: #4caf50;
        color: white;
        padding: 20px;
        text-align: center;
      }
      .container {
        width: 80%;
        margin: auto;
        overflow: hidden;
      }
      table {
        width: 100%;
        border-collapse: collapse;
      }
      table,
      th,
      td {
        border: 1px solid black;
      }
      th,
      td {
        padding: 10px;
        text-align: left;
      }
      th {
        background-color: #4caf50;
        color: white;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h1>Accounts List</h1>

      <table>
        <thead>
          <tr>
            <th>Account Number</th>
            <th>Type</th>
            <th>Balance</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="compte" items="${comptes}">
            <tr>
              <td>${compte.numero}</td>
              <td>${compte.compteType}</td>
              <td>${compte.solde}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>
</html>
