<%@page import="java.sql.Connection"%>
<%@page import="controlador.conexion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrador</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>


<style type="text/css">

.btn-primary{
margin: 0 auto;
display: block;
width: 50%;
}

.mb-4 {
	text-align: center;
	font-family: monospace;
}

label {
	font-family: monospace;
}

button {
	font-family: monospace;
}

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



	
</style>
</head>
<body>

<%
conexion test = new conexion();
Connection conexion = test.conectarBD();

if(conexion != null){
	out.print("<h2>Conectado a la base de datos</h2>");
	
}else{
	out.print("<h2>No conectado a la base de datos</h2>");
}
if(conexion != null){
	conexion.close();
}



%>

<div class="container mt-5">
        <h2 class="mb-4">Formulario de Registro</h2>
        <form action="PersonasServlet" method="POST">
            
            <input type="hidden" name="personaId" value="${persona.id}" />
            
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>

            <div class="mb-3">
                <label for="apellido" class="form-label">Apellido</label>
                <input type="text" class="form-control" id="apellido" name="apellido" required>
            </div>

            <div class="mb-3">
                <label for="edad" class="form-label">Edad</label>
                <input type="number" class="form-control" id="edad" name="edad" required>
            </div>

            <button type="submit" class="btn btn-primary">Enviar</button>
        </form>
    </div>
   


</body>
</html>