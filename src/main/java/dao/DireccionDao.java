package dao;


import entities.Direccion;
import entities.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DireccionDao {
    private static Connection conn;

    public DireccionDao(Connection conn) {
        this.conn = conn;
    }
    public static int insert(Direccion dao) throws SQLException {
        String query = "INSERT INTO Direccion (idDireccion, ciudad, calle, numero) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        int rowsAffected = 0;  // Para saber cuántas filas fueron afectadas

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, dao.getIdDireccion());
            ps.setString(2, dao.getCiudad());
            ps.setString(3, dao.getCalle());
            ps.setInt(4, dao.getNumero());

            rowsAffected = ps.executeUpdate();  // Ejecuta la consulta de inserción
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }

        return rowsAffected;  // Retorna el número de filas afectadas
    }

    public boolean delete(Integer id) {
        String query = "DELETE FROM Direccion WHERE idDireccion = ?";
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


    public Direccion find(Integer pk) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }


    public boolean update(Direccion dao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }


    public List<Direccion> selectList() {
        // Consulta SQL
        String query = "SELECT * FROM Direccion";

        Persona personaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Direccion> listado = null;

        try {

            // Se crea un prepareStatement para hacer la consulta
            ps = conn.prepareStatement(query);

            // Se ejecuta la consulta
            rs = ps.executeQuery();

            // Crear una nueva instancia de Persona con los datos recuperados de la consulta
            listado = new ArrayList<Direccion>();

            // Recorrer filas y extraer valores
            while (rs.next()) { // Verificar si hay resultados
                int idDireccion = rs.getInt("idDireccion");
                String ciudad = rs.getString("ciudad");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");
                Direccion direccion = new Direccion(idDireccion, ciudad, calle, numero);
                listado.add(direccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listado;
    }
}

/*

 Este código implementa un DAO para la tabla Direccion y proporciona un método selectList()
 que recupera todas las direcciones de la base de datos.

 El selectList() utiliza JDBC para ejecutar una consulta SQL y obtener los resultados,
 los cuales luego son convertidos en objetos Direccion y almacenados en una lista.

 */


