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

        <button class="btn btn-primary" type="submit">Enviar</button>
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