<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Product</title>
</head>
<body>

<form action="updateproduct" method="post">
	<input type="hidden" name="id" value="${productID}">
    Code: <input type="text" name="code" required> <br>
    Description: <input type="text" name="description" required> <br>
    <!--  these additional attributes for min and step allow us to take in a double variable -->
    List Price: <input type="number" min="1" step="any" name="listPrice"> <br>
    <input type="submit" value="Update Product">

</form>

</body>
</html>