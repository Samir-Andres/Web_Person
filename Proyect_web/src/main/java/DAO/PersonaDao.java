package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        } catch (SQLException e) {
            e.printStackTrace();
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