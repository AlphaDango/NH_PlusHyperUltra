package datastorage;

import model.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Oliver Neumann
 * @version 1.0
 * DAO Class for the Account model.
 */
public class AccountDAO extends DAOimp<Account> {

    /**
     * Default constructor of this DAO.
     * @param conn Connection to the Database
     */
    public AccountDAO(Connection conn) {
        super(conn);
    }

    @Override
    /**
     * Generates an SQL Statements that creates new User in the User Table
     */
    protected String getCreateStatementString(Account account) {
        return String.format(
                "INSERT INTO User (username, PASSWORT, cid, super) VALUES ('%s', '%s', '%s', '%s')",
                account.getUsername(), account.getPasswordHash(), account.getCid(), account.isSupervisor());
    }

    @Override
    /**
     * Selevts the user which has the given CID
     */
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM User WHERE CID = %d", key);
    }

    @Override
    /**
     * Selevts the user which has the given CID
     */
    protected String getReadByCIDStatementString(long key) {
        return String.format("SELECT * FROM USER WHERE CID = %d", key);
    }

    @Override
    /**
     * Unused with no function.
     */
    protected Account getInstanceFromResultSet(ResultSet set) throws SQLException {
        return null;
    }

    @Override
    /**
     * Selects every record from the User table. Currently unused.
     */
    protected String getReadAllStatementString() {
        return "SELECT * FROM User";
    }

    @Override
    /**
     * Unused with no function.
     */
    protected ArrayList<Account> getListFromResultSet(ResultSet set) throws SQLException {
        return null;
    }

    @Override
    /**
     * Updates a record in the user table which has the given CID.
     */
    protected String getUpdateStatementString(Account account) {
        return String.format(
                "UPDATE USER SET username = '%s', PASSWORT = '%s', super = '%s' WHER cid = '%s',",
                account.getUsername(), account.getPasswordHash(), account.isSupervisor(), account.getCid());
    }

    @Override
    /**
     * Deletes a record in the user table with a given CID.
     */
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM User WHERE cid=%d", key);
    }


}
