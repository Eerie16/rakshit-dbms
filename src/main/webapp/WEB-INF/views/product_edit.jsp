<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  


<html>
    <head>
        <title>
            Product Detials
        </title>
    </head>
    <body>
        <form:form method="POST" action="/product/${product.getProductId()}/edit" modelAttribute="product">
            <h1>Name <form:input type="text" path="name" value="${product.getName()}"/> Price <form:input type="number" path="price" value="${product.getPrice()}"/> 
                Employee<form:select path="employeeId" value="${product.getEmployeeId()}">
                        
                        <c:forEach var="employee" items="${employees}">
                            <option value="${employee.getEmployeeId()}">${employee.getLname()} ${employee.getFname()}</option>
                        </c:forEach>
                    </form:select>
            </h1>
            <br>
            <input type="submit" value="Update">
            
        </form:form>
        <h3>RawMaterials Required</h3>
        <table>
            <tr>
                <th>Name</th>
                <th>quantity</th>
            </tr>
            <c:forEach var="rawMaterial" items="${rawMaterialsRequired}">
                <tr>
                    <td>
                        ${rawMaterial.get("name")}
                    </td>
                    <td>
                        ${rawMaterial.get("quantity")}
                    </td>
                </tr>
            </c:forEach>
        </table>
        <h3>Add RawMaterials</h3>
        <select id="rawMaterialId" type="number">
            <c:forEach var="rawMaterial" items="${rawMaterials}">
                <option value="${rawMaterial.getRawMaterialId()}">${rawMaterial.getName()}</option>
            </c:forEach>
        </select>
        <input id="quantity" type="number"/>
        <input type="button" id="add_contact_btn" value="Add">
        <table>
                <tr>
                    <th>Plant</th>
                    <th>Time Required</th>
                </tr>
                <c:forEach var="plant" items="${plantsManufactures}">
                    <tr>
                        <td>
                            ${plant.get("name")}
                        </td>
                        <td>
                            ${plant.get("timeRequired")}
                        </td>
                    </tr>
                </c:forEach>
        </table>
        <h3>Add Plants</h3>
        Plant Name:<select id="plantId" type="number">
            <c:forEach var="plant" items="${plants}">
                <option value="${plant.getPlantId()}">${plant.getName()}</option>
            </c:forEach>
        </select>
        Hours Required:<input id="hours" type="number">
        <input type="button" id="add_plant_btn" value="Add">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
   /*  Submit form using Ajax */
   $("#add_contact_btn").click(function(e) {
   
      //Prevent default submission of form
      e.preventDefault();
      
      
      $.ajax({
         url : '/product/${product.getProductId()}/rawMaterial/add',
         type: "post",
         data : {
                rawMaterialId:$("#rawMaterialId").val(),
                quantity:$("#quantity").val()
         },
         success:function(data,status,xhr){
                        location.reload();
                    },
                    error:function(error){
                        alert(error);
                    }
      });
   });
});
</script>
       
       <script type="text/javascript">
        $(function() {
           /*  Submit form using Ajax */
           $("#add_plant_btn").click(function(e) {
           
              //Prevent default submission of form
              e.preventDefault();
              
              
              $.ajax({
                 url : '/product/${product.getProductId()}/plant/add',
                 type: "post",
                 data : {
                        plantId:$("#plantId").val(),
                        hours:$("#hours").val()
                 },
                 success:function(data,status,xhr){
                                location.reload();
                            },
                            error:function(error){
                                alert(error);
                            }
              });
           });
        });
        </script>
    </body>
</html>