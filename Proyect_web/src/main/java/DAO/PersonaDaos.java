package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controlador.conexion;
import modelo.Personas;

/**
 * Clase de acceso a datos (DAO) para la entidad Personas.
 * Encapsula toda la lógica de interacción con la tabla 'personas' de la base de datos.
 */
public class PersonaDaos { // Cambiado de PersonaDaos a PersonaDao
    
    // El constructor de la clase
    public PersonaDaos() {
    }

    /**
     * Método de ayuda para obtener una conexión a la base de datos.
     * Utiliza la clase 'conexion' para centralizar la configuración de la conexión.
     * @return Un objeto Connection.
     * @throws SQLException Si ocurre un error al intentar la conexión.
     */
    private Connection obtenerConexion() throws SQLException {
        // Crea una instancia de tu clase de conexión
        conexion conector = new conexion();
        // Llama al método para obtener la conexión
        return conector.conectarBD();
    }
    
    /**
     * Inserta un nuevo registro de persona en la base de datos.
     * @param persona El objeto Persona a insertar.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public void addPersona(Personas persona) throws SQLException {
        String sql = "INSERT INTO personas (nombre, apellido, edad) VALUES (?, ?, ?)";
        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setInt(3, persona.getEdad());
            ps.executeUpdate();
        }
    }
    
    /**
     * Obtiene una persona por su ID.
     * @param id El ID de la persona a buscar.
     * @return El objeto Persona si se encuentra, o null si no existe.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public Personas selectPersona(int id) throws SQLException {
        Personas persona = null;
        String sql = "SELECT id_persona, nombre, apellido, edad FROM personas WHERE id_persona = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    persona = new Personas();
                    persona.setId(rs.getInt("id_persona"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setApellido(rs.getString("apellido"));
                    persona.setEdad(rs.getInt("edad"));
                }
            }
        }
        return persona;
    }
    
    /**
     * Actualiza un registro de persona en la base de datos.
     * @param persona El objeto Persona con los datos actualizados.
     * @return true si la actualización fue exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public boolean updatePersona(Personas persona) throws SQLException {
        String sql = "UPDATE personas SET nombre = ?, apellido = ?, edad = ? WHERE id_persona = ?";
        boolean rowUpdated;
        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setInt(3, persona.getEdad());
            ps.setInt(4, persona.getId());
            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    /**
     * Elimina un registro de persona por su ID.
     * @param id El ID de la persona a eliminar.
     * @return true si la eliminación fue exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public boolean deletePersona(int id) throws SQLException {
        String sql = "DELETE FROM personas WHERE id_persona = ?";
        boolean rowDeleted;
        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    /**
     * Cuenta el número total de registros en la tabla 'personas'.
     * @return El número total de personas.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public int countTotalPersonas() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM personas";
        
        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }

    /**
     * Cuenta el número de registros creados en el día de hoy.
     * Esta consulta asume que existe una columna 'fecha_creacion' de tipo DATE o DATETIME.
     * @return El número de personas creadas hoy.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public int countPersonasCreatedToday() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM personas WHERE DATE(fecha_creacion) = CURDATE()"; 
        
        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }

    /**
     * Obtiene la lista completa de personas de la base de datos, ordenadas por ID.
     * @return Una lista de objetos Personas.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<Personas> selectAllPersonas() throws SQLException {
        List<Personas> listPersonas = new ArrayList<>();
        String sql = "SELECT * FROM personas ORDER BY id_persona DESC";
        
        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Personas persona = new Personas();
                persona.setId(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setEdad(rs.getInt("edad"));
                listPersonas.add(persona);
            }
        }
        return listPersonas;
    }
}