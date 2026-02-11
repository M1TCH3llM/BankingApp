<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg bg-dark border-bottom border-secondary">
  <div class="container">
    <a class="navbar-brand text-light" href="${pageContext.request.contextPath}/home">Bank App</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/branch/page">Branches</a></li>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/page">Customers</a></li>
        </sec:authorize>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/page">Users</a></li>
        
        <sec:authorize access="hasRole('ROLE_ADMIN')">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/role/page">Roles</a></li>
        </sec:authorize>
        
        <sec:authorize access="hasRole('ROLE_ADMIN')">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/account/page">Accounts</a></li>
   		</sec:authorize>
   		
   		 <sec:authorize access="hasRole('ROLE_ADMIN')">
   		<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/tx/page">Transactions</a></li>
        </sec:authorize>
        
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
      </ul>
      <c:if test="${not empty loggedInUser}">
        <span class="navbar-text text-light">
          Logged in: ${fn:toUpperCase(loggedInUser)}
        </span>
      </c:if>
    </div>
  </div>
</nav>
<sec:authorize access="isAuthenticated()">
  <sec:authentication property="name" var="username"/>
  <sec:authentication property="authorities" var="authorities"/>

  <div class="bg-dark border-bottom">
    <div class="container py-2 small">
      <span class="text-light me-3">
        Signed in as <strong>${username}</strong>
      </span>

       <c:set var="rolesCsv" value="${fn:replace(fn:replace(authorities, '[',''), ']','')}" />
      <c:forEach var="r" items="${fn:split(rolesCsv, ', ')}">
        <c:set var="roleClean" value="${fn:replace(fn:replace(fn:trim(r), 'ROLE_', ''), '_', ' ')}" />
        <span class="text-light me-3">Role: <strong>${roleClean}</strong></span>
      </c:forEach>
    </div>
  </div>
</sec:authorize>