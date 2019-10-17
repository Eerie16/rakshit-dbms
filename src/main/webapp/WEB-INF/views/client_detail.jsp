<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  


<html>
    <head>
        <title>
            Client Details
        </title>
    </head>
    <body>
            <a href="/client/order/new">New Order</a>
            <a href="/client/detail">Profile</a>
            <a href="/client/previous/order">Order history</a>
            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form><input type="button" onclick="document.forms['logoutForm'].submit()" value="Logout">
        <form:form method="POST" action="/client/edit" modelAttribute="client">
            <h1>First Name: <form:input type="text" path="fname" value="${client.getFname()}"/> Last Name: <form:input type="text" path="lname" value="${client.getLname()}"/></h1>
            <br>
            City : <form:input type="text" path="city" value="${client.getCity()}"/>
            <br>
            Street : <form:input type="text" path="street" value="${client.getStreet()}"/>
            <br>
            phoneNumber: <form:input type="tel" pattern="[6-9]\d{9}" path="phoneNumber" value="${client.getPhoneNumber()}"/>
            <br>
            
            Employee: 
            <form:select path="employeeId" disabled="${rolename.equals('Client')}">
                <c:forEach var="employee" items="${employees}">
                    <c:choose >
                        <c:when test="${employee.getEmployeeId() == client.getEmployeeId()}">
                            <option value="${employee.getEmployeeId()}" selected>${employee.getFname()} ${employee.getLname()}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${employee.getEmployeeId()}">${employee.getFname()} ${employee.getLname()}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>
            
            <form:radiobutton path="gender" value="M" label="Male"></form:radiobutton>
            <form:radiobutton path="gender" value="F" label="Female"></form:radiobutton>
            <br>
            <input type="submit" value="Update">
        </form:form>
        <h3>Add Email</h3>
        <input type="text" id="add_email_tb">
        <table>
            <tr>
                <th>Emails</th>
            </tr>
            <c:forEach var="clientEmail" items="${clientEmails}">
                <tr>
                    <td>${clientEmail.getEmail()}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="button" id="add_email_btn" value="Add">
        <script
        src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
        <script>
            $("#add_email_btn").click(function(){
                $.ajax({
                    url:"/client/email/add",
                    type:"post",
                    data:{
                        clientId:${client.getClientId()},
                        email:$("#add_email_tb").val()
                    },
                    success:function(data,status,xhr){
                        location.reload();
                    },
                    error:function(error){
                        alert("invalid email");
                    }
                });
            });
        </script>
    </body>
</html>