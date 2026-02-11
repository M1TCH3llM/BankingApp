<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>User Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-body">

      <jsp:include page="header.jsp" />
<main class="container py-4">
    <div class="card shadow-sm border-secondary">
     <sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="card-header bg-dark text-light">
          <strong>Create User</strong>
        </div>
        <div class="card-body">
            <form:form modelAttribute="user" method="post" class="row g-3">
				 <form:hidden path="userId"/> 
                <!-- Username -->
                <div class="col-md-6">
                    <label class="form-label">Username</label>
                    <form:input path="username" cssClass="form-control"/>
                    <form:errors path="username" cssClass="text-danger small"/>
                </div>

                <!-- Password -->
                <div class="col-md-6">
                    <label class="form-label">Password</label>
                    <form:password path="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="text-danger small"/>
                </div>

                <!-- Email -->
                <div class="col-md-6">
                    <label class="form-label">Email</label>
                    <form:input path="email" cssClass="form-control"/>
                    <form:errors path="email" cssClass="text-danger small"/>
                </div>

                
                <!-- Roles -->
                <div class="col-md-6">
   					 <label class="">Roles</label>
   				
   						 <form:checkboxes path="roles" 
                    		 items="${roles}" 
                    		 itemValue="roleId" 
                     		itemLabel="roleName" 
                    		 cssClass="form-check-input me-2" 
                    		 element="div"
                    	/>
   						 <form:errors path="roles" cssClass="text-danger small"/>
				</div>
				
				<div class="col-12">
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
                

            </form:form>
        </div>
        </sec:authorize>
    </div>
    <br/>
    <div class="card shadow-sm border-secondary">
      <div class="card-header bg-dark text-light">
        <strong>All Users</strong>
      </div>
      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-dark table-striped table-hover mb-0 align-middle">
            <thead class="table-secondary text-dark">
              <tr>
                <th style="width: 80px;">ID</th>
                <th>Name</th>
                <th>Roles</th>
                <th>Email</th>
                <th>Edit</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${empty users}">
                  <tr>
                    <td colspan="7" class="text-center text-muted py-4">No users yet.</td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="u" items="${users}">
                    <tr>
                      <td>${u.userId}</td>
                      <td>${u.username}</td>
                      <td><c:forEach var="r" items="${u.roles}">
     						 <span>${r.roleName}</span>
 							 </c:forEach></td>
                      <td>${u.email}</td>
                      
                      <td>
                      <a class="btn btn-sm btn-outline-warning"
   							href="${pageContext.request.contextPath}/user/page?editId=${u.userId}">
   							Edit
					  </a>
  					  </td>
  					 <td>
  					<form action="${pageContext.request.contextPath}/user/delete/${u.userId}" method="post" class="d-inline">
   						 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
   						 
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

</body>
</html>

