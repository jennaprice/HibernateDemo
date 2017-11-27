<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
<a href="getnewprod"> Add Product</a>
<form action="searchbyproduct" method="get">
<label>Search for Products</label>
<input type="text" name="searchproduct"> 
<input type="submit" value="submit">

</form>

<table border="1">
		<c:forEach var="myVar" items="${message}">
			<tr>
				<td>${myVar.productID}</td>
				<td>${myVar.code}</td>
				<td>${myVar.description}</td>
				<td>${myVar.listPrice}</td>
				<td><a href="delete?id=${myVar.productID}"> Delete </a></td>
				<td><a href="update?id=${myVar.productID}"> Update </a></td>

			</tr>

		</c:forEach>

	</table>
</body>
</html>