<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>
    <head>
        <title>
            Place Order
        </title>
    </head>
    <body>
            <a href="/client/order/new">New Order</a>
            <a href="/client/detail">Profile</a>
            <a href="/client/previous/order">Order history</a>
<table> Products Included 
        <tr>
            <th>Product Name</th>
            <th>Quantity</th>
        </tr>
        <c:forEach var="order" items="${list}">
            <tr>
                <td>${order.get("name")}</td>
                    <td>${order.get("quantity")}</td>
            </tr>
        </c:forEach>
</table>
<h3>Add Product</h3>
<select id="productId" type="text">
    <c:forEach var="product" items="${products}">
        <option value="${product.getProductId()}">${product.getName()}</option>
    </c:forEach>
</select>
<input id="quantity" type="number"/>
<input type="button" id="add_product_btn" value ="Add">
<c:if test="${rolename.equals('Admin')||rolename.equals('Employee')}">
    <input id="status" type="text" value="${clientOrder.getStatus()}"/>
    <input type="button" id="add_status_btn" value ="Add">
</c:if>
<h3>Remark</h3>
<input id="remarkNew" type="text" value="${remark}"/>
<input type="button" id="add_remark_btn" value="Update">
<h3>Prior FeedBacks</h3>
<c:forEach var="feedback" items="${feedbacks}">
    <h4>Content</h4>
    <p>"${feedback.getRemark()}"</p>
    <h4>Date Issued:</h4>"${feedback.getDate().toString()}"
    <br>
</c:forEach>
<p></p>
<c:if test="${!rolename.equals('Client')}">
    <input >
</c:if>
<c:if test="${rolename.equals('Client')}">

        <h3>Add feedback</h3>
        <input id="remarkFeedback" type="text"/>
        <input type="button" id="add_feedback_btn" value="Add">
</c:if>
<script
src="https://code.jquery.com/jquery-3.4.1.min.js"
integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
crossorigin="anonymous"></script>
<script>
    $("#add_product_btn").click(function(){
        $.ajax({
            url:"/clientOrder/add",
            type:"post",
            data:{
                clientOrderId:${clientOrder.getClientOrderId()},
                productId:$("#productId").val(),
                quantity:$("#quantity").val()
            },
            success:function(data,status,xhr){
                location.reload();
            },
            error:function(xhr, httpStatusMessage, customErrorMessage){
                alert(error);
            }
        });
    });
</script>
<script>
    $("#add_remark_btn").click(function(){
        $.ajax({
            url:"/client/order/remark",
            type:"post",
            data:{
                clientOrderId:${clientOrder.getClientOrderId()},
                remark:$("#remarkNew").val()
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
        $("#add_feedback_btn").click(function(){
            $.ajax({
                url:"/feedback/order",
                type:"post",
                data:{
                    clientOrderId:${clientOrder.getClientOrderId()},
                    remark:$("#remarkFeedback").val()
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
    $("#add_status_btn").click(function(){
        $.ajax({
            url:"/status/edit",
            type:"post",
            data:{
                clientOrderId:${clientOrder.getClientOrderId()},
                status:$("#status").val()
            },
            success:function(data,status,xhr){
                location.reload();
            },
            error:function(xhr, httpStatusMessage, customErrorMessage){
                alert(error);
            }
        });
    });
</script>
</body>
</html>