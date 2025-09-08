<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Panel de Administración</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
/* Estilos generales para el panel de control */
body {
    background-color: #f8f9fa;
    font-family: 'Segoe UI', Arial, sans-serif;
}
.sidebar {
    background-color: #2c3e50;
    color: #ecf0f1;
    height: 100vh;
    padding-top: 20px;
}
.sidebar .nav-link {
    color: #ecf0f1;
    font-size: 1.1rem;
    padding: 15px;
}
.sidebar .nav-link:hover {
    background-color: #34495e;
}
.main-content {
    padding: 30px;
}
.card {
    border-radius: 10px;
    border: none;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
}
.header-actions {
    display: flex;
    justify-content: flex-end;
    align-items: center;
}
.form-inline .form-control {
    border-radius: 20px;
}
/* Estilos de la notificación flotante */
.notificacion-flotante {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 1050;
    width: 350px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
</style>
</head>
<body>

<div class="d-flex" id="wrapper">
      
    <div id="page-content-wrapper" class="w-100">
        <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom p-3">
            <button class="btn btn-primary" id="menu-toggle"><i class="fas fa-bars"></i></button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto mt-2 mt-lg-0">
                    
                </ul>
            </div>
        </nav>

        <div class="container-fluid main-content">
            <h1 class="mt-4 mb-4">Gestión de Personas</h1>

            <c:if test="${notificacion != null}">
                <c:set var="alertClass" value="${notificacion.startsWith('✅') ? 'alert-success' : 'alert-danger'}" />
                <div class="alert ${alertClass} alert-dismissible fade show notificacion-flotante" role="alert">
                    <c:out value="${notificacion}" />
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="row">
                <div class="col-md-4 mb-4">
                    <div class="card p-3">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="text-muted">Total de Personas</h5>
                                <h2 class="mt-2 text-primary">${totalRegistros}</h2> 
                            </div>
                            <i class="fas fa-users fa-3x text-primary"></i>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="card p-3">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="text-muted">Nuevos Registros (Hoy)</h5>
                                <h2 class="mt-2 text-success">${nuevosRegistrosHoy}</h2> 
                            </div>
                            <i class="fas fa-user-plus fa-3x text-success"></i>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="card p-3">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="text-muted">Edad Promedio</h5>
                                <h2 class="mt-2 text-warning">--</h2> 
                            </div>
                            <i class="fas fa-chart-pie fa-3x text-warning"></i>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card p-4">
                <h3 class="mb-4">Listado de Personas</h3>
                <div class="mb-4 d-flex justify-content-between align-items-center flex-wrap">
                     
                    <form action="PersonasServlet" method="get" class="d-flex">
                     <a href="#" class="btn btn-outline-info"><i class="fas fa-file-pdf me-2"></i>PDF</a>
                    </form>
                </div>
                
                <div class="table-responsive">
                    <table class="table table-hover table-striped">
                        <thead class="bg-primary text-white">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Edad</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${personas}" var="persona">
                                <tr>
                                    <td><c:out value="${persona.id}" /></td>
                                    <td><c:out value="${persona.nombre}" /></td>
                                    <td><c:out value="${persona.apellido}" /></td>
                                    <td><c:out value="${persona.edad}" /></td>
                                    <td class="text-center">
                                        <a href="PersonasServlet?action=edit&personaId=${persona.id}" class="btn btn-sm btn-warning me-1" title="Actualizar"><i class="fas fa-edit"></i></a>
                                        <a href="PersonasServlet?action=view&personaId=${persona.id}" class="btn btn-sm btn-info me-1" title="Ver"><i class="fas fa-eye"></i></a>
                                        <a href="PersonasServlet?action=delete&personaId=${persona.id}" class="btn btn-sm btn-danger" onclick="return confirm('¿Estás seguro de que quieres eliminar este registro?');" title="Eliminar"><i class="fas fa-trash-alt"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            
            </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    var el = document.getElementById("wrapper");
    var toggleButton = document.getElementById("menu-toggle");

    if (toggleButton) {
        toggleButton.onclick = function () {
            el.classList.toggle("toggled");
        };
    }
</script>

</body>
</html>