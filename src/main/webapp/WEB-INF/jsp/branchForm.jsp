<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Branches</title>

  <!-- Bootstrap 5.3 (dark ready) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-body">
  <nav class="navbar navbar-expand-lg bg-dark border-bottom border-secondary">
    <div class="container">
      <a class="navbar-brand text-light" href="#">Bank • Branches</a>
    </div>
  </nav>

  <main class="container py-4">

    <!-- Form card -->
    <div class="card shadow-sm border-secondary mb-4">
      <div class="card-header bg-dark text-light">
        <strong>Create Branch</strong>
      </div>
      <div class="card-body">

        <form:form modelAttribute="branch"
                   action="${pageContext.request.contextPath}/branch/page"
                   method="post" class="row g-3">

          <!-- CSRF (safe if Spring Security present; harmless otherwise) -->
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <form:hidden path="branchId"/>
          <div class="col-12">
            <label class="form-label" for="branchName">Branch Name</label>
            <form:input path="branchName" id="branchName" cssClass="form-control" placeholder="" />
            <form:errors path="branchName" cssClass="text-danger small" />
          </div>

          <div class="col-md-6">
            <label class="form-label" for="addr1">Address Line 1</label>
            <form:input path="branchAddress.addressLine1" id="addr1" cssClass="form-control" placeholder="" />
            <form:errors path="branchAddress.addressLine1" cssClass="text-danger small" />
          </div>

          <div class="col-md-6">
            <label class="form-label" for="addr2">Address Line 2</label>
            <form:input path="branchAddress.addressLine2" id="addr2" cssClass="form-control" placeholder="" />
            <form:errors path="branchAddress.addressLine2" cssClass="text-danger small" />
          </div>

          <div class="col-md-4">
            <label class="form-label" for="city">City</label>
            <form:input path="branchAddress.city" id="city" cssClass="form-control" />
            <form:errors path="branchAddress.city" cssClass="text-danger small" />
          </div>

          <div class="col-md-4">
            <label class="form-label" for="state">State/Province</label>
            <form:input path="branchAddress.state" id="state" cssClass="form-control" />
            <form:errors path="branchAddress.state" cssClass="text-danger small" />
          </div>

          <div class="col-md-4">
            <label class="form-label" for="zip">ZIP/Postal</label>
            <form:input path="branchAddress.zip" id="zip" cssClass="form-control" />
            <form:errors path="branchAddress.zip" cssClass="text-danger small" />
          </div>

          <div class="col-md-6">
            <label class="form-label" for="country">Country</label>
            <form:input path="branchAddress.country" id="country" cssClass="form-control" />
            <form:errors path="branchAddress.country" cssClass="text-danger small" />
          </div>

          <div class="col-12 d-flex gap-2">
            <button type="submit" class="btn btn-primary">Create</button>
          </div>
        </form:form>
      </div>
    </div>

    <!-- Table-->
    <div class="card shadow-sm border-secondary">
      <div class="card-header bg-dark text-light">
        <strong>All Branches</strong>
      </div>
      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-dark table-striped table-hover mb-0 align-middle">
            <thead class="table-secondary text-dark">
              <tr>
                <th style="width: 80px;">ID</th>
                <th>Name</th>
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
                <c:when test="${empty branches}">
                  <tr>
                    <td colspan="7" class="text-center text-muted py-4">No branches yet.</td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="b" items="${branches}">
                    <tr>
                      <td>${b.branchId}</td>
                      <td>${b.branchName}</td>
                      <td>
                        <c:out value="${b.branchAddress.addressLine1}" />
                        <c:if test="${not empty b.branchAddress.addressLine2}">
                          , <c:out value="${b.branchAddress.addressLine2}" />
                        </c:if>
                      </td>
                      <td><c:out value="${b.branchAddress.city}" /></td>
                      <td><c:out value="${b.branchAddress.state}" /></td>
                      <td><c:out value="${b.branchAddress.zip}" /></td>
                      <td><c:out value="${b.branchAddress.country}" /></td>
                      <td>
  						<a class="btn btn-sm btn-outline-warning" href="<c:url value='/branch/page'><c:param name='editId' value='${b.branchId}'/></c:url>">Edit</a>
  					  </td>
  					  <td>
 						 <form action="${pageContext.request.contextPath}/branch/delete/${b.branchId}"
      									 method="post" class="d-inline">
    								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
   									 <input type="hidden" name="_method" value="delete" />
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
