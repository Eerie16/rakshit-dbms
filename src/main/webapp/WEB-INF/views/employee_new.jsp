<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
    <head>
        <title>
            Add employee
        </title>
    </head>
    <body>
        <form:form action="/employee/new" modelAttribute="employee"   method="POST">
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
            <form:label path="gender">Gender</form:label>
            <form:radiobutton path="gender" value="M" label="Male"/>
            <form:radiobutton path="gender" value="F" label="FeMale"/>
            <form:errors path="gender"></form:errors>
            <br>
            <form:input type="date" path="dateOfJoining" placeholder="DOJ" value="2019-10-10"></form:input>
            <form:select path="plantId">
                <c:forEach var="plant" items="${plants}">
                    <option value="${plant.getPlantId()}">${plant.getName()}</option>
                </c:forEach>
            </form:select>
            <br>
            <input type="submit" value="submit"></input>
        </form:form>
    </body>
</html>