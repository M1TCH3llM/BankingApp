<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Accounts</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<script>
document.addEventListener('DOMContentLoaded', function() {
  var custSelect = document.querySelector('select[name="customerId"]');
  var ownerDisplay = document.getElementById('ownerDisplay');
  if (custSelect && ownerDisplay) {
    custSelect.addEventListener('change', function() {
      var text = this.options[this.selectedIndex]?.text || '';
      var idx = text.indexOf(' — ');
      ownerDisplay.value = idx >= 0 ? text.substring(idx + 3).trim() : text.trim();
    });
  }
});
</script>
<body class="bg-body">
  <jsp:include page="header.jsp"/>

  <main class="container py-4">
    <c:if test="${not empty error}">
      <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
      <div class="alert alert-success">${msg}</div>
    </c:if>

    <div class="row g-4">
      <!-- Left: table -->
      <div class="col-lg-8">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h3 class="m-0">Accounts</h3>
          <!-- “New” just clears edit state -->
          <a class="btn btn-primary" href="${pageContext.request.contextPath}/account/page">+ New</a>
        </div>

        <div class="card shadow-sm border-secondary">
          <div class="table-responsive">
            <table class="table table-dark table-striped table-hover m-0 align-middle">
              <thead>
                <tr>
                  <th>ID</th>
   				  <th>Type</th>
   				  <th>Owner</th>
    			  <th>Opened</th>
   			      <th>Balance</th>
    			  <th>Branch</th>
                  <th >Edit</th>
                  <th >Delete</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="a" items="${accounts}">
   			 <tr>
     			 <td>${a.accountId}</td>
      			<td>${a.accountType}</td>
     		 <td>
     		   <c:choose>
      		    <c:when test="${not empty a.accountCustomer}">
            ${a.accountCustomer.customerId} — ${a.accountCustomer.customerName}
      		    </c:when>
      		    <c:otherwise><span class="text-secondary">—</span></c:otherwise>
      		  </c:choose>
     		 </td>
    		  <td>${a.accountDateOpened}</td>
   			   <td>${a.accountBalance}</td>
     		 <td>
       			 <c:choose>
         		 <c:when test="${not empty a.accountBranch}">
         			   ${a.accountBranch.branchId} — ${a.accountBranch.branchName}
        			  </c:when>
        			  <c:otherwise><span class="text-secondary">—</span></c:otherwise>
       			 </c:choose>
     				 </td>
     				 <td>
                      
                        <a class="btn btn-sm btn-outline-light"
                           href="${pageContext.request.contextPath}/account/page?editId=${a.accountId}">Edit</a>
                           </td>
                            <td>
                        <a class="btn btn-sm btn-outline-danger"
                           href="${pageContext.request.contextPath}/account/delete/${a.accountId}"
                           onclick="return confirm('Delete account #${a.accountId}?');">Delete</a>
                      
                    </td>
                  </tr>
                </c:forEach>

                <c:if test="${empty accounts}">
                  <tr>
                    <td colspan="8" class="text-center text-secondary py-4">
                      No accounts yet. Use the form to create one.
                    </td>
                  </tr>
                </c:if>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- Right: form -->
       <sec:authorize access="hasRole('ROLE_ADMIN')">
      <div class="col-lg-4">
        <div class="card shadow-sm border-secondary">
          <div class="card-header">
            <c:choose>
              <c:when test="${not empty account.accountId}">Edit Account #${account.accountId}</c:when>
              <c:otherwise>New Account</c:otherwise>
            </c:choose>
          </div>
          <div class="card-body">
            <form:form method="post"
                       action="${pageContext.request.contextPath}/account/save"
                       modelAttribute="account"
                       class="row g-3">

              <form:hidden path="accountId"/>

              <div class="col-12">
                <label class="form-label">Account Type</label>
                <form:select path="accountType" cssClass="form-select">
                  <form:options items="${accountTypes}"/>
                </form:select>
              </div>
              
			 <%-- <div class="col-12">
                <label class="form-label">Customer (Owner)</label>
                <select name="customerId" class="form-select" required>
                  <option value="" disabled <c:if test="${empty account.accountCustomer}">selected</c:if>>Choose customer…</option>
                  <c:forEach var="cst" items="${customers}">
                    <option value="${cst.customerId}"
                      <c:if test="${account.accountCustomer ne null and account.accountCustomer.customerId eq cst.customerId}">selected</c:if>>
                      ${cst.customerId} — ${cst.customerName}
                    </option>
                  </c:forEach>
                </select>
              </div> --%>
			
              <div class="col-6">
                <label class="form-label">Date Opened</label>
                <form:input path="accountDateOpened" type="date" cssClass="form-control"/>
              </div>

              <div class="col-6">
                <label class="form-label">Balance</label>
                <form:input path="accountBalance" type="number" step="0.01" cssClass="form-control"/>
              </div>

              <!-- Customer select posts raw id -->
            
              
              <c:choose>
 				 <c:when test="${lockCustomer}">
   				 <c:set var="lockedId" value="${presetCustomerId}"/>
   				 <c:set var="lockedName" value=""/>
  				  <c:forEach var="cst" items="${customers}">
   				   <c:if test="${cst.customerId == lockedId}">
   				     <c:set var="lockedName" value="${cst.customerName}"/>
   				   </c:if>
  				  </c:forEach>

  				  <div class="col-12">
  			    <label class="form-label">Customer (Owner)</label>
  				    <input type="text" class="form-control" value="${lockedId} — ${lockedName}" readonly/>
      <!-- post the id the controller expects -->
  				    <input type="hidden" name="customerId" value="${lockedId}"/>
  				  </div>
 				 </c:when>
 				 <c:otherwise>
    <!-- original dropdown -->
  				  <div class="col-12">
  				    <label class="form-label">Customer (Owner)</label>
   				   <select name="customerId" class="form-select" required>
  				      <option value="" disabled <c:if test="${empty account.accountCustomer}">selected</c:if>>Choose customer…</option>
   				     <c:forEach var="cst" items="${customers}">
  				        <option value="${cst.customerId}"
   				         <c:if test="${account.accountCustomer ne null and account.accountCustomer.customerId eq cst.customerId}">selected</c:if>>
    				        ${cst.customerId} — ${cst.customerName}
   				       </option>
   				     </c:forEach>
   				   </select>
   				 </div>
  				</c:otherwise>
			</c:choose>

              <!-- Branch select posts raw id -->
              <div class="col-12">
                <label class="form-label">Branch</label>
                <select name="branchId" class="form-select" required>
                  <option value="" disabled <c:if test="${empty account.accountBranch}">selected</c:if>>Choose branch…</option>
                  <c:forEach var="br" items="${branches}">
                    <option value="${br.branchId}"
                      <c:if test="${account.accountBranch ne null and account.accountBranch.branchId eq br.branchId}">selected</c:if>>
                      ${br.branchId} — ${br.branchName}
                    </option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-12 d-flex gap-2">
                <button type="submit" class="btn btn-primary">Save</button>
                <!-- Clear edit state -->
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/account/page">Reset</a>
              </div>
              	<a class="btn btn-sm btn-outline-primary"
  				 href="${pageContext.request.contextPath}/account/page?customerId=${c.customerId}">
 				 Add Account
				</a>
            </form:form>
          </div>
        </div>
      </div>
      </sec:authorize>
    </div>
  </main>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

