package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Servlet implementation class DescargarExcelServlet
 */
@WebServlet("/DescargarExcelServlet")
public class DescargarExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DescargarExcelServlet() {
        super();
    }

    /*En este servlet llamado (DescargarExcelServlet) sirve para generar un reporte de la base de datos de la tabla personas
     * En el metodo doget se le indica que será un documento de excel y se le agrega un nombre sl docuemnto en la linea 38
     * Se crea objetos de las clases PrintWriter, Connection, Statement, ResultSet  para escribir texto en el documento, la conexio
     * de la base de datos y ejecutar las consultas a la base de datos.
     * Dentro de este metodo se crea un try-catch se hace la carga del driver y se defie los parametros de conexion, despues 
     * de esto se crea un statement y se ejcuta la consulta. Dentro de un while se rellena los campos deacuerdo con sus variables declaradas
     * cualquiera error se captura cualquier error se captura en el catch y se cierra el ResultSet, ResultSet, Connection y Statement
     * 
     * */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "filename=sumarsonumero.xls");
	
	
		PrintWriter out = response.getWriter();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		
		
		 try {
		        // 1. Cargar el driver de la base de datos (ejemplo para MySQL)
		        Class.forName("com.mysql.cj.jdbc.Driver");

		        // 2. Establecer la conexión a la base de datos
		        String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas";
		        String user = "root";
		        String password = "123456";
		        conn = DriverManager.getConnection(url, user, password);

		        // 3. Crear el statement y ejecutar la consulta
		        stmt = conn.createStatement();
		        String sql = "SELECT id_persona, nombre, apellido, edad, fecha_creacion FROM personas"; // Tu consulta SELECT
		        rs = stmt.executeQuery(sql);

		        // 4. Escribir las cabeceras en el archivo Excel
		        out.println("ID\tNombre\tApellido\tEdad\tfecha_creacion");

		        // 5. Iterar sobre el resultado y escribir los datos en el archivo
		        while (rs.next()) {
		            int id_persona = rs.getInt("id_persona");
		            String nombre = rs.getString("nombre");
		            String apellido = rs.getString("apellido");
		            String edad = rs.getString("edad");
		            String fecha_creacion = rs.getString("fecha_creacion");

		            // Escribe los datos en una línea, separados por tabuladores (\t)
		            out.println(id_persona + "\t" + nombre + "\t" + apellido + "\t" + edad + "\t" + fecha_creacion);
		        }

		    } catch (ClassNotFoundException | SQLException e) {
		        // Manejo de errores: imprime la traza para depuración
		        e.printStackTrace();
		        out.println("Error al generar el reporte: " + e.getMessage());
		    } finally {
		        // 6. Cerrar los recursos en el bloque finally para asegurar que se liberen
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) conn.close();
		            if (out != null) out.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
