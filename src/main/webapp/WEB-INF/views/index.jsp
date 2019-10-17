<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>SaveForm</title>
</head>
<body>
    <h1>Enter book price and name </h1>
    <form:form action="/index" modelAttribute = "book" method="post">
        <p>Name: <form:input type="text" path = "name" /></p>
        <p>price: <form:input type="int" path ="price" /></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></p>
    </form:form>
</body>
</html>