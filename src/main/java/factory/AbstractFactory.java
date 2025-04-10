package factory;

import dao.DireccionDao;
import dao.PersonaDao;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;
    public abstract PersonaDao getPersonaDAO();
    public abstract DireccionDao getDireccionDAO();
    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC : {
                return MySQLDAOFactory.getInstance();
            }
            case DERBY_JDBC: return null;
            default: return null;
        }
    }

}
