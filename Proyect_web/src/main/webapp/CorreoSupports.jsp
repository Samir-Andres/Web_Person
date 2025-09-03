<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enviar Correo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f1f1;
            margin: 0;
            padding: 40px;
        }
        .form-container {
            background-color: #fff;
            max-width: 500px;
            margin: auto;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        h2 {
            text-align: center;
        }
        input, textarea {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            width: 100%;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .status {
            margin-top: 20px;
            font-weight: bold;
            text-align: center;
        }
        
         .btn-custom {
         margin: 10px auto;
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
    
     .btn-warning:hover {
        transform: translateY(-5px); /* Efecto de "levantar" al pasar el mouse */
        box-shadow: 0 0 0 1px rgba(255, 193, 7, 0.5);
     
    }
    
     .btn-warning {
      margin: 5px auto;
        width: 100%;
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

<div class="form-container">
    <h2> </h2>

    <form method="post" action="${pageContext.request.contextPath}/EmailSupports">
        <label for="correo">Correo destino:</label>
        <input class="form-control" type="email" id="correo" name="correo" required>

        <label for="asunto">Asunto:</label>
        <input class="form-control"  type="text" id="asunto" name="asunto" required>

        <label for="mensaje">Mensaje:</label>
        <textarea class="form-control"  id="mensaje" name="mensaje" rows="6" required></textarea>

        <button class="btn-custom" type="submit">Enviar</button>
         <a href="PersonasServlet" class="btn btn-warning" >Volver</a>
    
    </form>

    <%
        String status = request.getParameter("status");
        if ("ok".equals(status)) {
    %>
        <div class="status" style="color: blue;">Correo enviado correctamente👍</div>
    <%
        } else if ("error".equals(status)) {
    %>
        <div class="status" style="color: red;">Hubo un error al enviar el correo.</div>
    <%
        }
    %>
</div>

</body>
</html>