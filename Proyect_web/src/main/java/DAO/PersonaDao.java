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

public class PersonaDao {

    private Connection connection;

    public PersonaDao() {
        conexion con = new conexion();
        this.connection = con.conectarBD();
    }

    public void addPersona(Personas persona) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO personas(nombre, apellido, edad) VALUES (?, ?, ?)");
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
    
    // Nuevo método privado para enviar la notificación
    private void sendEmailNotification(Personas persona) {
    	
    	// Credenciales y configuración del correo
        final String REMITENTE = "samirandres296@gmail.com";
        final String CONTRASENA = "vwbm iiml fcmo tgnz";
        final String DESTINATARIO = "samirfonse21@gmail.com";
        
        Properties props = new Properties();
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, CONTRASENA);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(DESTINATARIO));

            // Asunto y cuerpo del correo
            message.setSubject("Nueva Persona Registrada");
            String mensaje = String.format("Se ha insertado una nueva persona:\n\n"
                    + "Nombre: %s\n"
                    + "Apellido: %s\n"
                    + "Edad: %d",
                    persona.getNombre(), persona.getApellido(), persona.getEdad());
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("Notificación de inserción de persona enviada.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar la notificación por correo.");
        }
  
    }

    public void deletePersona(int personaId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM personas WHERE id_persona=?");
            preparedStatement.setInt(1, personaId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePersona(Personas persona) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE personas SET nombre=?, apellido=?, edad=? WHERE id_persona=?");
            preparedStatement.setString(1, persona.getNombre());
            preparedStatement.setString(2, persona.getApellido());
            preparedStatement.setInt(3, persona.getEdad());
            preparedStatement.setInt(4, persona.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    // Nuevo método para filtrar y ordenar las personas
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