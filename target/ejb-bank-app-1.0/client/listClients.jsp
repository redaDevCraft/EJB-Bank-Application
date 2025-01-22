<!-- filepath: src/main/webapp/client/listClients.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Clients</title>
 

</head>
<body>
    <h1>Clients List</h1>
    <table border="1">
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Email</th>
            <th>Type</th>
        </tr>
        <c:forEach var="client" items="${clients}">
            <tr>
                <td>${client.nom}</td>
                <td>${client.prenom}</td>
                <td>${client.email}</td>
                <td>${client.type}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>