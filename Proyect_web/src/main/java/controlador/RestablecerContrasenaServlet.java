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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/RestablecerContrasenaServlet")
public class RestablecerContrasenaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT correo FROM tblusuarios WHERE token_recuperacion = ? AND fecha_expiracion_token > ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            
            rs = stmt.executeQuery();

            if (rs.next()) {
                String correoUsuario = rs.getString("correo");
                request.setAttribute("correo", correoUsuario);
                request.setAttribute("token", token);
                request.getRequestDispatcher("cambiarContrasena.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "El enlace de recuperación es inválido o ha expirado.");
                request.getRequestDispatcher("RecuperarContrasenas.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Hubo un error. Por favor, inténtalo de nuevo.");
            request.getRequestDispatcher("RecuperarContrasenas.jsp").forward(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}