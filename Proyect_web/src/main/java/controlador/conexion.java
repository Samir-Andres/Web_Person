package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

//En esta clase se establece y retorna una conexión a la base de datos MySQL.
//Este método (conectarBD) gestiona el proceso de conexión, incluyendo la 
//carga del driver,la definición de los parámetros de la base de datos 
//URL, usuario, contraseña) y el manejo de posibles errores que captura exepcion 
//si no se encuentra el driver o los parametros de la base de datos .
    public Connection conectarBD() {
        
        // Objeto que almacenará la conexión
        Connection conexion = null;

        
        String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas"; // URL de la BD (puerto y nombre)
        String usuario = "root";                                     // Usuario de MySQL
        String contraseña = "123456";                                // Contraseña de MySQL
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos.");

        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver JDBC: " + e.getMessage());

        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        return conexion;
    }
}
