package datastorage;

import model.Caregiver;
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
 * caregiver-SQL-queries.
 */
public class CaregiverDAO extends DAOimp<Caregiver> {

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     *
     * @param conn
     */
    public CaregiverDAO (Connection conn) { super(conn); }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given caregiver
     *
     * @param caregiver for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Caregiver caregiver) {
        return String.format(
                "INSERT INTO Caregiver (firstname, surname, phoneNumber, role) VALUES ('%s', '%s', '%s', '%s')",
                caregiver.getFirstName(), caregiver.getSurname(), caregiver.getPhoneNumber(), caregiver.getRole());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     *
     * @param key for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM caregiver WHERE cid = %d AND DATEOFARCHIVE IS NULL", key);
    }

    @Override
    protected String getReadByCIDStatementString(long key) {
        return String.format("SELECT * FROM caregiver WHERE CID = %d AND DATEOFARCHIVE IS NULL", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient</code>
     *
     * @param result ResultSet with a single row. Columns will be mapped to a caregiver-object.
     * @return caregiver with the data from the resultSet.
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
        Caregiver c = null;
        //LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        LocalDate dateOfArchive = DateConverter.convertStringToLocalDate(result.getString(7));
        c = new Caregiver(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), dateOfArchive);
        return c;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all caregivers.
     *
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM caregiver";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Caregiver-List</code>
     *
     * @param result ResultSet with a multiple rows. Data will be mapped to caregiver-object.
     * @return ArrayList with caregivers from the resultSet.
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<Caregiver>();
        Caregiver c = null;
        while (result.next()) {
            //LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            LocalDate dateOfArchive = DateConverter.convertStringToLocalDate(result.getString(6));
            c = new Caregiver(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), dateOfArchive);
            list.add(c);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given caregiver
     *
     * @param caregiver for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Caregiver caregiver) {
        String dateOfArchive =
                caregiver.getDateOfArchive() != null ? "'" + caregiver.getDateOfArchive() + "'"
                        : "NULL";

        return String.format(
                "UPDATE caregiver SET firstname = '%s', surname = '%s', phoneNumber = '%s', role = '%s',dateOfArchive = %s  WHERE cid = %d",
                caregiver.getFirstName(), caregiver.getSurname(), caregiver.getPhoneNumber(), caregiver.getRole(), dateOfArchive, caregiver.getCid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     *
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM caregiver WHERE cid=%d", key);
    }

    /**
     *
     *
     * @return list of All caregivers that are not archived
     */
    public ArrayList<Caregiver> getAllNoneArchivedCaregivers() throws SQLException {
        Statement st = conn.createStatement();
        return getListFromResultSet(st.executeQuery(getStatementAllNoneArchivedCaregivers()));
    }

    /**
     *
     *
     * @return <code>string statement</code> to query all the caregivers if there is no date for
     *         archiving
     */
    private String getStatementAllNoneArchivedCaregivers() {
        return "SELECT * FROM Caregiver WHERE DATEOFARCHIVE IS NULL";
    }

    /**
     *
     *
     * @return list of All caregivers that are archived
     */
    public ArrayList<Caregiver> getAllArchivedCaregivers() throws SQLException {
        Statement st = conn.createStatement();
        return getListFromResultSet(st.executeQuery(getStatementAllArchivedCaregivers()));
    }

    /**
     *
     *
     * @return <code>string statement</code> to query all the Caregivers if there is a date for
     *         archiving
     */
    private String getStatementAllArchivedCaregivers() {
        return "SELECT * FROM Caregiver WHERE DATEOFARCHIVE IS NOT NULL";
    }

}
