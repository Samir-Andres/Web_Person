package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.Connection;
public class DbUtil {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");
                
                if (inputStream == null) {
                    throw new IOException("El archivo db.properties no se encontró en el classpath.");
                }
                
                prop.load(inputStream);
                
                // La propiedad del driver no es necesaria si usas JDBC 4.0 o superior
                String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas";
                String user = "root";
                String password = "123456";
                
                connection = DriverManager.getConnection(url, user, password);
                
            } catch (SQLException e) {
                System.err.println("Error de conexión a la base de datos: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error al cargar el archivo de propiedades: " + e.getMessage());
                e.printStackTrace();
            }
            return connection;
        }
    }
}
	

