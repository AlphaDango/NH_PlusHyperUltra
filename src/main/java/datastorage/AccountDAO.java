package datastorage;

import model.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO extends DAOimp<Account> {

    public AccountDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(Account account) {
        return String.format(
                "INSERT INTO User (username, PASSWORT, cid, super) VALUES ('%s', '%s', '%s', '%s')",
                account.getUsername(), account.getPasswordHash(), account.getCid(), account.isSupervisor());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM User WHERE CID = %d", key);
    }

    @Override
    protected String getReadByCIDStatementString(long key) {
        return String.format("SELECT * FROM USER WHERE CID = %d", key);
    }

    @Override
    protected Account getInstanceFromResultSet(ResultSet set) throws SQLException {
        return null;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM User";
    }

    @Override
    protected ArrayList<Account> getListFromResultSet(ResultSet set) throws SQLException {
        return null;
    }

    @Override
    protected String getUpdateStatementString(Account account) {
        return String.format(
                "UPDATE USER SET username = '%s', PASSWORT = '%s', cid = '%s', super = '%s'",
                account.getUsername(), account.getPasswordHash(), account.getCid(), account.isSupervisor());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM User WHERE cid=%d", key);
    }


}
