<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>
    <head>
        <title>Book list</title>
    </head>
    <body>
            <table border="1px solid black">
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                    </tr>
                    <c:forEach var="book" items="${list}">
                        <tr>
                            <td>${book.name}  </td>
                            <td>${book.price}</td>
                        </tr>
                    </c:forEach>
                </table>
    </body>
</html>