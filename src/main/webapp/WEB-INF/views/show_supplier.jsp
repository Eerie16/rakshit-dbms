<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
    <head>
        <title>
            Add 
        </title>
    </head>
    <body>
        <table border="1px solid black">
            <tr>
                <th>Name</th>
                <th>Contact</th>
                <th>EmployeeId</th>
            </tr>
            <c:forEach var="supplier" items="${suppliers}">
                <tr>
                    <th>${supplier.getName()}</th>
                    <th>${supplier.getContact()}</th>
                    <th><a href="/supplier/detail/${supplier.getSupplierId()}">edit</a></th>
                </tr>
            </c:forEach>
    </body>
</html>