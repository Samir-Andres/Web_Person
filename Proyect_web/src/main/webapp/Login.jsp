<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
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
            width: 300px;
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
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .error {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>



<div class="login-container">
    <h2>Inicio de Sesión</h2>

    <form  action="LoginServlet" method="POST">
        <div class="form-group">
            <label for="username">Usuario:</label>
            <input type="text" id="usuario" name="usuario" required>
        </div>
        <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="password" id="contrasena" name="contrasena" required>
        </div>
        <input type="submit" value="Ingresar">
    </form>
    
    <%
        // Obtiene el mensaje de error del servlet si existe
        String message = (String) request.getAttribute("message");
        if (message != null && !message.isEmpty()) {
    %>
        <p class="error"><%= message %></p>
    <%
        }
    %>
</div>

</body>
</html>