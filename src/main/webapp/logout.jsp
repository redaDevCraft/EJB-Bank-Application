<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Logout</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
      }
      .logout-container {
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        text-align: center;
      }
      .logout-container h1 {
        color: #333;
      }
      .logout-container p {
        color: #666;
      }
      .logout-container a {
        display: inline-block;
        margin-top: 20px;
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        text-decoration: none;
        border-radius: 4px;
        transition: background-color 0.3s;
      }
      .logout-container a:hover {
        background-color: #0056b3;
      }
    </style>
  </head>
  <body>
    <div class="logout-container">
      <h1>You have been logged out</h1>
      <p>Thank you for using our service.</p>
      <a href="login.jsp">Login Again</a>
    </div>
  </body>
</html>
