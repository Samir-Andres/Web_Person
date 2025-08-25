package controlador;

import jakarta.servlet.RequestDispatcher;
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


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Credenciales de la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bd_tiendamascotas";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("usuario");
        String password = request.getParameter("contrasena");
        
        String errorMessage = "Usuario o contraseña incorrectos.";
        boolean isValidUser = false;
        
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión con la base de datos
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
                
                // Usar PreparedStatement para evitar inyección SQL
                String sql = "SELECT * FROM tblusuarios WHERE usuario = ? AND contrasena = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    
                    // Ejecutar la consulta
                    try (ResultSet result = statement.executeQuery()) {
                        if (result.next()) {
                            // Si se encuentra un resultado, las credenciales son válidas
                            isValidUser = true;
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            errorMessage = "Error: Driver JDBC no encontrado.";
            e.printStackTrace();
        } catch (SQLException e) {
            errorMessage = "Error de conexión con la base de datos.";
            e.printStackTrace();
        }

        if (isValidUser) {
            // Autenticación exitosa, reenviar la solicitud al PersonasServlet para que cargue la lista
            RequestDispatcher dispatcher = request.getRequestDispatcher("PersonasServlet?action=list");
            dispatcher.forward(request, response);
        } else {
            // Credenciales incorrectas, reenviar al formulario de login con un mensaje de error
            request.setAttribute("message", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
        }
    }
}