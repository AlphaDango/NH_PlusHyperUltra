package datastorage;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface of the DAO.
 * Implements all base Methods for the DAO Classes.
 * @param <T> Model Type like Caregiver or Account.
 */
public interface DAO<T> {
    /**
     * Method to create a record in the database.
     * @param t Model Type.
     * @throws SQLException
     */
    void create(T t) throws SQLException;

    /**
     * Reads a record from the Database with a give ID.
     * @param key ID to look for in the database.
     * @return Model Type.
     * @throws SQLException
     */
    T read(long key) throws SQLException;

    /**
     * Reads all records from the Database of a specific Modeltype.
     * @return List of a Modeltype.
     * @throws SQLException
     */
    List<T> readAll() throws SQLException;

    /**
     * Updates a record in the database.
     * @param t Model Type.
     * @throws SQLException
     */
    void update(T t) throws SQLException;

    /**
     * Deletes a record in the database with a specific ID.
     * @param key ID to look for in the database.
     * @throws SQLException
     */
    void deleteById(long key) throws SQLException;
}
