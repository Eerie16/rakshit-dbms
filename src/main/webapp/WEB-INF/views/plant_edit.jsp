<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  


<html>
    <head>
        <title>
            Plant Detials
        </title>
    </head>
    <body>
        <form:form method="POST" action="/plant/${plant.getPlantId()}/edit" modelAttribute="plant">
            <h1>Name <form:input type="text" path="name" value="${plant.getName()}"/> address <form:input type="text" path="address" value="${plant.getAddress()}"/>Current Capacity <form:input type="number" path="currentCapacity" value="${plant.getCurrentCapacity()}"/></h1>
            <br>
            
            <input type="submit" value="Update">
        </form:form>
        <h3>RawMaterials Stored</h3>
        <table>
            <tr>
                <th>Name</th>
                <th>quantity</th>
            </tr>
            <c:forEach var="rawMaterial" items="${rawMaterialsStored}">
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
        <h3>Add Raw Material</h3>
        <select id="rawMaterialId" type="number">
                <c:forEach var="rawMaterial" items="${rawMaterials}">
                    <option value="${rawMaterial.getRawMaterialId()}"3>${rawMaterial.getName()}</option>
                </c:forEach>
            </select>
            <input id="quantity" type="number"/>
            <input type="button" id="add_contact_btn" value="Add">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
   /*  Submit form using Ajax */
   $("#add_contact_btn").click(function(e) {
   
      //Prevent default submission of form
      e.preventDefault();
      
      
      $.ajax({
         url : '/plant/${plant.getPlantId()}/rawMaterial/add',
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
        </body>
</html>