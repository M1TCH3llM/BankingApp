<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Customers</title>

  <!-- Bootstrap 5.3 (dark ready) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-body">
        <jsp:include page="header.jsp" />
  

  <main class="container py-4">

    <!-- Form card -->
    <div class="card shadow-sm border-secondary mb-4">
      <div class="card-header bg-dark text-light">
        <strong>Create Customer</strong>
      </div>
      <div class="card-body">

        <form:form modelAttribute="customer"
                   action="${pageContext.request.contextPath}/customer/page"
                   method="post" class="row g-3">

          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <form:hidden path="customerId"/>
			<div class="col-12">
  				<label class="form-label" for="customerName">Customer Name</label>
  				<form:input path="customerName" id="customerName" cssClass="form-control"/>
  				<form:errors path="customerName" cssClass="text-danger small"/>
		  </div>
		  
          <div class="col-md-6">
  			<label class="form-label" for="dob">Date of Birth</label>
  			<form:input path="customerDOB" id="dob" type="date" cssClass="form-control"/>
 			<form:errors path="customerDOB" cssClass="text-danger small"/>
		  </div>
		  
		 <div class="col-md-6">
  			<label class="form-label">Gender</label><br/>
  			<form:radiobuttons path="customerGender" items="${genders}" cssClass="form-check-input"  delimiter="&nbsp;&nbsp;&nbsp;"/>
  			<form:errors path="customerGender" cssClass="text-danger small"/>
		 </div>
	
		  <div class="col-md-6">
  			<label class="form-label" for="ssn">SSN</label>
  			<form:input path="customerSSN" id="ssn" cssClass="form-control"/>
  			<form:errors path="customerSSN" cssClass="text-danger small"/>
		  </div>
		 <%-- <div class="col-12">
  			<label class="form-label">Accounts</label><br/>
  			<form:checkboxes path="customerAccounts"
                   items="${accountTypes}"
                   cssClass="form-check-input" delimiter="&nbsp;&nbsp;&nbsp;"/>
 			 <form:errors path="customerAccounts" cssClass="text-danger small"/>
		  </div> --%>
          <div class="col-md-6">
            <label class="form-label" for="addr1">Address Line 1</label>
            <form:input path="customerAddress.addressLine1" id="addr1" cssClass="form-control" placeholder="" />
            <form:errors path="customerAddress.addressLine1" cssClass="text-danger small" />
          </div>

          <div class="col-md-6">
            <label class="form-label" for="addr2">Address Line 2</label>
            <form:input path="customerAddress.addressLine2" id="addr2" cssClass="form-control" placeholder="" />
            <form:errors path="customerAddress.addressLine2" cssClass="text-danger small" />
          </div>

          <div class="col-md-4">
            <label class="form-label" for="city">City</label>
            <form:input path="customerAddress.city" id="city" cssClass="form-control" />
            <form:errors path="customerAddress.city" cssClass="text-danger small" />
          </div>

          <div class="col-md-4">
            <label class="form-label" for="state">State/Province</label>
            <form:input path="customerAddress.state" id="state" cssClass="form-control" />
            <form:errors path="customerAddress.state" cssClass="text-danger small" />
          </div>

          <div class="col-md-4">
            <label class="form-label" for="zip">ZIP/Postal</label>
            <form:input path="customerAddress.zip" id="zip" cssClass="form-control" />
            <form:errors path="customerAddress.zip" cssClass="text-danger small" />
          </div>

          <div class="col-md-6">
            <label class="form-label" for="country">Country</label>
            <form:input path="customerAddress.country" id="country" cssClass="form-control" />
            <form:errors path="customerAddress.country" cssClass="text-danger small" />
          </div>
          
          <c:if test="${empty customer.customerId}">
 		 <div class="col-md-6">
    		<label class="form-label">Initial Account Type</label>
   				 <select name="initialAccountType" class="form-select" required>
   			   <c:forEach var="t" items="${accountTypes}">
    		    <option value="${t}">${t}</option>
    			  </c:forEach>
   				 </select>
  				  <form:errors path="customerAccounts" cssClass="text-danger"/>
		</div>
			</c:if>


<!-- Branch for the new account (optional) -->
			<div class="col-md-6">
  				<label class="form-label">Branch (optional)</label>
 				 <select name="branchId" class="form-select">
  				  <option value="">(No branch)</option>
  				  <c:forEach var="b" items="${branches}">
  				    <option value="${b.branchId}">${b.branchId} — ${b.branchName}</option>
 				   </c:forEach>
				  </select>
			</div>

<!-- Opening balance (optional; defaults to 0.00 if blank) -->
			<div class="col-md-6">
 				 <label class="form-label">Opening Balance</label>
				  <input type="number" name="openingBalance" step="0.01" class="form-control" value="0.00"/>
			</div>

          <div class="col-12 d-flex gap-2">
            <button type="submit" class="btn btn-primary">Submit</button>
          </div>
        </form:form>
      </div>
    </div>

    <!-- Table-->
    <div class="card shadow-sm border-secondary">
      <div class="card-header bg-dark text-light">
        <strong>All Customers</strong>
      </div>
      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-dark table-striped table-hover mb-0 align-middle">
            <thead class="table-secondary text-dark">
              <tr>
                <th style="width: 80px;">ID</th>
                <th>Name</th>
                <th>DOB</th>
                <th>Gender</th>
                <th>SSN</th>
                <th>Account</th>
                <th>Address</th>
                <th>City</th>
                <th>State</th>
                <th>ZIP</th>
                <th>Country</th>
                <th>Edit</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${empty customers}">
                  <tr>
                    <td colspan="7" class="text-center text-muted py-4">No customers yet.</td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="c" items="${customers}">
                    <tr>
                      <td>${c.customerId}</td>
                      <td>${c.customerName}</td>
                      <td>${c.customerDOB}</td>
                      <td>${c.customerGender}</td>
                      <td>${c.customerSSN}</td>
                      <td>${c.customerAccounts}</td>
                      <td>
                        <c:out value="${c.customerAddress.addressLine1}" />
                        <c:if test="${not empty c.customerAddress.addressLine2}">
                          , <c:out value="${c.customerAddress.addressLine2}" />
                        </c:if>
                      </td>
                      <td><c:out value="${c.customerAddress.city}" /></td>
                      <td><c:out value="${c.customerAddress.state}" /></td>
                      <td><c:out value="${c.customerAddress.zip}" /></td>
                      <td><c:out value="${c.customerAddress.country}" /></td>
                      <td>
  						<a class="btn btn-sm btn-outline-warning" href="<c:url value='/customer/page'><c:param name='editId' value='${c.customerId}'/></c:url>">Edit</a>
  					  </td>
  					  <td>
 						 <form action="${pageContext.request.contextPath}/customer/delete/${c.customerId}"
    						  method="post" class="d-inline">
 						 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 						 <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
						</form>
							</td>                   
					 </tr>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
      </div>
    </div>

  </main>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
