<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Create Account</title>
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
      .container {
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        width: 300px;
      }
      h1 {
        text-align: center;
        color: #333;
      }
      label {
        display: block;
        margin-bottom: 8px;
        color: #555;
      }
      input[type="text"],
      select {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
      }
      button {
        width: 100%;
        padding: 10px;
        background-color: #28a745;
        border: none;
        border-radius: 4px;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
      }
      button:hover {
        background-color: #218838;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h1>Create New Account</h1>
      <form
        action="${pageContext.request.contextPath}/BankServlet"
        method="post"
      >
        <input type="hidden" name="action" value="createCompte" />
        <label for="numero">Account Number:</label>
        <input type="text" name="numero" required /><br />

        <label for="solde">Balance:</label>
        <input type="text" name="solde" required /><br />

        <label for="type">Type (PARTAGE/PRIVE):</label>
        <select name="type" required>
          <option value="PARTAGE">Partage</option>
          <option value="PRIVE">Prive</option></select
        ><br />

        <button type="submit">Create Account</button>
      </form>
    </div>
  </body>
</html>
