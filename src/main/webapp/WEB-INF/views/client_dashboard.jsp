<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title> Homepage</title>
    </head>
    <body>
        <a href="/client/order/new">New Order</a>
        <a href="/client/detail">Profile</a>
        <a href="/client/previous/order">Order history</a>
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form><input type="button" onclick="document.forms['logoutForm'].submit()" value="Logout">
    </body>
</html>