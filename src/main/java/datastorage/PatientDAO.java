package datastorage;

import model.Patient;
import utils.DateConverter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific
 * patient-SQL-queries.
 */
public class PatientDAO extends DAOimp<Patient> {

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * 
     * @param conn
     */
    public PatientDAO(Connection conn) {
        super(conn);
    }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given patient
     * 
     * @param patient for which a specific INSERT INTO is to be created //DATEOFARCHIVE
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Patient patient) {
        String dateOfArchive =
                patient.getDateOfArchive() != null ? "'" + patient.getDateOfArchive() + "'"
                        : "NULL";
        return String.format(
                "INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber, dateOfArchive) VALUES ('%s', '%s', '%s', '%s', '%s', %s)",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(),
                patient.getCareLevel(), patient.getRoomnumber(), dateOfArchive);
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * 
     * @param key for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM patient WHERE pid = %d DATEOFARCHIVE IS NULL", key);
    }

    @Override
    protected String getReadByCIDStatementString(long key) {
        return String.format("SELECT * FROM patient WHERE CID = %d AND DATEOFARCHIVE IS NULL ", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient</code>
     * 
     * @param result ResultSet with a single row. Columns will be mapped to a patient-object.
     * @return patient with the data from the resultSet.
     */
    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        Patient p = null;
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        LocalDate dateOfArchive = DateConverter.convertStringToLocalDate(result.getString(7));
        p = new Patient(result.getLong(1), result.getString(2), result.getString(3), date,
                result.getString(5), result.getString(6), dateOfArchive);
        return p;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all patients.
     * 
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM patient";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient-List</code>
     * 
     * @param result ResultSet with a multiple rows. Data will be mapped to patient-object.
     * @return ArrayList with patients from the resultSet.
     */
    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<Patient>();
        Patient p = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            LocalDate dateOfArchive = DateConverter.convertStringToLocalDate(result.getString(7));
            p = new Patient(result.getInt(1), result.getString(2), result.getString(3), date,
                    result.getString(5), result.getString(6), dateOfArchive);
            list.add(p);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given patient
     * 
     * @param patient for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Patient patient) {
        String dateOfArchive =
                patient.getDateOfArchive() != null ? "'" + patient.getDateOfArchive() + "'"
                        : "NULL";
        return String.format(
                "UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s',"
                        + "roomnumber = '%s', dateOfArchive = %s WHERE pid = %d",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(),
                patient.getCareLevel(), patient.getRoomnumber(), dateOfArchive, patient.getPid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * 
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM patient WHERE pid=%d", key);
    }

    /**
     * 
     * 
     * @return list of All Patient that are not archived
     */
    public ArrayList<Patient> getAllNoneArchivedPatients() throws SQLException {
        Statement st = conn.createStatement();
        return getListFromResultSet(st.executeQuery(getStatementAllNoneArchivedPatients()));
    }

    /**
     * 
     * 
     * @return <code>string statement</code> to query all the patients if there is no date for
     *         archiving
     */
    private String getStatementAllNoneArchivedPatients() {
        return "SELECT * FROM Patient WHERE DATEOFARCHIVE IS NULL";
    }

    /**
     * 
     * 
     * @return list of All Patient that are archived
     */
    public ArrayList<Patient> getAllArchivedPatients() throws SQLException {
        Statement st = conn.createStatement();
        return getListFromResultSet(st.executeQuery(getStatementAllArchivedPatients()));
    }

    /**
     * 
     * 
     * @return <code>string statement</code> to query all the patients if there is a date for
     *         archiving
     */
    private String getStatementAllArchivedPatients() {
        return "SELECT * FROM Patient WHERE DATEOFARCHIVE IS NOT NULL";
    }

}
