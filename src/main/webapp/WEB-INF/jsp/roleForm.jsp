<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Roles</title>

  <!-- Bootstrap 5.3 (dark ready) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-body">
  <nav class="navbar navbar-expand-lg bg-dark border-bottom border-secondary">
    <div class="container">
      <a class="navbar-brand text-light" href="#">Bank • Roles</a>
    </div>
  </nav>

  <main class="container py-4">

    <!-- Form card -->
    <div class="card shadow-sm border-secondary mb-4">
      <div class="card-header bg-dark text-light">
        <strong>Create Role</strong>
      </div>
      <div class="card-body">

        <form:form modelAttribute="role"
                   action="${pageContext.request.contextPath}/role/page"
                   method="post" class="row g-3">

          <!-- CSRF (safe if Spring Security present; harmless otherwise) -->
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <form:hidden path="roleId"/>
          <div class="col-12">
            <label class="form-label" for="branchName">Role Name</label>
            <form:input path="roleName" id="roleName" cssClass="form-control" placeholder="" />
            <form:errors path="roleName" cssClass="text-danger small" />
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
        <strong>All Roles</strong>
      </div>
      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-dark table-striped table-hover mb-0 align-middle">
            <thead class="table-secondary text-dark">
              <tr>
                <th style="width: 80px;">ID</th>
                <th>Name</th>
                <th>Edit</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${empty roles}">
                  <tr>
                    <td colspan="7" class="text-center text-muted py-4">No roles yet.</td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="r" items="${roles}">
                    <tr>
                      <td>${r.roleId}</td>
                      <td>${r.roleName}</td>
                      
                      <td>
                      <a class="btn btn-sm btn-outline-warning"
   							href="${pageContext.request.contextPath}/role/page?editId=${r.roleId}">
   							Edit
					  </a>
  					  </td>
  					 <td>
  					<form action="${pageContext.request.contextPath}/role/delete" method="post" class="d-inline">
   						 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
   						 <input type="hidden" name="id" value="${r.roleId}" />
   						 <button type="submit" class="btn btn-sm btn-outline-danger">
   						   Delete
    					</button>
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
