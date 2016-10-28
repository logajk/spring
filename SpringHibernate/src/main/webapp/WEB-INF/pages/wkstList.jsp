<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de terminales</title>
</head>
<body>
	<h1>Listado de terminales</h1>
	<p>Aquí puede  ver el listado de terminales, modificarlos, borrarlos o actualizarlos2</p>
	<c:if test="${listado == null}">
		No hay listado
	</c:if>
	<c:if test="${listado != null}">
		Sí hay listado
	</c:if>
		
		<table border="1px" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th>id</th>
					<th>Estado</th>
					<th>Estado prev.</th>
					<th>Pais</th>
					<th>Estado</th>
					<th>Ciudad</th>
					<th>Cod. Postal</th>
					<th>Fec. Instal.</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<c:forEach var="wkst" items="${listado}">
				<tbody>
					<tr>
						<td><c:out value="${wkst.id}"/></td>
						<td>${wkst.statusId}</td>
						<td>${wkst.prevstatusId}</td>
						<td>${wkst.country}</td>
						<td>${wkst.state}</td>
						<td>${wkst.city}</td>
						<td>${wkst.zipcode}</td>
						<td>${wkst.installationdate}</td>
						<td>
							<a href="<c:url value="/wkst/edit/${wkst.id}"/>">Modificar</a>
							<a href="<c:url value="/wkst/delete/${wkst.id}"/>">Borrar</a>
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	<p><a href="<c:url value="/index"/>">Home page</a></p>
	
</body>
</html>