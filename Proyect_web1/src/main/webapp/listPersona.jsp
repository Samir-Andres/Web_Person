<%@page import="DAO.PersonaDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestión de Personas</title>

<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f0f2f5;
	margin: 0;
	padding: 0;
	line-height: 1.6;
}

.container {
	width: 85%;
	margin: 40px auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

h1, h2, h3 {
	text-align: center;
	color: #333;
}

.header-title {
	background-color: #007bff;
	color: #fff;
	padding: 15px;
	border-top-left-radius: 8px;
	border-top-right-radius: 8px;
	text-align: center;
	margin-bottom: 20px;
}

.table-container {
	width: 100%;
	overflow-x: auto;
}

.custom-table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.custom-table th, .custom-table td {
	padding: 12px 15px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

.custom-table thead th {
	background-color: #495057;
	color: white;
	text-transform: uppercase;
	letter-spacing: 0.05em;
}

.custom-table tbody tr:nth-child(odd) {
	background-color: #f8f9fa;
}

.custom-table tbody tr:hover {
	background-color: #e9ecef;
}

.button {
	display: inline-block;
	padding: 8px 12px;
	margin-right: 5px;
	font-size: 14px;
	font-weight: bold;
	text-align: center;
	text-decoration: none;
	border-radius: 5px;
	border: none;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

.button-primary {
	background-color: #007bff;
	color: #fff;
}

.button-primary:hover {
	background-color: #0056b3;
}

.button-warning {
	background-color: #ffc107;
	color: #212529;
}

.button-warning:hover {
	background-color: #e0a800;
}

.button-danger {
	background-color: #dc3545;
	color: #fff;
}

.button-danger:hover {
	background-color: #c82333;
}

.text-center {
	text-align: center;
}

th {
	text-align: center;


}

.simple-input {
    width: 300px;
    padding: 3px 15px;
    font-size: 16px;
    font-family: 'Segoe UI', Arial, sans-serif;
    color: #333;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
}

.simple-input:focus {
    border-color: #007bff;
    box-shadow: 0 0 8px rgba(0, 123, 255, 0.25);
    outline: none;
    background-color: #fff;
}

.simple-input::placeholder {
    color: #aaa;
    font-style: italic;
}

.select-styled {
    width: 220px;
    padding: 3px 15px;
    font-size: 16px;
    font-family: 'Segoe UI', Arial, sans-serif;
    color: #333;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
    cursor: pointer;
    -webkit-appearance: none; /* Elimina la apariencia por defecto en navegadores WebKit */
    -moz-appearance: none;    /* Elimina la apariencia por defecto en Firefox */
    appearance: none;    
    text-align: center;     /* Elimina la apariencia por defecto en otros navegadores */
}

.select-styled:focus {
    border-color: #007bff;
    box-shadow: 0 0 8px rgba(0, 123, 255, 0.25);
    outline: none;
    background-color: #fff;
}
</style>
</head>
<body>

	<div class="container">
		<div class="header-title">
			<h2>Gestión de Personas</h2>
		</div>
		
		<div class="text-center" style="margin-bottom: 20px;">
			<a href="PersonasServlet?action=insert" class="button button-primary">Insertar
				Nueva Persona</a>
		</div>
		
		<div style="margin-bottom: 20px;">
			<form action="PersonasServlet" method="get">
				<input type="hidden" name="action" value="list"/>
				<label for="search">Buscar por nombre:</label>
				<input class="simple-input" type="text" id="search" name="search" placeholder="Escribe el nombre...">
				
				<label for="sort">Ordenar por:</label>
				<select class="select-styled" id="sort" name="sort">
					<option value="id" <c:if test="${param.sort == 'id'}">selected</c:if>>ID</option>
					<option value="nombre" <c:if test="${param.sort == 'nombre'}">selected</c:if>>Nombre</option>
					<option value="apellido" <c:if test="${param.sort == 'apellido'}">selected</c:if>>Apellido</option>
					<option value="edad" <c:if test="${param.sort == 'edad'}">selected</c:if>>Edad</option>
				</select>
				
				<button type="submit" class="button button-primary">Aplicar</button>
			</form>
		</div>

		<div class="table-container">
			<table class="custom-table">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Apellido</th>
						<th>Edad</th>
						<th colspan="3" class="text-center">Acción</th>
					</tr>
				</thead>
				<tbody>
			
					<c:forEach items="${personas}" var="persona">
						<tr>
							<td><c:out value="${persona.id}" /></td>
							<td><c:out value="${persona.nombre}" /></td>
							<td><c:out value="${persona.apellido}" /></td>
							<td><c:out value="${persona.edad}" /></td>
							<td class="text-center"><a href="PersonasServlet?action=edit&personaId=${persona.id}"class="button button-warning">Actualizar</a></td>
							<td class="text-center"><a href="PersonasServlet?action=view&personaId=${persona.id}" class="button button-primary">Ver</a></td>
						    <td class="text-center"><a href="PersonasServlet?action=delete&personaId=${persona.id}" class="button button-danger">Eliminar</a></td>
						  
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

</body>
</html>