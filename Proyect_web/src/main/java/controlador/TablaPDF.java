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

// Importaciones de iText para generar PDF
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet que genera un reporte en PDF de las personas almacenadas en la base de datos.
 * Utiliza la librería iText para crear el documento y consultas SQL para obtener los datos.
 */
@WebServlet("/TablaPDF")
public class TablaPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor del servlet
     */
    public TablaPDF() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Se indica que la respuesta es un PDF
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"Reporte_no.pdf\"");

		// Documento PDF
		Document document = new Document();
		Connection connection = null;

		try {
			// Configurar el escritor de PDF que enviará el documento al navegador
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();

			// Definición de estilos de fuente
			Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
			Font fontTitle1 = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.DARK_GRAY);
			Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
			Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 10);

			// Títulos principales
			Paragraph title = new Paragraph("Reporte de Personas", fontTitle);
			Paragraph title1 = new Paragraph("Datos de la base de datos", fontTitle1);
			title.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(title);
			document.add(Chunk.NEWLINE); // Salto de línea

			// Conectar a la base de datos MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_tiendamascotas", "root", "123456");

			if (connection != null) {
				
				// Crear tabla PDF con 4 columnas
				PdfPTable table = new PdfPTable(4);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);

				// Encabezados de tabla
				String[] headers = {"ID", "Nombre", "Apellido", "Edad"};
				for (String header : headers) {
					PdfPCell cell = new PdfPCell(new Paragraph(header, fontHeader));
					cell.setBackgroundColor(BaseColor.DARK_GRAY);
					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setPadding(5);
					table.addCell(cell);
				}

				// Ejecutar consulta para obtener datos de la tabla "personas"
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id_persona, nombre, apellido, edad FROM personas");

				// Recorrer resultados y añadirlos al PDF
				while (rs.next()) {
					table.addCell(new Paragraph(rs.getString("id_persona"), fontData));
					table.addCell(new Paragraph(rs.getString("nombre"), fontData));
					table.addCell(new Paragraph(rs.getString("apellido"), fontData));
					table.addCell(new Paragraph(String.valueOf(rs.getInt("edad")), fontData));
				}

				// Agregar tabla al documento PDF
				document.add(table);

				// Cerrar recursos
				rs.close();
				stmt.close();

			} else {
				// En caso de fallo en la conexión
				document.add(new Paragraph("Fallo al conectar a la base de datos."));
			}

		} catch (Exception e) {
			// Manejo de errores
			e.printStackTrace();
			if (document.isOpen()) {
				try {
					document.add(new Paragraph("Error: " + e.getMessage()));
				} catch (Exception docEx) {
					docEx.printStackTrace();
				}
			}
		} finally {
			// Cerrar el documento y la conexión
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
	 * Maneja solicitudes POST, redirige a doGet para generar el PDF.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
