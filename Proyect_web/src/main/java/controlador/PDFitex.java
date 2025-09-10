package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PDFitex
 */
@WebServlet("/PDFitex")
public class PDFitex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PDFitex() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    /* En este servlet (pdfitex) se utiliza el metodo  doget pra genera un reporte pdf de usuarios de la tabla tblusarios de la base de datos.
     * En este servlet de le establece la repuesta que en este caso es un pdf, se configuara los encabezados y se le asigna el nombre.
     * Tambien de crea un objetos de la clase documento de la libreria y un objeto de Connetion despues de esto se crea un bloque try-catch para gestionar los errores
     * sea de la conexion a la base de datos o del document.
     * dentro del bloque try-catch se configura el escrito de pdf y se abre el documento, tambien se carga el driver y se declara la ruta de de la base de datos con 
     * su credenciales, se hace un if con la condicion para verificar la conexion, se agrega el titulo del pdf y se ejecuta la consulta (Script) agregando cada uno de los 
     * atributos al reporte del pdf segun la variable declara dentro del while, despues de esto de cierra la consulta de la base de datos y el documento y si hubo errores 
     * en la verificacion de la base de datos sale por el else  
     * 
     * 
     * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// Establece el tipo de contenido de la respuesta a PDF.
		response.setContentType("application/pdf");
		
		// Configura el encabezado para que el navegador muestre el PDF en línea y le asigne un nombre de archivo.
		response.setHeader("Content-Disposition", "inline; filename=\"Reporte_no:.pdf\"");
		
		// Crea una instancia del objeto Document de iText.
		Document document  = new Document();
		Connection connection = null;

		try {
			
			// Configura el escritor de PDF para que escriba en el flujo de salida de la respuesta del servlet.
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			// Carga el driver de la base de datos MySQL.
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establece una conexión a la base de datos
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_tiendamascotas", "root", "123456");
			
			if (connection != null) { // Verifica si la conexión fue exitosa.
				
				// Agrega un título y una línea separadora al documento.
				document.add(new Paragraph("Reporte de personas"));
				document.add(new Paragraph("-----------------------------------"));

				//Crear el Statement y ejecutar la consulta
				Statement stmt = connection.createStatement();
				
				// Ejecuta la consulta y obtiene un conjunto de resultados
				ResultSet rs = stmt.executeQuery("SELECT id_usuario, usuario, contrasena, tipo_usuario, rol FROM tblusuarios");

				//Leer los resultados y agregarlos al PDF
				while (rs.next()) {
					String id = rs.getString("id_usuario");
					String usuario = rs.getString("usuario");
					String contrasena = rs.getString("contrasena");
					String tipo_usuario = rs.getString("tipo_usuario");
					String rol = rs.getString("rol");
				
					
					// Agregar cada fila de datos como un nuevo párrafo en el documento
					document.add(new Paragraph("ID: " + id + ", Usuario: " + usuario + ", Contrasena: " + contrasena +  " Tipo de usuario " + tipo_usuario + " rol: " + rol));
				}
				
				//Cerrar el ResultSet y el Statement y cierra la consulta sql
				rs.close();
				stmt.close();

			} else {
				document.add(new Paragraph("Fallo al conectar a la base de datos."));
			}

		} catch (Exception e) {
			e.printStackTrace();
			// Manejar cualquier excepción y agregar un mensaje de error al PDF
			if (document.isOpen()) {
				try {
					document.add(new Paragraph("Error: " + e.getMessage()));
				} catch (Exception docEx) {
					docEx.printStackTrace();
				}
			}
		} finally {
			//Cerrar el documento y la conexión en el bloque finally
			if (document.isOpen()) {
				document.close();
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
