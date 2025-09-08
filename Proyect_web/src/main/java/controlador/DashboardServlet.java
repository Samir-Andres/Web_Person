package controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Personas;

import java.io.IOException;
import java.util.List;

import DAO.PersonaDaos;

/**
 * Servlet encargado de manejar la lógica del panel de control (Dashboard).
 * Obtiene datos resumidos de la base de datos para mostrarlos en el JSP.
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PersonaDaos dao; // Updated class name
    // Vista JSP para el panel de control
    private static final String DASHBOARD_JSP = "/dashboard.jsp";

    public DashboardServlet() {
        super();
        dao = new PersonaDaos(); // Initializes the DAO to interact with the DB
    }

    /**
     * Handles GET requests to load the dashboard page.
     * @param request The HTTP request object
     * @param response The HTTP response object
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            // 1. Get the number of records created today from the DAO
            int nuevosRegistrosHoy = dao.countPersonasCreatedToday();
            // Pass the value to the request so the JSP can access it
            request.setAttribute("nuevosRegistrosHoy", nuevosRegistrosHoy);

            // 2. Get the total number of people in the database
            int totalRegistros = dao.countTotalPersonas();
            // Pass the value to the request
            request.setAttribute("totalRegistros", totalRegistros);

            // 3. Get the complete list of people for the dashboard table
            List<Personas> listPersonas = dao.selectAllPersonas();
            request.setAttribute("personas", listPersonas);

            // 4. Set a notification message if one exists in the session
            String notificacion = (String) request.getSession().getAttribute("notificacion");
            if (notificacion != null) {
                request.setAttribute("notificacion", notificacion);
                // Remove the message from the session so it's not shown again
                request.getSession().removeAttribute("notificacion");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar los datos del panel: " + e.getMessage());
            // In case of an error, set a notification message
            request.getSession().setAttribute("notificacion", "❌ Error al cargar los datos del panel: " + e.getMessage());
        }

        // Redirect to the dashboard view
        RequestDispatcher dispatcher = request.getRequestDispatcher(DASHBOARD_JSP);
        dispatcher.forward(request, response);
    }
}