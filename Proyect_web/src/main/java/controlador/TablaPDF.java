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

// Importaciones CORRECTAS de iText
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
@WebServlet("/TablaPDF")
public class TablaPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TablaPDF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"Reporte_no.pdf\"");

		Document document = new Document();
		Connection connection = null;

		try {
			// Configurar el escritor de PDF
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();

			// Estilos de fuente (se usaron las importaciones de iText)
			Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
			Font fontTitle1 = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.DARK_GRAY);
			Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
			Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 10);

			// Título del documento
			Paragraph title = new Paragraph("Reporte de Personas", fontTitle);
			Paragraph title1 = new Paragraph("Datos de la base de datos", fontTitle1);
			title.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(title);
			document.add(Chunk.NEWLINE);

			// Conectar a la base de datos
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_tiendamascotas", "root", "123456");

			if (connection != null) {
				// Crear la tabla con 4 columnas
				PdfPTable table = new PdfPTable(4);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);

				// Añadir las cabeceras de la tabla
				String[] headers = {"ID", "Nombre", "Apellido", "Edad"};
				for (String header : headers) {
					PdfPCell cell = new PdfPCell(new Paragraph(header, fontHeader));
					cell.setBackgroundColor(BaseColor.DARK_GRAY);
					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setPadding(5);
					table.addCell(cell);
				}

				// Llenar la tabla con datos de la base de datos
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id_persona, nombre, apellido, edad FROM personas");

				while (rs.next()) {
					table.addCell(new Paragraph(rs.getString("id_persona"), fontData));
					table.addCell(new Paragraph(rs.getString("nombre"), fontData));
					table.addCell(new Paragraph(rs.getString("apellido"), fontData));
					table.addCell(new Paragraph(String.valueOf(rs.getInt("edad")), fontData));
				}

				document.add(table);

				rs.close();
				stmt.close();

			} else {
				document.add(new Paragraph("Fallo al conectar a la base de datos."));
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (document.isOpen()) {
				try {
					document.add(new Paragraph("Error: " + e.getMessage()));
				} catch (Exception docEx) {
					docEx.printStackTrace();
				}
			}
		} finally {
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
