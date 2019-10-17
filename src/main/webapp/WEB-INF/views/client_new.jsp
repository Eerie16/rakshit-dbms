<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
    <head>
        <title>
            Add Client
        </title>
    </head>
    <body>
        <form:form action="/client/new" modelAttribute="client"   method="POST">
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
            <form:label path="fname">First Name</form:label>
            <form:input path="fname"/>
            <form:errors path="fname"></form:errors>
            <br>
            <form:label path="lname">Last Name</form:label>
            <form:input path="lname"/>
            <form:errors path="lname"></form:errors>
            <br>
            <form:label path="city">City</form:label>
            <form:input path="city"/>
            <form:errors path="city"></form:errors>
            <br>
            <form:label path="street">street</form:label>
            <form:input path="street"/>
            <form:errors path="street"></form:errors>
            <br>
            <form:label path="phoneNumber">phoneNumber</form:label>
            <form:input type="text" pattern="[6-9]\d{9}" path="phoneNumber"/>       
            <form:errors path="phoneNumber"></form:errors>
            <br>
            <form:label path="organisation">organisation</form:label>
            <form:input path="organisation"/>
            <form:errors path="organisation"></form:errors>
            <br>
            <form:label path="gender">Gender</form:label>
            <form:radiobutton path="gender" value="M" label="Male"/>
            <form:radiobutton path="gender" value="F" label="FeMale"/>
            <form:errors path="gender"></form:errors>
            <br>
            <input type="submit" value="submit"></input>
        </form:form>
    </body>
</html>