<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
  <jsp:include page="header.jsp" />

  <main class="container py-4">

    <!-- Form card (ADMIN only) -->
    <sec:authorize access="hasRole('ROLE_ADMIN')">
      <div class="card shadow-sm border-secondary mb-4">
        <div class="card-header bg-dark text-light">
          <strong>Create Branch</strong>
        </div>
        <div class="card-body">
          <form:form modelAttribute="branch"
                     action="${pageContext.request.contextPath}/branch/page"
                     method="post" class="row g-3">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <form:hidden path="branchId"/>

            <div class="col-12">
              <label class="form-label" for="branchName">Branch Name</label>
              <form:input path="branchName" id="branchName" cssClass="form-control" />
              <form:errors path="branchName" cssClass="text-danger small" />
            </div>

            <div class="col-md-6">
              <label class="form-label" for="addr1">Address Line 1</label>
              <form:input path="branchAddress.addressLine1" id="addr1" cssClass="form-control" />
              <form:errors path="branchAddress.addressLine1" cssClass="text-danger small" />
            </div>

            <div class="col-md-6">
              <label class="form-label" for="addr2">Address Line 2</label>
              <form:input path="branchAddress.addressLine2" id="addr2" cssClass="form-control" />
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
              <button type="submit" class="btn btn-primary">Submit</button>
            </div>
          </form:form>
        </div>
      </div>
    </sec:authorize>

    <!-- Table + Paging/Sorting -->
    <div class="card shadow-sm border-secondary">
      <div class="card-header bg-dark text-light d-flex justify-content-between align-items-center">
        <strong>All Branches</strong>
      </div>

      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-dark table-striped table-hover mb-0 align-middle">
            <thead class="table-secondary text-dark">
              <tr>
                <!-- Sortable ID -->
                <th style="width: 80px;">
                  <c:url var="sortIdUrl" value="/branch/page">
                    <c:param name="page" value="0"/>
                    <c:param name="size" value="5"/>
                    <c:param name="sort" value="branchId"/>
                    <c:param name="dir" value="${sort == 'branchId' ? reverseDir : 'asc'}"/>
                  </c:url>
                  <a class="link-dark text-decoration-none" href="${sortIdUrl}">
                    ID
                    <c:if test="${sort == 'branchId'}">
                      <span class="ms-1">${dir == 'asc' ? '&#9650;' : '&#9660;'}</span>
                    </c:if>
                  </a>
                </th>

                <!-- Sortable Name -->
                <th>
                  <c:url var="sortNameUrl" value="/branch/page">
                    <c:param name="page" value="0"/>
                    <c:param name="size" value="5"/>
                    <c:param name="sort" value="branchName"/>
                    <c:param name="dir" value="${sort == 'branchName' ? reverseDir : 'asc'}"/>
                  </c:url>
                  <a class="link-dark text-decoration-none" href="${sortNameUrl}">
                    Name
                    <c:if test="${sort == 'branchName'}">
                      <span class="ms-1">${dir == 'asc' ? '&#9650;' : '&#9660;'}</span>
                    </c:if>
                  </a>
                </th>

                <th>Address</th>
                <th>City</th>
                <th>State</th>
                <th>ZIP</th>
                <th>Country</th>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                  <th>Edit</th>
                  <th>Delete</th>
                </sec:authorize>
              </tr>
            </thead>

            <tbody>
              <c:choose>
                <c:when test="${empty branches}">
                  <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <tr><td colspan="9" class="text-center text-muted py-4">No branches yet.</td></tr>
                  </sec:authorize>
                  <sec:authorize access="!hasRole('ROLE_ADMIN')">
                    <tr><td colspan="7" class="text-center text-muted py-4">No branches yet.</td></tr>
                  </sec:authorize>
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

                      <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <td>
                          <!-- Preserve current page/sort on Edit -->
                          <c:url var="editUrl" value="/branch/page">
                            <c:param name="editId" value="${b.branchId}"/>
                            <c:param name="page" value="${currentPage}"/>
                            <c:param name="size" value="5"/>
                            <c:param name="sort" value="${sort}"/>
                            <c:param name="dir" value="${dir}"/>
                          </c:url>
                          <a class="btn btn-sm btn-outline-warning" href="${editUrl}">Edit</a>
                        </td>
                        <td>
                          <!-- Delete (redirect preservation done in controller Step 4) -->
                          <form action="${pageContext.request.contextPath}/branch/delete/${b.branchId}"
                                method="post" class="d-inline"
                                onsubmit="return confirm('Delete branch #${b.branchId}?');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
                          </form>
                        </td>
                      </sec:authorize>
                    </tr>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
          	<div class="d-flex justify-content-end p-3">
  <nav aria-label="Branch pagination">
    <ul class="pagination mb-0">
      <c:url var="prevUrl" value="/branch/page">
        <c:param name="page" value="${branchPage.number - 1}"/>
        <c:param name="size" value="5"/>
        <c:param name="sort" value="${sort}"/>
        <c:param name="dir" value="${dir}"/>
      </c:url>
      <li class="page-item ${branchPage.first ? 'disabled' : ''}">
        <a class="page-link" href="${prevUrl}">Prev</a>
      </li>

      <c:url var="nextUrl" value="/branch/page">
        <c:param name="page" value="${branchPage.number + 1}"/>
        <c:param name="size" value="5"/>
        <c:param name="sort" value="${sort}"/>
        <c:param name="dir" value="${dir}"/>
      </c:url>
      <li class="page-item ${branchPage.last ? 'disabled' : ''}">
        <a class="page-link" href="${nextUrl}">Next</a>
      </li>
    </ul>
  </nav>
</div>
      </div>
    </div>
  </main>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

