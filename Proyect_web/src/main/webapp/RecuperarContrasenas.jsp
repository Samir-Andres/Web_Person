<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cambiar Contraseña</title>
 <link   href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
    
    <style type="text/css">
    
    body {
	font-family: Arial, sans-serif;
	background-color: #f1f1f1;
	margin: 0;
	padding: 40px;
}

    .form-container {
	background-color: #fff;
	max-width: 500px;
	margin: 5px auto;
	padding: 30px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
}
    
    .btn-custom {
	width: 100%;
	font-family: "Times New Roman", Times, serif;
	font-size: 1.25rem; /* Tamaño de fuente más grande */
	padding: 5px 30px; /* Relleno del botón */
	border-radius: 50px; /* Bordes muy redondeados para un look moderno */
	font-weight: bold;
	transition: all 0.3s ease; /* Transición suave para los efectos */
	border: none;
	background-image: linear-gradient(45deg, #007bff, #0056b3);
	/* Fondo con degradado */
	color: white;
	box-shadow: 0 4px 15px rgba(0, 123, 255, 0.4);
}
.btn-custom:hover {
	transform: translateY(-5px);
	/* Efecto de "levantar" al pasar el mouse */
	box-shadow: 0 8px 20px rgba(0, 123, 255, 0.6);
	background-image: linear-gradient(45deg, #0056b3, #007bff);
}
h2{
	font-family: "Times New Roman", Times, serif;

}
label {
		font-family: "Times New Roman", Times, serif;
}
    
    </style>
</head>
<body>
   
    
    <div class="form-container">
     <h2>Cambiar Contraseña</h2>
    <form class="form-container" action="ActualizarContrasenaServlet" method="post">
        <input type="hidden" name="correo" value="<%= request.getAttribute("correo") %>">
        <input class="form-control" type="hidden" name="token" value="<%= request.getAttribute("token") %>">

        <label for="nueva_contrasena">Nueva Contraseña:</label><br>
        <input class="form-control" type="password" id="nueva_contrasena" name="nueva_contrasena" required><br><br>

        <label for="confirmar_contrasena">Confirmar Contraseña:</label><br>
        <input class="form-control"  type="password" id="confirmar_contrasena" name="confirmar_contrasena" required><br><br>

        <button class="btn btn-custom" type="submit">Cambiar Contraseña</button>
    </form>
    <%
        // Obtener el mensaje del servlet
        String mensaje = (String) request.getAttribute("mensaje");
        if (mensaje != null && !mensaje.isEmpty()) {
            // Determinar el color del mensaje (éxito o error)
            String color = mensaje.contains("exitosa") ? "green" : "red";
    %>
            <p style="color: <%= color %>;"><%= mensaje %></p>
    <%
        }
    %>
    
    </div>
</body>
</html>