package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {


    public Connection conectarBD() {
        
        // Objeto que almacenará la conexión
        Connection conexion = null;

        // Parámetros de conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas"; // URL de la BD (puerto y nombre)
        String usuario = "root";                                     // Usuario de MySQL
        String contraseña = "123456";                                // Contraseña de MySQL

        try {
            // Cargar el driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión con la base de datos
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos.");

        } catch (ClassNotFoundException e) {
            // Se lanza cuando no se encuentra el driver JDBC en el proyecto
            System.err.println("No se encontró el driver JDBC: " + e.getMessage());

        } catch (SQLException e) {
            // Se lanza cuando ocurre un error al intentar conectarse (credenciales/URL incorrectas)
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        // Retorna la conexión (null si falló)
        return conexion;
    }
}
