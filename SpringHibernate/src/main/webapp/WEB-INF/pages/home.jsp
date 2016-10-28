<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to WorkStation Management</title>
</head>
<body>
	<h1>Home page</h1>
	<p>
		${mensaje}<br>
		<a href="<c:url value="/wkst/add"/>">Add new WorkStation</a>
		
		<a href="<c:url value="/wkst/list"/>">WorkStation List</a>
		
	</p>
</body>
</html>