<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<style type="text/css">
body {
	font-family: Arial, sans-serif;
	background-color: #f1f1f1;
	margin: 0;
	padding: 40px;
}

label {
	font-family: "Times New Roman", Times, serif;
}

.form-container {
	background-color: #fff;
	max-width: 500px;
	margin: 5px auto;
	padding: 30px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
}

input::placeholder {
	font-family: "Times New Roman", Times, serif;
}

.btn-warning:hover {
	transform: translateY(-5px);
	/* Efecto de "levantar" al pasar el mouse */
	box-shadow: 0 0 0 1px rgba(255, 193, 7, 0.5);
}

.btn-warning {
	width: 50%;
	font-family: "Times New Roman", Times, serif;
	margin: 20px 100px;
	font-size: 20px; /* Tamaño de fuente más grande */
	padding: 5px 20px; /* Relleno del botón */
	border-radius: 50px; /* Bordes muy redondeados para un look moderno */
	font-weight: bold;
	transition: all 0.3s ease; /* Transición suave para los efectos */
	border: none;
	color: white;
	box-shadow: 0 4px 15px rgba(0, 123, 255, 0.4);
}

.btn-custom {
	width: 50%;
	margin: 0 100px;
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

h4 {
	text-align: center;
	font-family: "Times New Roman", Times, serif;
	color: blue;
}

p {
	font-family: "Times New Roman", Times, serif;
	text-align: center;
	font-weight: bold;
	font-size: 20px;
}

.form1 {
	margin-top: -10px;
}
</style>



</head>
<body>

	<div class="form-container">
		<div>
			<h4>Recuperar contraseña</h4>
			<p>Por favor ingrese su correo para la recuperación de su
				contraseña</p>
		</div>
		<form class="form-container" action="ContrasenaServlet">

			<div>
				<label for="correo" class="form-label">Correo Electronico</label> <input
					type="text" class="form-control" name="correo" id="correo"
					placeholder="Ingresa tu correo" required="required">
			</div>

			<div>
				<button type="submit" class="btn btn-warning">Recuperar</button>
				<a href="Login.jsp" class="btn btn-custom">Volver</a>

			</div>

			<%
			String mensaje = (String) request.getAttribute("mensaje");
			if (mensaje != null && !mensaje.isEmpty()) {
				// Por ejemplo, si el mensaje de éxito contiene la palabra "enviado"
				// y si el mensaje de error contiene la palabra "no se encuentra"
				String color;
				if (mensaje.contains("enviado") || mensaje.contains("restablecer")) {
					color = "blue";
				} else {
					color = "red";
				}
			%>
			<p style="color: <%=color%>; font-size: 15px;"><%=mensaje%></p>
			<%
			}
			%>




		</form>

	</div>



</body>
</html>