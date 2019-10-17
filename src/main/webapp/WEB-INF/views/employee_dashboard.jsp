<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title> Homepage</title>
    </head>
    <body>
        <h1>Welcome "${username}"</h1>
        <a href="/supplier/new">new supplier</a>
        <a href="/supplier/show">all supplier</a>
        <a href="/plant/show">all plants</a>
        
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form><input type="button" onclick="document.forms['logoutForm'].submit()" value="Logout">
    </body>
</html>