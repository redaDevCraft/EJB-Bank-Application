<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
            text-align: center;
        }
        h1 {
            color: #4CAF50;
        }
        p {
            color: #555;
        }
        .button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Login</h1>
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <label for="email">Email:</label>
            <input type="email" name="email"  required/><br/>
            <label for="password">Password:</label>
            <input type="password" name="password" required/><br/>
            <button type="submit">Login</button>
        </form>
        <c:if test="${not empty param.error}">
            <p style="color: red;">${param.error}</p>
        </c:if>
    </div>
</body>
</html>