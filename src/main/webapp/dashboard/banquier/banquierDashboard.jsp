<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Banquier Dashboard</title>
    <style>
      /* Existing CSS */
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
      }
      .container {
        width: 80%;
        margin: auto;
        overflow: hidden;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
      }
      h1 {
        background-color: #4caf50;
        color: white;
        padding: 20px;
        text-align: center;
        border-radius: 8px 8px 0 0;
      }
      .button-container {
        text-align: center;
        margin: 20px 0;
      }
      .button {
        display: inline-block;
        margin: 10px;
        padding: 10px 20px;
        background-color: #4caf50;
        color: #fff;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s;
      }
      .button:hover {
        background-color: #45a049;
      }
      table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
      }
      th,
      td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: left;
      }
      th {
        background-color: #4caf50;
        color: white;
      }
      .dropdown {
        position: relative;
        display: inline-block;
      }
      .dropdown-toggle {
        background-color: #4caf50;
        color: white;
        padding: 10px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
      }
      .dropdown-menu {
        display: none;
        position: sticky;
        background-color: #f9f9f9;
        min-width: 160px;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        z-index: 1;
        border-radius: 5px;
      }
      .dropdown-menu a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
      }
      .dropdown-menu a:hover {
        background-color: #f1f1f1;
      }
      .dropdown:hover .dropdown-menu {
        display: block;
      }
      .modal {
        display: none;
        position: fixed;
        z-index: 2;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0, 0, 0, 0.4);
        padding-top: 60px;
      }
      .modal-content {
        background-color: #fefefe;
        margin: 5% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 40%;
        border-radius: 8px;
      }
      .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
      }
      .close:hover,
      .close:focus {
        color: black;
        text-decoration: none;
      }
      @media (max-width: 600px) {
        .modal-content {
          width: 90%;
        }
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h1>Banquier Dashboard</h1>
      <div class="button-container">
        <a
          href="${pageContext.request.contextPath}/client/createClient.jsp"
          class="button"
          >Create Client</a
        >
        <a
          href="${pageContext.request.contextPath}/compte/createCompte.jsp"
          class="button"
          >Create Account</a
        >
      </div>
      <h2>Clients</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom et Prénom</th>
            <th>Email</th>
            <th>Type</th>
            <th colspan="2">Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="client" items="${clients}">
            <tr>
              <td>${client.id}</td>
              <td>${client.nom} ${client.prenom}</td>
              <td>${client.email}</td>
              <td>${client.type}</td>
              <td>
                <a
                  href="${pageContext.request.contextPath}/BankServlet?action=showComptes&clientId=${client.id}"
                  class="button"
                  >Show Comptes</a
                >
              </td>
              <td>
                <div class="dropdown">
                  <button class="dropdown-toggle">Actions</button>
                  <div class="dropdown-menu">
                    <a
                      href="${pageContext.request.contextPath}/BankServlet?action=attachCompte&clientId=${client.id}$compteId=${compte.id}"
                      class="dropdown-item"
                      data-client-id="${client.id}"
                      data-compte-id="${compte.id}"
                      >Attach Account</a
                    >
                    <a
                      href="${pageContext.request.contextPath}/BankServlet?action=detachCompte&clientId=${client.id}"
                      class="dropdown-item detach-account"
                      data-client-id="${client.id}"

                      >Detach Account</a
                    >
               
                  </div>
                </div>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- Attach Account Modal -->
    <div id="attachAccountModal" class="modal">
      <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Attach Account</h2>
        <form
          id="attachAccountForm"
          action="${pageContext.request.contextPath}/BankServlet"
          method="post"
        >
          <input type="hidden" name="action" value="assignClientToCompte" />
          <input
            type="hidden"
            name="clientId"
            id="attachClientId"
            value="${client.id}"
          />
          <label for="compteId">Select Account:</label>
          <select name="compteId" id="compteId" required>
            <option disabled selected>-- Select an Account --</option>
            <c:forEach var="account" items="${availableAccounts}">
              <option value="${account.compte.id}">
                ${account.compte.numero} - ${account.compte.compteType}
              </option>
            </c:forEach>
          </select>
          <button type="submit">Attach</button>
        </form>
      </div>
    </div>
    <!-- Detach Account Modal -->
    <div id="detachAccountModal" class="modal">
      <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Detach Account</h2>
        <form
          id="detachAccountForm"
          action="${pageContext.request.contextPath}/BankServlet"
          method="post"
        >
          <input type="hidden" name="action" value="detachCompte" />
          <input
            type="hidden"
            name="clientId"
            id="detachClientId"
          />
          <input
            type="hidden"
            name="selectedCompte"
            id="detachSelectedCompte"
          />
          <label for="selectedCompte">Select Account to Detach:</label>
          <select name="selectedCompte" id="selectedCompte" required>
            <option disabled selected>-- Select an Account --</option>
            <!-- Options populated via JavaScript -->
          </select>
          <button type="submit">Yes, Detach</button>
          <button type="button" onclick="closeDetachModal()">Cancel</button>
        </form>
      </div>
    </div>

    <script>
      var modal = document.getElementById("attachAccountModal");
      var span = document.getElementsByClassName("close")[0];

      document
        .querySelectorAll('.dropdown-menu a[href*="attachCompte"]')
        .forEach(function (element) {
          element.addEventListener("click", function (e) {
            e.preventDefault();
            var clientId =
              this.getAttribute("data-client-id") || "${client.id}";
            document.getElementById("attachClientId").value = clientId;
            modal.style.display = "block";
          });
        });

      span.onclick = function () {
        modal.style.display = "none";
      };

      window.onclick = function (event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      };
    </script>

   <script>
  var clientAccountsMap = {
    <c:forEach var="client" items="${clients}">
      "${client.id}": [
        <c:forEach var="clientAccount" items="${client.clientAccounts}">
          { id: "${clientAccount.id}", numero: "${clientAccount.compte.numero}", type: "${clientAccount.compte.compteType}" }
          <c:if test="${fn:indexOf(clientAccount, client.clientAccounts) < fn:length(client.clientAccounts) - 1}">,</c:if>
        </c:forEach>
      ]
      <c:if test="${fn:indexOf(client, clients) < fn:length(clients) - 1}">,</c:if>
    </c:forEach>
  };

  var detachClientIdInput = document.getElementById("detachClientId");
  var detachSelectedCompteInput = document.getElementById("detachSelectedCompte");
  var detachModal = document.getElementById("detachAccountModal");
  var detachSpan = detachModal.getElementsByClassName("close")[0];
  var detachForm = document.getElementById("detachAccountForm");
  var selectedCompteSelect = document.getElementById("selectedCompte");

  document.querySelectorAll(".detach-account").forEach(function(element) {
    element.addEventListener("click", function(e) {
      e.preventDefault();
      var clientId = this.getAttribute("data-client-id");
      var accounts = clientAccountsMap[clientId];

      selectedCompteSelect.innerHTML = '<option value="" disabled selected>-- Select an Account --</option>';

      if (accounts && accounts.length > 0) {
        accounts.forEach(function(account) {
          var option = document.createElement("option");
          option.value = account.id;
          option.text = "Account " + account.numero + " - Type " + account.type;
          selectedCompteSelect.appendChild(option);
        });
        detachClientIdInput.value = clientId;
        detachModal.style.display = "block";
      } else {
        alert("No accounts available to detach for this client.");
      }
    });
  });

  detachForm.addEventListener("submit", function(e) {
    var selectedCompteId = selectedCompteSelect.value;
    if (!selectedCompteId) {
      alert("Please select an account to detach.");
      e.preventDefault();
      return;
    }
    detachSelectedCompteInput.value = selectedCompteId;
  });

  detachSpan.onclick = function() {
    detachModal.style.display = "none";
  };

  window.onclick = function(event) {
    if (event.target == detachModal) {
      detachModal.style.display = "none";
    }
  };

  function closeDetachModal() {
    detachModal.style.display = "none";
  }
</script>

  </body>
</html>
