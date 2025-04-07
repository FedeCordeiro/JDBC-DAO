package factory;

import dao.DireccionDao;
import dao.PersonaDao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase MySQLDAOFactory
 * <p>
 * Esta clase implementa la clase Abstract Factory para MySQL.
 * Maneja la conexión a la base de datos y la creación de DAOs.
 *
 * @see factory.AbstractFactory
 *
 * @author Federico Cordeiro
 * @version 1.0
 */

public class MySQLDAOFactory extends AbstractFactory {
    private static MySQLDAOFactory instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/demodao";
    public static Connection conn;

    // Constructor privado para evitar instanciación directa
    private MySQLDAOFactory() {}

    /**
     * Implementación de Singleton para obtener la única instancia de la fábrica.
     * @return Instancia única de MySQLDAOFactory
     */
    public static synchronized MySQLDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    /**
     * Crea y devuelve una conexión a la base de datos.
     * @return Objeto Connection
     */
    public static Connection createConnection() {
        if (conn != null) {
            return conn;
        }

        try {
            // Carga el driver de MySQL
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            // Establece la conexión con la base de datos
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Devuelve una instancia de PersonaDao asociada a la conexión.
     * @return Objeto PersonaDao
     */
    @Override
    public PersonaDao getPersonaDAO() {
        return new PersonaDao(createConnection());
    }

    /**
     * Devuelve una instancia de DireccionDao asociada a la conexión.
     * @return Objeto DireccionDao
     */
    @Override
    public DireccionDao getDireccionDAO() {
        return new DireccionDao(createConnection());
    }
}
