package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/ActualizarContrasenaServlet")
public class ActualizarContrasenaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String token = request.getParameter("token");
        String nuevaContrasena = request.getParameter("nueva_contrasena");
        String confirmarContrasena = request.getParameter("confirmar_contrasena");

        // 1. Validar que las contraseñas coincidan
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            request.setAttribute("mensaje", "Las contraseñas no coinciden. Por favor, verifica.");
            request.getRequestDispatcher("cambiarContrasena.jsp").forward(request, response);
            return;
        }
        
        // 2. Aquí podrías agregar lógica para validar la fortaleza de la contraseña
        // Por ejemplo, longitud mínima o caracteres especiales.

        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // 3. Conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);

            // 4. Actualizar la contraseña y limpiar el token.
            String sql = "UPDATE tblusuarios SET contraseña = ?, token_recuperacion = NULL, fecha_expiracion_token = NULL WHERE correo = ? AND token_recuperacion = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nuevaContrasena);
            stmt.setString(2, correo);
            stmt.setString(3, token);
            int filasActualizadas = stmt.executeUpdate();

            if (filasActualizadas > 0) {
                // Si la actualización fue exitosa, reenviar con un mensaje de éxito
                request.setAttribute("mensaje", "¡Tu contraseña ha sido actualizada exitosamente!");
            } else {
                // Si la actualización falló (token inválido o ya usado), reenviar con un mensaje de error
                request.setAttribute("mensaje", "El enlace de recuperación no es válido o ya fue utilizado.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error en el servidor. Por favor, inténtalo más tarde.");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 5. Reenviar al mismo formulario para mostrar el resultado
        request.getRequestDispatcher("RecuperarContraseña.jsp").forward(request, response);
    }
}