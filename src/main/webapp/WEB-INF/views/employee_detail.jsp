<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  

<html>
    <head>
        <title>
            Employee Details
        </title>
    </head>
    <body>
        <form:form method="POST" action="/employee/${employee.getEmployeeId()}/edit" modelAttribute="employee">
            <h1>First Name: <form:input type="text" path="fname" value="${employee.getFname()}"/> Last Name: <form:input type="text" path="lname" value="${employee.getLname()}"/></h1>
            <br>
            City : <form:input type="text" path="city" value="${employee.getCity()}"/>
            <br>
            
            <c:if test="${rolename.equals('Admin')}">
            Salary: <form:input type="number" path="salary" value="${employee.getSalary()}"/>
            <br>
            </c:if>
            Position: <form:input type="text" path="position" value="${employee.getPosition()}"/>
            <br>
            Plant: 
            <form:select path="plantId">
                <c:forEach var="plant" items="${plants}">
                    <c:choose >
                        <c:when test="${plant.getPlantId() == employee.getPlantId()}">
                            <option value="${plant.getPlantId()}" selected>${plant.getName()}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${plant.getPlantId()}">${plant.getName()}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>
            
            <form:radiobutton path="gender" value="M" label="Male"></form:radiobutton>
            <form:radiobutton path="gender" value="F" label="Female"></form:radiobutton>
            <br>
            <input type="submit" value="Update">
        </form:form>
        <h3>Add contacts</h3>
        <input type="number" id="add_contact_tb">
        <table>
            <tr>
                <th>Contact</th>
            </tr>
            <c:forEach var="employeePhoneNo" items="${employeePhoneNos}">
                <tr>
                    <td>${employeePhoneNo.getPhoneNo()}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="button" id="add_contact_btn" value="Add">
        <script
        src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
        <script>
            $("#add_contact_btn").click(function(){
                $.ajax({
                    url:"/employee/contacts/add",
                    type:"post",
                    data:{
                        employeeId:${employee.getEmployeeId()},
                        phoneNo:$("#add_contact_tb").val()
                    },
                    success:function(data,status,xhr){
                        location.reload();
                    },
                    error:function(error){
                        alert(error);
                    }
                });
            });
        </script>
    </body>
</html>