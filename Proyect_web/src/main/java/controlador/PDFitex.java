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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"Reporte_no:.pdf\"");
		
		// Crea una instancia o objeto  del documento PDF.
		Document document  = new Document();
		Connection connection = null;

		try {
			//Configurar el escritor de PDF
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			//Conectar a la base de datos
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_tiendamascotas", "root", "123456");
			
			if (connection != null) {
				
				// Agrega un título y una línea separadora al documento.
				document.add(new Paragraph("Reporte de personas"));
				document.add(new Paragraph("-----------------------------------"));

				//Crear el Statement y ejecutar la consulta
				Statement stmt = connection.createStatement();
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
				
				//Cerrar el ResultSet y el Statement
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
