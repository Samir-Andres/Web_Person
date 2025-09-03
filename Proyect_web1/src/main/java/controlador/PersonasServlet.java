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

@WebServlet("/PersonasServlet")
public class PersonasServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String INSERT_OR_EDIT = "/index.jsp"; // Asumiendo que este es tu formulario de inserción/edición
    private static final String LIST_PERSONA = "/listPersona.jsp"; // Asumiendo que este es tu listado principal
    private static final String VIEW_PERSONA = "/VerPersonas.jsp";
    
    private PersonaDao dao;

    public PersonasServlet() {
        super();
        dao = new PersonaDao();
    }
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        
        try {
            if (action != null && action.equalsIgnoreCase("view")) { 
                forward = VIEW_PERSONA;
                String personaIdParam = request.getParameter("personaId");
                if (personaIdParam != null && !personaIdParam.isEmpty()) {
                    int personaId = Integer.parseInt(personaIdParam);
                    Personas persona = dao.getPersonaById(personaId);
                    request.setAttribute("persona", persona);
                }
            } else if (action != null && action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                String personaIdParam = request.getParameter("personaId");
                if (personaIdParam != null && !personaIdParam.isEmpty()) {
                    int personaId = Integer.parseInt(personaIdParam);
                    Personas persona = dao.getPersonaById(personaId);
                    request.setAttribute("persona", persona);
                }
            } else if (action != null && action.equalsIgnoreCase("delete")) {
                String personaIdParam = request.getParameter("personaId");
                if (personaIdParam != null && !personaIdParam.isEmpty()) {
                    int personaId = Integer.parseInt(personaIdParam);
                    dao.deletePersona(personaId);
                }
                forward = LIST_PERSONA;
                listPersonas(request, response);
                return; // Importante para evitar que se ejecute la lógica por defecto
            } else if (action != null && action.equalsIgnoreCase("insert")) {
                forward = INSERT_OR_EDIT;
            } else {
                // Lógica de listado con filtro y ordenamiento (por defecto o acción "list")
                forward = LIST_PERSONA;
                listPersonas(request, response);
                return; // Importante para evitar que se ejecute la lógica por defecto
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error en el servlet: " + e.getMessage());
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void listPersonas(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException, SQLException {
        
        String searchTerm = request.getParameter("search");
        String sortBy = request.getParameter("sort");

        List<Personas> listPersonas = dao.selectFilteredAndSortedPersonas(searchTerm, sortBy);
        
        request.setAttribute("personas", listPersonas);
        RequestDispatcher dispatcher = request.getRequestDispatcher(LIST_PERSONA);
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Personas persona = new Personas();
        persona.setNombre(request.getParameter("nombre"));
        persona.setApellido(request.getParameter("apellido"));
        
        // Manejo de la edad para evitar NumberFormatException
        String edadStr = request.getParameter("edad");
        if (edadStr != null && !edadStr.isEmpty()) {
            try {
                persona.setEdad(Integer.parseInt(edadStr));
            } catch (NumberFormatException e) {
                // Podrías manejar el error aquí, por ejemplo, enviando un mensaje al usuario
                System.out.println("Error: La edad no es un número válido.");
            }
        }

        String personaId = request.getParameter("personaId");
        
        if (personaId == null || personaId.isEmpty()) {
            dao.addPersona(persona);
        } else {
            persona.setId(Integer.parseInt(personaId));
            dao.updatePersona(persona);
        }
        
        // Redireccionar al listado después de la operación
        response.sendRedirect("PersonasServlet?action=list");
    }
}