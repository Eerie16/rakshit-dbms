<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
    <head>
        <title>
            Add Supplier
        </title>
    </head>
    <body>
        <form:form action="/supplier/new" modelAttribute="supplier"   method="POST">
            <form:label path="userName">userName</form:label>
            <form:input path="userName"/>
            <form:errors path="userName"></form:errors>
            <br>
            <form:label path="password">password</form:label>
            <form:input path="password"/>
            <form:errors path="password"></form:errors>
            <br>
            <form:label path="confirmPassword">confirmPassword</form:label>
            <form:input path="confirmPassword"/>
            <form:errors path="confirmPassword"></form:errors>
            <br>
            <form:label path="contact">Phone Number</form:label>
            <form:input path="contact"/>
            <form:errors path="contact"></form:errors>
            <br>
            <form:label path="name">First Name</form:label>
            <form:input path="name"/>
            <form:errors path="name"></form:errors>
            <br>
            <input type="submit" value="submit"></input>
        </form:form>
    </body>
</html>