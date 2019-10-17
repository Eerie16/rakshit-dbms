<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  


<html>
    <head>
        <title>
            Supplier Details
        </title>
    </head>
    <body>
        <form:form method="POST" action="/supplier/${supplier.getSupplierId()}/edit" modelAttribute="supplier">
            <h1>Name: <form:input type="text" path="name" value="${supplier.getName()}"/> </h1>
            
            contact: <form:input type="number" path="contact" value="${client.getContact()}"/>
            <input type="submit" value="Update">
        </form:form>
        <h3>Add Raw Material</h3>
<select id="rawMaterialId" type="number">
    <c:forEach var="rawMaterial" items="${rawMaterials}">
        <option value="${rawMaterial.getRawMaterialId()}">${rawMaterial.getName()}</option>
    </c:forEach>
</select>
<input type="button" id="add_rawMaterial_btn" value="Add">
<h3>Place New Order</h3>
<select id="supplyOrderRawMaterialId" type="number">
    <c:forEach var="rawMaterial" items="${suppliedRawMaterials}">
        <option value="${rawMaterial.get('rawMaterialId')}">${rawMaterial.get("name")}</option>
    </c:forEach>
</select>
<input type="number" id="quantity" />
<input type="button" id="add_supplyOrder_btn" value="add"> 
<h3>Pending Orders</h3>
<table>
    <tr>
        <th>Employee</th>
        <th>Raw Material</th>
        <th>quantity</th>
    </tr>
    <c:forEach var="order" items="${PendingOrders}">
        <tr>
            <th>${order.get("Fname")} ${order.get("Lname")}</th>
            <th>${order.get("name")}</th>
            <th>${order.get("quantity")}</th>
            <c:if test="${rolename.equals('Admin')||rolename.equals('Employee')}">
            <th><a href="/supplyOrder/edit/${order.get('supplyOrderId')}">Order completed</a></th>
            </c:if>
        </tr>
    </c:forEach>
</table>
<h3>completed Orders</h3>
<table>
    <tr>
        <th>Employee</th>
        <th>Raw Material</th>
        <th>quantity</th>
    </tr>
    <c:forEach var="order" items="${CompletedOrders}">
        <tr>
            <th>${order.get("Fname")} ${order.get("Lname")}</th>
            <th>${order.get("name")}</th>
            <th>${order.get("quantity")}</th>
        </tr>
    </c:forEach>
</table>


        <table>
            <tr>
                <th>Supplied Raw Materials</th>
            </tr>
            <c:forEach var="rawMaterial" items="${suppliedRawMaterials}">
                <tr>
                    <td>${rawMaterial.get("name")}</td>
                </tr>
            </c:forEach>
        </table>
        <script
        src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
        <script>
            $("#add_rawMaterial_btn").click(function(){
                $.ajax({
                    url:"/supplier/rawMaterial/add",
                    type:"post",
                    data:{
                        supplierId:${supplier.getSupplierId()},
                        rawMaterialId:$("#rawMaterialId").val()
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
        <script>
                $("#add_supplyOrder_btn").click(function(){
                    $.ajax({
                        url:"/supplyOrder/add",
                        type:"post",
                        data:{
                            supplierId:${supplier.getSupplierId()},
                            rawMaterialId:$("#supplyOrderRawMaterialId").val(),
                            quantity:$("#quantity").val(),
                            employeeId:${userid}
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