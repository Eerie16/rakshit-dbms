<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
    <head>
        <title>
            Add 
        </title>
    </head>
    <body>
            <a href="/client/order/new">New Order</a>
            <a href="/client/detail">Profile</a>
            <a href="/client/previous/order">Order history</a>
            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form><input type="button" onclick="document.forms['logoutForm'].submit()" value="Logout">
        <table border="1px solid black">
            <tr>
                <th>Details</th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <th><a href="/client/order/${order.getClientOrderId()}">"${order.getClientOrderId()}"</a></th>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>