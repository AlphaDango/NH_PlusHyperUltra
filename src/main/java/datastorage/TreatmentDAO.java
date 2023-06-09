package datastorage;

import model.Treatment;
import utils.DateConverter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO extends DAOimp<Treatment> {

    public TreatmentDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(Treatment treatment) {
        return String.format(
                "INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks) VALUES "
                        + "(%d, '%s', '%s', '%s', '%s', '%s')",
                treatment.getPid(), treatment.getDate(), treatment.getBegin(), treatment.getEnd(),
                treatment.getDescription(), treatment.getRemarks());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE tid = %d", key);
    }

    @Override
    protected String getReadByCIDStatementString(long key) {
        return String.format("SELECT * FROM Treatment WHERE PID IN (SELECT PID FROM Patient WHERE CID = %d AND DATEOFARCHIVE IS NULL) " ,key);
    }

    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        Treatment m = new Treatment(result.getLong(1), result.getLong(2), date, begin, end,
                result.getString(6), result.getString(7));
        return m;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM treatment";
    }

    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment t = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
            t = new Treatment(result.getLong(1), result.getLong(2), date, begin, end,
                    result.getString(6), result.getString(7));
            list.add(t);
        }
        return list;
    }

    @Override
    protected String getUpdateStatementString(Treatment treatment) {
        return String.format(
                "UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s',"
                        + "description = '%s', remarks = '%s' WHERE tid = %d",
                treatment.getPid(), treatment.getDate(), treatment.getBegin(), treatment.getEnd(),
                treatment.getDescription(), treatment.getRemarks(), treatment.getTid());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM treatment WHERE tid= %d", key);
    }

    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsOfOnePatientByPid(pid));
        list = getListFromResultSet(result);
        return list;
    }

    private String getReadAllTreatmentsOfOnePatientByPid(long pid) {
        return String.format("SELECT * FROM treatment WHERE pid = %d", pid);
    }

    public void deleteByPid(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("Delete FROM treatment WHERE pid= %d", key));
    }

    public ArrayList<Treatment> getAllNoneArchivedTreatment() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet set = st.executeQuery(getStatementAllNoneArchivedTreatment());
        return getListFromResultSet(set);
    }

    /**
     * 
     * 
     * @return <code>string</code> statement for sql database to join patient table with treatment
     *         table if patient has no date of archiving
     */
    private String getStatementAllNoneArchivedTreatment() {
        return "SELECT * FROM treatment INNER JOIN Patient ON treatment.pid = Patient.pid WHERE Patient.dateOfArchive IS NULL";
    }

}
