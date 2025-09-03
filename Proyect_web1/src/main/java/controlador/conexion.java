package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

public Connection conectarBD() {
		
	Connection conexion = null;
	  String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas";
	  String usuario = "root";
	  String contraseña = "123456";

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