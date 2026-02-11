<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head><title>Login</title></head>
<body>
    <h2>Login</h2>
   <form action="${pageContext.request.contextPath}/perform_login" method="post">
    <div>
        <label>Username</label>
        <input type="text" name="username"/>
    </div>
    <div>
        <label>Password</label>
        <input type="password" name="password"/>
    </div>
    <button type="submit">Sign In</button>
</form>

<c:if test="${param.error != null}">
    <p style="color:red;">Invalid username or password</p>
</c:if>
<c:if test="${param.logout != null}">
    <p style="color:green;">You have been logged out</p>
</c:if>

</body>
</html>
