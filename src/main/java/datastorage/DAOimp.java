package datastorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of the DAO interface.
 * @param <T>
 */
public abstract class DAOimp<T> implements DAO<T> {
    protected Connection conn;

    /**
     * Constructor which gets a Connection to hold.
     * @param conn
     */
    public DAOimp(Connection conn) {
        this.conn = conn;
    }

    @Override
    /**
     * Method to create a record in the database.
     * @param t Model Type.
     * @throws SQLException
     */
    public void create(T t) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getCreateStatementString(t));
    }

    /**
     * Reads a record from the Database with a give ID.
     * @param key ID to look for in the database.
     * @return Model Type.
     * @throws SQLException
     */
    @Override
    public T read(long key) throws SQLException {
        T object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadByIDStatementString(key));
        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }
        return object;
    }

    @Override
    /**
     * Reads all records from the Database of a specific Modeltype.
     * @return List of a Modeltype.
     * @throws SQLException
     */
    public List<T> readAll() throws SQLException {
        ArrayList<T> list = new ArrayList<T>();
        T object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllStatementString());
        list = getListFromResultSet(result);
        return list;
    }

    /**
     * Reads all records from the Database of a specific Modeltype that hold a specific ID.
     * @param CID ID too look in the database.
     * @return List of a Modeltype.
     * @throws SQLException
     */
    public List<T> readByID(int CID) throws SQLException {
        ArrayList<T> list;
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(getReadByCIDStatementString(CID));
        list = getListFromResultSet(resultSet);
        return list;
    }

    /**
     * Updates a record in the database.
     * @param t Model Type.
     * @throws SQLException
     */
    @Override
    public void update(T t) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getUpdateStatementString(t));
    }

    @Override
    /**
     * Deletes a record in the database with a specific ID.
     * @param key ID to look for in the database.
     * @throws SQLException
     */
    public void deleteById(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getDeleteStatementString(key));
    }

    protected abstract String getCreateStatementString(T t);

    protected abstract String getReadByIDStatementString(long key);

    protected abstract String getReadByCIDStatementString(long key);

    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getReadAllStatementString();

    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getUpdateStatementString(T t);

    protected abstract String getDeleteStatementString(long key);

}
