<%@page import="controlador.conexion"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Estado de la Conexión</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<style>
    body {
        background-color: #f0f2f5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .main-container {
        text-align: center;
        background-color: #ffffff;
        padding: 50px;
        border-radius: 15px;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
    }
    .btn-custom {
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
    .status-text {
        font-size: 1.5rem;
        margin-bottom: 30px;
    }
    .status-connected {
        color: #28a745;
    }
    .status-disconnected {
        color: #dc3545;
    }
    .btn-custom{
     display: block;
     margin: 20px;
    }
    
    .btn-warning{
    display: block;
    
    }
    .btn-warning:hover {
        transform: translateY(-5px); /* Efecto de "levantar" al pasar el mouse */
        box-shadow: 0 0 0 1px rgba(255, 193, 7, 0.5);
     
    }
    
     .btn-warning {
     margin: 5px auto;
        width: 90%;
        font-size: 20px; /* Tamaño de fuente más grande */
        padding: 10px 30px; /* Relleno del botón */
        border-radius: 50px; /* Bordes muy redondeados para un look moderno */
        font-weight: bold;
        transition: all 0.3s ease; /* Transición suave para los efectos */
        border: none;
        color: white;
        box-shadow: 0 4px 15px rgba(0, 123, 255, 0.4);
    }
   
</style>
</head>
<body>
    <div class="main-container">
        <h1 class="mb-4">Estado de la Conexión</h1>

        <%
        conexion test = new conexion();
        Connection conexion = test.conectarBD();

        if(conexion != null){
            out.print("<p class='status-text status-connected'>Conectado a la base de datos</p>");
        }else{
            out.print("<p class='status-text status-disconnected'>No conectado a la base de datos</p>");
        }
        
        if(conexion != null){
            conexion.close();
        }
        %>
        <a href="PDFitex" class="btn btn-custom" target="_blank">Generar Reporte PDF</a>
        <a href="TablaPDF" class="btn btn-custom" target="_blank">Tabla PDF</a>
        <a href="PersonasServlet" class="btn btn-warning">Volver</a>
    </div>

      
      

    
</body>
</html>