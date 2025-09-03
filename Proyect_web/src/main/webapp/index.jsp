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
<style type="text/css">
    body {
        background-color: #f8f9fa;
        font-family: Arial, sans-serif;
    }
    .form-container {
        max-width: 600px;
        margin: 50px auto;
        padding: 30px;
        background-color: #ffffff;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    .header-title {
        text-align: center;
        margin-bottom: 2rem;
        color: #007bff;
        font-weight: 700;
    }
   .btn-custom {
        width: 100%;
        font-size: 1.25rem; /* Tamaño de fuente más grande */
        padding: 10px 30px; /* Relleno del botón */
        border-radius: 50px; /* Bordes muy redondeados para un look moderno */
        font-weight: bold;
        transition: all 0.3s ease; /* Transición suave para los efectos */
        border: none;
        background-image: linear-gradient(45deg, #007bff, #0056b3); /* Fondo con degradado */
        color: white;
        box-shadow: 0 4px 15px rgba(0, 123, 255, 0.4);
    }
      .btn-custom:hover {
        transform: translateY(-5px); /* Efecto de "levantar" al pasar el mouse */
        box-shadow: 0 8px 20px rgba(0, 123, 255, 0.6);
        background-image: linear-gradient(45deg, #0056b3, #007bff);
    }

    .connection-status {
        text-align: center;
        padding: 10px;
        margin-bottom: 20px;
        border-radius: 5px;
        font-weight: bold;
        
    }

    .status-disconnected {
        background-color: #f8d7da;
        color: black;
    }
</style>
</head>
<body>

<div class="container form-container">
    <%
    conexion test = new conexion();
    Connection conexion = null;
    try {
        conexion = test.conectarBD();
    } catch (Exception e) {
        e.printStackTrace();
    }

    if(conexion != null){
        out.print("<div class='connection-status status-connected'><h1>Conectado a la base de datos</h1></div>");
    } else {
        out.print("<div class='connection-status status-disconnected'><h1>No conectado a la base de datos</h1></div>");
    }
    if(conexion != null){
        conexion.close();
    }
    %>

    <h2 class="header-title">Formulario de Registro</h2>
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

        <button type="submit" class="btn btn-primary btn-custom">Enviar</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>