package utils;

// Importaciones necesarias
import entities.Persona;
import entities.Direccion;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelperMySQL {
    private Connection conn = null; // Conexión a la base de datos

    // Constructor que inicializa la conexión a MySQL
    public HelperMySQL() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/demodao";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false); // Deshabilita autocommit para manejar transacciones manualmente
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para cerrar la conexión a la base de datos
    public void closeConnection() {
        if (conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Elimina las tablas si existen
    public void dropTables() throws SQLException {
        String dropPersona = "DROP TABLE IF EXISTS Persona";
        this.conn.prepareStatement(dropPersona).execute();
        this.conn.commit();

        String dropDireccion = "DROP TABLE IF EXISTS Direccion";
        this.conn.prepareStatement(dropDireccion).execute();
        this.conn.commit();
    }

    // Crea las tablas si no existen
    public void createTables() throws SQLException {
        String tableDireccion = "CREATE TABLE IF NOT EXISTS Direccion(" +
                "idDireccion INT NOT NULL, " +
                "ciudad VARCHAR(50), " +
                "calle VARCHAR(50), " +
                "numero INT NOT NULL, " +
                "CONSTRAINT Direccion_pk PRIMARY KEY (idDireccion));" ;
        this.conn.prepareStatement(tableDireccion).execute();
        this.conn.commit();

        String tablePersona = "CREATE TABLE IF NOT EXISTS Persona(" +
                "idPersona INT NOT NULL, " +
                "nombre VARCHAR(500), " +
                "edad INT NOT NULL, " +
                "idDireccion INT NOT NULL, " +
                "CONSTRAINT Persona_pk PRIMARY KEY (idPersona), "+
                "CONSTRAINT FK_idDireccion FOREIGN KEY (idDireccion) REFERENCES Direccion (idDireccion))";
        this.conn.prepareStatement(tablePersona).execute();
        this.conn.commit();
    }

    // Método para leer los datos desde archivos CSV
    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src\\main\\resources\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};  // Puedes configurar un encabezado personalizado aquí si es necesario
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);

        return csvParser.getRecords();
    }

    // Poblar la base de datos con los datos de los archivos CSV
    public void populateDB() throws Exception {
        try {
            System.out.println("Populating DB...");
            for(CSVRecord row : getData("direcciones.csv")) {
                if(row.size() >= 4) { // Verificar que hay al menos 4 campos en el CSVRecord
                    String idString = row.get(0);
                    String numeroString = row.get(3);
                    if(!idString.isEmpty() && !numeroString.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idString);
                            int numero = Integer.parseInt(numeroString);
                            Direccion direccion = new Direccion(id, row.get(1), row.get(2), numero);
                            insertDireccion(direccion, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de dirección: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Direcciones insertadas");

            for (CSVRecord row : getData("personas.csv")) {
                if (row.size() >= 4) { // Verificar que hay al menos 4 campos en el CSVRecord
                    String idString = row.get(0);
                    String nombre = row.get(1);
                    String edadString = row.get(2);
                    String idDireccionString = row.get(3);

                    if (!idString.isEmpty() && !nombre.isEmpty() && !edadString.isEmpty() && !idDireccionString.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idString);
                            int edad = Integer.parseInt(edadString);
                            int idDireccion = Integer.parseInt(idDireccionString);

                            Persona persona = new Persona(id, nombre, edad, idDireccion);
                            insertPersona(persona, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de persona: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("Personas insertadas");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inserta una persona en la base de datos
    private int insertPersona (Persona persona, Connection conn) throws Exception{
        String insert = "INSERT INTO Persona (idPersona, nombre, edad,idDireccion) VALUES (?, ?, ?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1,persona.getIdPersona());
            ps.setString(2, persona.getNombre());
            ps.setInt(3,persona.getEdad());
            ps.setInt(4,persona.getIdDireccion());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    // Inserta una dirección en la base de datos
    private int insertDireccion(Direccion direccion, Connection conn) throws Exception {
        String insert = "INSERT INTO Direccion (idDireccion, ciudad, calle,numero) VALUES (?, ?, ?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1,direccion.getIdDireccion());
            ps.setString(2, direccion.getCiudad());
            ps.setString(3,direccion.getCalle());
            ps.setInt(4,direccion.getNumero());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    // Cierra el PreparedStatement y realiza el commit
    private void closePsAndCommit(Connection conn, PreparedStatement ps) {
        if (conn != null){
            try {
                ps.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}