<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Create Client</title>
  </head>
  <body>
    <h1>Create New Client</h1>
    <form action="${pageContext.request.contextPath}/BankServlet" method="post">
      <input type="hidden" name="action" value="createClient" />
      <label for="nom">Nom:</label>
      <input type="text" name="nom" required /><br />

      <label for="prenom">Prenom:</label>
      <input type="text" name="prenom" required /><br />

      <label for="email">Email:</label>
      <input type="text" name="email" required /><br />

      <label for="password">Password:</label>
      <input type="password" name="password" required /><br />

      <label for="type">Type (particulier/professionnel/Banquier):</label>
      <select name="type">
        <option value="PARTICULIER">Particulier</option>
        <option value="PROFESSIONNEL">Professionnel</option>
        <option value="BANQUIER">Banquier</option></select
      ><br />

      <button type="submit">Create Client</button>
    </form>
  </body>
</html>
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
    text-align: center;
    color: #333;
  }

  form {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 300px;
  }

  label {
    display: block;
    margin-bottom: 5px;
    color: #333;
  }

  input[type="text"],
  input[type="password"],
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
    background-color: #007bff;
    border: none;
    border-radius: 4px;
    color: white;
    font-size: 16px;
    cursor: pointer;
  }

  button:hover {
    background-color: #0056b3;
  }
</style>
