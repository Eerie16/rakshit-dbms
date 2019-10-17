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
                <th>address</th>
                <th>currentCapacity</th>
            </tr>
            <c:forEach var="plant" items="${plants}">
                <tr>
                    <th>${plant.getName()}</th>
                    <th>${plant.getAddress()}</th>
                    <th>${plant.getCurrentCapacity()}</th>
                    <th><a href="/plant/${plant.getPlantId()}/edit">edit</a></th>
                </tr>
            </c:forEach>
        </table>
        <h3>Add Plant</h3>
            <input id="name" type="text"/>
            <input id="address" type="text"/>
            <input id="currentCapacity" type="number"/>
            <input type="button" id="add_contact_btn" value="Add">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
   /*  Submit form using Ajax */
   $("#add_contact_btn").click(function(e) {
   
      //Prevent default submission of form
      e.preventDefault();
      
      
      $.ajax({
         url : '/plant/new',
         type: "post",
         data : {
                name:$("#name").val(),
                address:$("#address").val(),
                currentCapacity:$("#currentCapacity").val()
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