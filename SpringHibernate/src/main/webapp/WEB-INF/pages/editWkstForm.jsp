<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar WorkStation</title>
</head>
<body>
	<h1>Página de modificación de Workstation</h1>
	<p>Aquí puede modificar un WorkStation</p>
<%-- 	<p>${mensaje}</p> --%>
	<form:form method="POST" commandName="wkst" action="${pageContext.request.contextPath}/wkst/edit/${team.id}.html">
		<table>
			<tr>
				<td>Estado:</td>
				<td><form:input path="statusId"/></td>
			</tr>
			<tr>
				<td>Estado anterior:</td>
				<td><form:input path="prevstatusId"/></td>
			</tr>
			<tr>
				<td>Pais:</td>
				<td><form:input path="country"/></td>
			</tr>
			<tr>
				<td>Ciudad:</td>
				<td><form:input path="city"/></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Modificar"></td>
			</tr>
		</table>
	</form:form>
	<p><a href="SpringHibernate/index.html">Home page</a></p>
</body>
</html>