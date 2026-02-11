<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
  <meta charset="UTF-8"/>
  <title>Admin Transactions</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-body">
  <jsp:include page="header.jsp"/>

  <main class="container py-4">
    <h3 class="mb-3">Admin — Transactions</h3>

    <c:if test="${not empty error}">
      <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
      <div class="alert alert-success">${msg}</div>
    </c:if>

    <div class="row g-4">
      <!-- Deposit -->
      <div class="col-lg-4">
        <div class="card border-secondary shadow-sm h-100">
          <div class="card-header">Deposit</div>
          <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/tx/deposit" class="row g-3">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <div class="col-12">
                <label class="form-label">To Account</label>
                <select name="toAccountId" class="form-select" required>
                  <c:forEach var="a" items="${allAccounts}">
                    <option value="${a.accountId}">
                      #${a.accountId} — ${a.accountCustomer.customerName} — ${a.accountType}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-12">
                <label class="form-label">Amount</label>
                <input type="number" name="amount" step="0.01" min="0.01" class="form-control" required/>
              </div>
              <div class="col-12">
                <label class="form-label">Note (optional)</label>
                <input type="text" name="note" class="form-control"/>
              </div>
              <div class="col-12">
                <button class="btn btn-primary">Deposit</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- Withdraw -->
      <div class="col-lg-4">
        <div class="card border-secondary shadow-sm h-100">
          <div class="card-header">Withdraw</div>
          <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/tx/withdraw" class="row g-3">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <div class="col-12">
                <label class="form-label">From Account</label>
                <select name="fromAccountId" class="form-select" required>
                  <c:forEach var="a" items="${allAccounts}">
                    <option value="${a.accountId}">
                      #${a.accountId} — ${a.accountCustomer.customerName} — ${a.accountType}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-12">
                <label class="form-label">Amount</label>
                <input type="number" name="amount" step="0.01" min="0.01" class="form-control" required/>
              </div>
              <div class="col-12">
                <label class="form-label">Note (optional)</label>
                <input type="text" name="note" class="form-control"/>
              </div>
              <div class="col-12">
                <button class="btn btn-warning">Withdraw</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- Transfer -->
      <div class="col-lg-4">
        <div class="card border-secondary shadow-sm h-100">
          <div class="card-header">Transfer</div>
          <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/tx/transfer" class="row g-3">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <div class="col-12">
                <label class="form-label">From Account</label>
                <select name="fromAccountId" class="form-select" required>
                  <c:forEach var="a" items="${allAccounts}">
                    <option value="${a.accountId}">
                      #${a.accountId} — ${a.accountCustomer.customerName} — ${a.accountType}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-12">
                <label class="form-label">To Account</label>
                <select name="toAccountId" class="form-select" required>
                  <c:forEach var="a" items="${allAccounts}">
                    <option value="${a.accountId}">
                      #${a.accountId} — ${a.accountCustomer.customerName} — ${a.accountType}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-12">
                <label class="form-label">Amount</label>
                <input type="number" name="amount" step="0.01" min="0.01" class="form-control" required/>
              </div>
              <div class="col-12">
                <label class="form-label">Note (optional)</label>
                <input type="text" name="note" class="form-control"/>
              </div>
              <div class="col-12">
                <button class="btn btn-success">Transfer</button>
              </div>
            </form>
          </div>
        </div>
      </div>

    </div>
  </main>
</body>
</html>


