package controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Personas;
import DAO.PersonaDao;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;

/**
 * Servlet encargado de manejar las operaciones CRUD sobre Personas.
 * Controla las vistas (insertar, editar, listar, ver y eliminar) 
 * interactuando con el DAO y JSP correspondientes.
 */
@WebServlet("/PersonasServlet")
public class PersonasServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Vistas JSP para cada acción
    private static final String INSERT_OR_EDIT = "/index.jsp";      // Formulario de inserción/edición
    private static final String LIST_PERSONA = "/listPersona.jsp";  // Listado principal
    private static final String VIEW_PERSONA = "/VerPersonas.jsp";  // Vista de detalles

    private PersonaDao dao; // DAO para acceder a la base de datos

    /**
     * Constructor: inicializa el DAO de Persona.
     */
    public PersonasServlet() {
        super();
        dao = new PersonaDao();
    }
  
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	String forward = ""; // JSP al que se redirigirá
        String action = request.getParameter("action"); // Acción recibida desde la URL
        
        try {
            if (action != null && action.equalsIgnoreCase("view")) { 
                // Acción: ver persona
                forward = VIEW_PERSONA;
                String personaIdParam = request.getParameter("personaId");
                if (personaIdParam != null && !personaIdParam.isEmpty()) {
                    int personaId = Integer.parseInt(personaIdParam);
                    Personas persona = dao.getPersonaById(personaId);
                    request.setAttribute("persona", persona);
                }

            } else if (action != null && action.equalsIgnoreCase("edit")) {
                // Acción: editar persona
                forward = INSERT_OR_EDIT;
                String personaIdParam = request.getParameter("personaId");
                if (personaIdParam != null && !personaIdParam.isEmpty()) {
                    int personaId = Integer.parseInt(personaIdParam);
                    Personas persona = dao.getPersonaById(personaId);
                    request.setAttribute("persona", persona);
                }

            } else if (action != null && action.equalsIgnoreCase("delete")) {
                // Acción: eliminar persona
                String personaIdParam = request.getParameter("personaId");
                if (personaIdParam != null && !personaIdParam.isEmpty()) {
                    int personaId = Integer.parseInt(personaIdParam);
                    dao.deletePersona(personaId);
                }
                
                // Después de eliminar, volver al listado
                forward = LIST_PERSONA;
                listPersonas(request, response);
                return; 

            } else if (action != null && action.equalsIgnoreCase("insert")) {
                // Acción: insertar nueva persona
                forward = INSERT_OR_EDIT;

            } else {
                // Acción por defecto: listar (con búsqueda y ordenamiento si aplica)
                forward = LIST_PERSONA;
                listPersonas(request, response);
                return; 
            }
        } catch (NumberFormatException | SQLException e) {
            // Captura de errores (id mal formado o problemas en BD)
            e.printStackTrace();
            System.out.println("Error en el servlet: " + e.getMessage());
        }

        // Redirección a la vista correspondiente
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    /**
     * Método auxiliar para listar personas con filtros y ordenamiento.
     */
    private void listPersonas(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException, SQLException {
        
        // Parámetros de búsqueda y ordenamiento
        String searchTerm = request.getParameter("search");
        String sortBy = request.getParameter("sort");

        // Obtiene la lista de personas desde el DAO
        List<Personas> listPersonas = dao.selectFilteredAndSortedPersonas(searchTerm, sortBy);
        
        // Envía los datos a la vista
        request.setAttribute("personas", listPersonas);
        RequestDispatcher dispatcher = request.getRequestDispatcher(LIST_PERSONA);
        dispatcher.forward(request, response);
    }
    
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
    	// Construcción del objeto Persona a partir del formulario
        Personas persona = new Personas();
        persona.setNombre(request.getParameter("nombre"));
        persona.setApellido(request.getParameter("apellido"));
        
        // Manejo de la edad con validación
        String edadStr = request.getParameter("edad");
        if (edadStr != null && !edadStr.isEmpty()) {
            try {
                persona.setEdad(Integer.parseInt(edadStr));
            } catch (NumberFormatException e) {
                System.out.println("Error: La edad no es un número válido.");
            }
        }

        // Validar si es inserción o actualización
        String personaId = request.getParameter("personaId");
        if (personaId == null || personaId.isEmpty()) {
            // Inserción
            dao.addPersona(persona);
        } else {
            // Actualización
            persona.setId(Integer.parseInt(personaId));
            dao.updatePersona(persona);
        }
        
        // Redireccionar al listado después de la operación
        response.sendRedirect("PersonasServlet?action=list");
    }
}
