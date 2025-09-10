<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="registro.png" type="imagenes/png">
<meta charset="UTF-8">
<title>Registro</title>

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
	max-width: 700px;
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

h4 {
	text-align: center;
	font-family: "Times New Roman", Times, serif;
}

.btn-custom {
	width: 50%;
	margin: 30px 100px;
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

label input {
	display: inline;
}

label {
	margin: 20px auto;
}
.select{
width: 100%;

}

.form-select::after {
    content: "\25BC"; /* Código para flecha hacia abajo */
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-50%);
    color: #999;
    pointer-events: none; /* Para que no interfiera con el click */
}
</style>
</head>
<body>


	<div class="form-container">
		<div>

			<h4>Registro</h4>

		</div>

		<form class="form-container" action="">

			<div>
				<label for="correo" class="form-label">Usuario</label> <input
					type="text" class="form-control" name="usuario" id="usuario"
					placeholder="Ingresa tu usuario" required="required">
			</div>
			<div>
				<label for="correo" class="form-label">Contraseña</label> <input
					type="text" class="form-control" name="contrasena" id="contrasena"
					placeholder="Ingresa tu contraseña" required="required">
			</div>

			<div>
				<label for="correo" class="form-label">Rol</label> <select class="form-select"
					name="rol" id="rol" required="required">
					<option class="" value="" >-- Selecciona un rol --</option>
					<option value="empleado">Empleado</option>
					<option value="administrador">Administrador</option>
				</select>
			</div>

			<div>
				<label for="correo" class="form-label">Correo Electronico</label> <input
					type="text" class="form-control" name="correo" id="correo"
					placeholder="Ingresa tu correo" required="required">
			</div>
			<div>
				<button type="submit" class="btn btn-custom">Registro</button>
			</div>
		</form>

	</div>
</body>
</html>