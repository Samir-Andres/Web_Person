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
 * Servlet encargado de gestionar el inicio de sesión de los usuarios.
 * Verifica en la base de datos si las credenciales (usuario y contraseña)
 * coinciden con un registro existente en la tabla "tblusuarios".
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Parámetros de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bd_tiendamascotas"; // URL y BD
    private static final String DB_USER = "root";                                          // Usuario MySQL
    private static final String DB_PASSWORD = "123456";                                    // Contraseña MySQL

    /**
     * Constructor del servlet.
     */
    public LoginServlet() {
        super();
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	// Captura de los parámetros enviados desde el formulario de login
        String username = request.getParameter("usuario");    // Usuario ingresado
        String password = request.getParameter("contrasena"); // Contraseña ingresada
        
        // Mensaje por defecto en caso de error
        String errorMessage = "Usuario o contraseña incorrectos.";
        boolean isValidUser = false; // Indicador de validez del usuario
        
        try {
        	
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //Establecer la conexión con la base de datos
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
                
                // Preparar la consulta SQL para validar usuario y contraseña
                //    Uso de PreparedStatement para prevenir inyección SQL
                String sql = "SELECT * FROM tblusuarios WHERE usuario = ? AND contrasena = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, username); // Sustituye el primer parámetro
                    statement.setString(2, password); // Sustituye el segundo parámetro
                    
                    // Ejecutar la consulta
                    try (ResultSet result = statement.executeQuery()) {
                        if (result.next()) {
                            // Si existe un resultado, las credenciales son correctas
                            isValidUser = true;
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            // Error si no se encuentra el driver JDBC
            errorMessage = "Error: Driver JDBC no encontrado.";
            e.printStackTrace();

        } catch (SQLException e) {
            // Error de conexión o consulta a la base de datos
            errorMessage = "Error de conexión con la base de datos.";
            e.printStackTrace();
        }

        // 5. Validar resultado de la autenticación
        if (isValidUser) {
            //Credenciales válidas: reenviar al servlet PersonasServlet con acción "list"
            RequestDispatcher dispatcher = request.getRequestDispatcher("PersonasServlet?action=list");
            dispatcher.forward(request, response);
        } else {
            // Credenciales inválidas: reenviar a Login.jsp con un mensaje de error
            request.setAttribute("message", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
