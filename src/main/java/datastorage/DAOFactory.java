package datastorage;

/**
 * DAO Factory Class to create a specific DAO with an open Connection.
 */
public class DAOFactory {

    private static DAOFactory instance;

    /**
     * Constructor is private to keep it a singleton.
     */
    private DAOFactory() {

    }

    /**
     * Creates a DAOFactory if the static variable is null and returns the instance.
     * @return Instance of the DAOFactory.
     */
    public static DAOFactory getDAOFactory() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    /**
     * Create a TreatmentDAO
     * @return TreatmentDAO instance.
     */
    public TreatmentDAO createTreatmentDAO() {
        return new TreatmentDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a PatientDAO.
     * @return PatientDAO instance.
     */
    public PatientDAO createPatientDAO() {
        return new PatientDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a CaregiverDAO.
     * @return CaregiverDAO instance.
     */
    public CaregiverDAO createCaregiverDAO() {
        return new CaregiverDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Creates an AccountDAO.
     * @return AccountDAO instance.
     */
    public AccountDAO createAccountDAO(){
        return new AccountDAO(ConnectionBuilder.getConnection());
    }
}