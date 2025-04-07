package dao;

import dto.PersonaDto;
import entities.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDao {
    private Connection conn;

    public PersonaDao(Connection conn) {
        this.conn = conn;
    }

    public void insertPersona(Persona persona) {
        String query = "INSERT INTO Persona (idPersona, nombre, edad, idDireccion) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, persona.getIdPersona()); // idPersona
            ps.setString(2, persona.getNombre()); // nombre
            ps.setInt(3, persona.getEdad()); // edad
            ps.setInt(4, persona.getIdDireccion()); // idDireccion
            ps.executeUpdate();
            System.out.println("Persona insertada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean delete(Integer id) {
        String query = "DELETE FROM Persona WHERE idPersona = ?";
        PreparedStatement ps = null;
        boolean deleted = false;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            deleted = (rowsAffected > 0); // Retorna true si se eliminó al menos una fila

            conn.commit(); // Confirmar la eliminación
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return deleted;
    }


    public Persona find(Integer pk) {
        String query = "SELECT p.nombre, p.edad, p.idDireccion " +
                "FROM Persona p " +
                "WHERE p.idPersona = ?";
        Persona personaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                int direccion = rs.getInt("idDireccion");

                // Crear una nueva instancia de Persona con los datos recuperados de la consulta
                personaById = new Persona(pk, nombre, edad, direccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return personaById;
    }

    public PersonaDto findPersonaDTO(Integer pk) {
        String query = "SELECT p.nombre, p.edad, d.ciudad, d.calle, d.numero " +
                "FROM Persona p " +
                "JOIN Direccion d ON p.idDireccion = d.idDireccion " +
                "WHERE p.idPersona = ?";
        Persona personaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        PersonaDto personaDTO = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String ciudad = rs.getString("ciudad");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");

                // Crear una nueva instancia de PersonaDTO con los datos recuperados de la consulta
                personaDTO = new PersonaDto(nombre, edad, ciudad, calle, numero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return personaDTO;
    }
}