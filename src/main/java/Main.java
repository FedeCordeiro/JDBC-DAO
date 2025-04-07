
import dao.DireccionDao;
import dao.PersonaDao;
import dto.PersonaDto;
import entities.Direccion;
import entities.Persona;
import factory.AbstractFactory;
import utils.HelperMySQL;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();
        dbMySQL.closeConnection();

        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
        System.out.println();

        DireccionDao direccion = chosenFactory.getDireccionDAO();
        PersonaDao persona = chosenFactory.getPersonaDAO();

        // Insertamos una nueva dirección
        System.out.println("Insertando una nueva dirección...");
        Direccion nuevaDireccion = new Direccion(0, "Buenos Aires", "Av. Libertador", 1000); // 0 es un valor temporal, el ID lo generará la base de datos
        int filasAfectadas = DireccionDao.insert(nuevaDireccion);  // Insertamos la dirección en la base de datos
        System.out.println("Filas afectadas: " + filasAfectadas); // Filas afectadas debe ser 1 si la inserción fue exitosa

        System.out.println("////////////////////////////////////////////");
        System.out.println();

        // Borrar una direccion por ID
        System.out.println("Borrar una direccion por ID: ");
        int idDireccionEliminar = 6; // Cambia esto según la ID que quieres eliminar
        boolean eliminado = direccion.delete(idDireccionEliminar);

        if (eliminado) {
            System.out.println("Dirección con ID " + idDireccionEliminar + " eliminada correctamente.");
        } else {
            System.out.println("No se encontró una dirección con ID " + idDireccionEliminar);
        }

        System.out.println("////////////////////////////////////////////");
        System.out.println();

        // Borrar una persona por ID
        System.out.println("Borrar una persona por ID: ");
        int idPersonaEliminar = 7;
        boolean eliminadoP = persona.delete(idPersonaEliminar);

        if (eliminadoP) {
            System.out.println("Persona con ID " + idPersonaEliminar + " eliminada correctamente.");
        } else {
            System.out.println("No se encontró una Persona con ID " + idPersonaEliminar);
        }

        System.out.println("////////////////////////////////////////////");
        System.out.println();

        System.out.println("Busco una Persona por ID: ");
        Persona personaById = persona.find(2);
        System.out.println(personaById);

        System.out.println("////////////////////////////////////////////");
        System.out.println();

        System.out.println("Lista de direcciones: ");
        List<Direccion> listadoDirecciones = direccion.selectList();
        for (Direccion dir : listadoDirecciones) {
            System.out.println(dir);
        }

        System.out.println("////////////////////////////////////////////");
        System.out.println();

        PersonaDto personaDTO = persona.findPersonaDTO(2);
        System.out.println(personaDTO);

    }
}
