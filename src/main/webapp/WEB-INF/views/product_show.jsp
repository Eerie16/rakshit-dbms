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
                <th>Price</th>
                <th>EmployeeId</th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <th>${product.getName()}</th>
                    <th>${product.getPrice()}</th>
                    <th>${product.getEmployeeId()}</th>
                    <th><a href="/product/${product.getProductId()}/edit">edit</a></th>
                </tr>
            </c:forEach>
        </table>
        <h3>Add product</h3>
        <form:form action="/product/new" modelAttribute="newProduct" method="POST">
    <form:label path="name"> Name </form:label>
    <form:input path ="name"/>
    <form:errors path="name"></form:errors>
    <br>
    <form:label path="Price"> Price </form:label>
    <form:input path ="Price"/>
    <form:errors path="Price"></form:errors>
    <br>
    <form:select path="employeeId">
                Employee
                <c:forEach var="employee" items="${employees}">
                    <option value="${employee.getEmployeeId()}">${employee.getLname()} ${employee.getFname()}</option>
                </c:forEach>
            </form:select>
            <br>
            <input type="submit" value="submit"></input>
    </form:form>
    </body>
</html>