<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalles de la Persona</title>

<style>
/* Aquí puedes reutilizar los estilos de tu archivo listPersona.jsp */
body {
    font-family: Arial, sans-serif;
    background-color: #f0f2f5;
    margin: 0;
    padding: 0;
    line-height: 1.6;
}
.container {
    width: 600px;
    margin: 40px auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
.details-list {
    list-style-type: none;
    padding: 0;
}
.details-list li {
    background-color: #f8f9fa;
    margin-bottom: 10px;
    padding: 15px;
    border-radius: 5px;
    border-left: 4px solid #007bff;
}
.details-list strong {
    color: #333;
    display: block;
    margin-bottom: 5px;
}
.button {
    display: inline-block;
    padding: 8px 12px;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    text-decoration: none;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    background-color: #007bff;
    color: #fff;
}
.button:hover {
    background-color: #0056b3;
}
</style>
</head>
<body>
    <div class="container">
        <div class="header-title">
            <h2>Detalles de la Persona</h2>
        </div>
        
        <ul class="details-list">
            <li>
                <strong>ID:</strong> <c:out value="${persona.id}" />
            </li>
            <li>
                <strong>Nombre:</strong> <c:out value="${persona.nombre}" />
            </li>
            <li>
                <strong>Apellido:</strong> <c:out value="${persona.apellido}" />
            </li>
            <li>
                <strong>Edad:</strong> <c:out value="${persona.edad}" />
            </li>
        </ul>
        
        <div style="text-align: center; margin-top: 20px;">
            <a href="PersonasServlet?action=listPersona" class="button">Volver a la Lista</a>
        </div>
    </div>
</body>
</html>