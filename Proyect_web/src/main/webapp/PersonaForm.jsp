<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Persona</title>
</head>
<body>
    <h2>Editar Persona</h2>

    <form action="PersonasServlet" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${persona.id}">

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${persona.nombre}" required><br><br>

        <label for="apellido">Apellido:</label>
        <input type="text" id="apellido" name="apellido" value="${persona.apellido}" required><br><br>

        <label for="edad">Edad:</label>
        <input type="number" id="edad" name="edad" value="${persona.edad}" required><br><br>

        <button type="submit">Guardar Cambios</button>
        <a href="PersonasServlet?action=list">Cancelar</a>
    </form>
</body>
</html>