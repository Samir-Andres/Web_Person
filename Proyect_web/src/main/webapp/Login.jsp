<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.login-container {
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 5 7px 10px rgba(3, 7, 1, 0.1);
	width: 400px;
	text-align: center;
}

h2 {
	margin-bottom: 20px;
	color: #333;
}

.form-group {
	margin-bottom: 15px;
	text-align: left;
}

label {
	display: block;
	margin-bottom: 5px;
	color: #555;
	font-family: Timew new roman;
}

input[type="text"], input[type="password"] {
	width: 100%;
	padding: 10px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.error {
	color: red;
	margin-top: 10px;
}

.Form-group1 {
	margin-top: -10px;
	margin-right: 55%;
	margin-bottom: 20px;
}

.recuperar {
	margin-top: 12px;
	height: 100px;
	padding: 10px;
	background-color: #007bff;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

.h2 {
	font-family: Timew new roman;
}

.btn-custom {
	width: 100%;
	margin: 0 0;
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
	/* Efecto de "levantar" al pasar el mouse */
	box-shadow: 0 8px 20px rgba(0, 123, 255, 0.6);
	background-image: linear-gradient(45deg, #0056b3, #007bff);
}

.form {
	margin: 20px auto;
}

input {
	height: 40px;
}
</style>
</head>
<body>



	<div class="login-container">
		<h2 class="h2">Inicio de Sesión</h2>

		<form action="LoginServlet" method="POST">
			<div class="form-group">
				<label for="username">Usuario:</label> <input class="form-control"
					type="text" id="usuario" name="usuario" required>
			</div>
			<div class="form-group">
				<label for="password">Contraseña:</label> <input
					class="form-control" type="password" id="contrasena"
					name="contrasena" required>
			</div>
			<div class="Form-group1">
				<a class="Recuperar" href="RecuperarContrasena.jsp">Recuperar
					contraseña</a>
			</div>
			<input type="submit" class="btn btn-custom" value="Ingresar">

			<div class="form">
				<a href="Registro.jsp" class="btn btn-custom">Registro</a>
			</div>
		</form>

		<%
		// Obtiene el mensaje de error del servlet si existe
		String message = (String) request.getAttribute("message");
		if (message != null && !message.isEmpty()) {
		%>
		<p class="error"><%=message%></p>
		<%
		}
		%>
	</div>

</body>
</html>