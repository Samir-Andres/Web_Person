package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import controlador.conexion;
import modelo.Personas;

/**
 * Clase DAO (Data Access Object) para gestionar la entidad Personas.
 * Se encarga de realizar operaciones CRUD en la base de datos
 * y además enviar notificaciones por correo electrónico.
 */
public class PersonaDao {

    // Conexión a la base de datos
    private Connection connection;

    /**
     * Constructor que inicializa la conexión a la base de datos
     * utilizando la clase 'conexion'.
     */
    public PersonaDao() {
        conexion con = new conexion();
        this.connection = con.conectarBD();
    }

    /**
     * Método para insertar una persona en la base de datos.
     * Tras una inserción exitosa, envía una notificación por correo.
     * @param persona objeto Personas con los datos a insertar
     */
    public void addPersona(Personas persona) {
        try {
        	  // Prepara la sentencia SQL para una inserción segura.
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO personas(nombre, apellido, edad) VALUES (?, ?, ?)");
            
         // Asigna los valores de los atributos del objeto `persona` a los parámetros de la sentencia.
            preparedStatement.setString(1, persona.getNombre());
            preparedStatement.setString(2, persona.getApellido());
            preparedStatement.setInt(3, persona.getEdad());
            preparedStatement.executeUpdate();
            
            // Llamar al método de envío de correo después de una inserción exitosa
            sendEmailNotification(persona);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método privado para enviar una notificación por correo electrónico
     * cuando se registra una nueva persona.
     * @param persona objeto Personas recién insertado
     */
    private void sendEmailNotification(Personas persona) {
    	
    	// Credenciales y configuración del correo
        final String REMITENTE = "samirandres296@gmail.com"; // Correo remitente
        final String CONTRASENA = "vwbm iiml fcmo tgnz";     // Contraseña de aplicación
        final String DESTINATARIO = "samirfonse21@gmail.com"; // Correo destinatario
        
        // Configuración de propiedades para SMTP (servidor de correo)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        // Crear sesión de correo con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            	// Devuelve un objeto con las credenciales del remitente.
                return new PasswordAuthentication(REMITENTE, CONTRASENA);
            }
        });
        
        try {
            // Crear mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE));
            
            // Establece el destinatario del correo.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(DESTINATARIO));

            // Configura el asunto del correo.
            message.setSubject("Nueva Persona Registrada");
            String mensaje = String.format("Se ha insertado una nueva persona:\n\n"
                    + "Nombre: %s\n"
                    + "Apellido: %s\n"
                    + "Edad: %d",
                    persona.getNombre(), persona.getApellido(), persona.getEdad());
            message.setText(mensaje);

            // Envía el mensaje de correo.
            Transport.send(message);
            System.out.println("Notificación de inserción de persona enviada.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar la notificación por correo.");
        }
  
    }

    /**
     * Método para eliminar una persona de la base de datos según su ID.
     * @param personaId identificador de la persona a eliminar
     */
    public void deletePersona(int personaId) {
        try {
        	// Prepara la sentencia SQL para eliminar el registro.
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM personas WHERE id_persona=?");
            // Asigna el ID al parámetro de la sentencia.
            
            preparedStatement.setInt(1, personaId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para actualizar los datos de una persona existente.
     * @param persona objeto Personas con la información actualizada
     */
    public void updatePersona(Personas persona) {
        try {
        	PreparedStatement preparedStatement = connection.prepareStatement("UPDATE personas SET nombre=?, apellido=?, edad=? WHERE id_persona=?");
            preparedStatement.setString(1, persona.getNombre());
            preparedStatement.setString(2, persona.getApellido());
            preparedStatement.setInt(3, persona.getEdad());
            preparedStatement.setInt(4, persona.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para obtener todas las personas de la base de datos.
     * @return lista con todos los objetos Personas
     */
    public List<Personas> getAllPersonas() {
        List<Personas> personas = new ArrayList<Personas>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM personas");
            while (rs.next()) {
                Personas persona = new Personas();
                persona.setId(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setEdad(rs.getInt("edad"));
                personas.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    /**
     * Método para obtener una persona específica por su ID.
     * @param personaId identificador de la persona
     * @return objeto Personas con los datos obtenidos
     */
    public Personas getPersonaById(int personaId) {
        Personas persona = new Personas();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM Personas WHERE id_persona=?");
            preparedStatement.setInt(1, personaId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                persona.setId(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }

    /**
     * Método para filtrar y ordenar la lista de personas según criterios.
     * @param searchTerm término de búsqueda (para el nombre)
     * @param sortBy campo por el cual ordenar (id_persona, nombre, apellido, edad)
     * @return lista de Personas filtradas y ordenadas
     */
    public List<Personas> selectFilteredAndSortedPersonas(String searchTerm, String sortBy) {
        List<Personas> personas = new ArrayList<>();
        String sql = "SELECT * FROM personas WHERE 1=1"; // Cláusula base

        // Añadir condición de búsqueda si se proporciona un término de búsqueda
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql += " AND nombre LIKE ?";
        }

        // Añadir condición de ordenamiento si se proporciona una columna válida
        if (sortBy != null && (sortBy.equals("id_persona") || sortBy.equals("nombre") || sortBy.equals("apellido") || sortBy.equals("edad"))) {
            sql += " ORDER BY " + sortBy;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                preparedStatement.setString(parameterIndex++, "%" + searchTerm + "%");
            }

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Personas persona = new Personas();
                persona.setId(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setEdad(rs.getInt("edad"));
                personas.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }
}
