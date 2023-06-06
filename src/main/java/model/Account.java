package model;

/**
 * @author Oliver Neumann
 * @version 1.0
 * Model of an Account.
 */
public class Account {

    private long cid;
    private String username;
    private String passwordHash;
    private boolean supervisor;

    public Account(long cid, String username, String passwordHash, boolean supervisor){
        this.cid = cid;
        this.username = username;
        this.passwordHash = passwordHash;
        this.supervisor = supervisor;
    }

    /**
     * Getter for the CID.
     * @return CID
     */
    public long getCid() {
        return cid;
    }

    /**
     * Setter for the CID.
     * @param cid
     */
    public void setCid(long cid) {
        this.cid = cid;
    }

    /**
     * Getter for the Username.
     * @return Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the Username.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the hashed password.
     * @return Hashed Password.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setter for the hashed password.
     * @param passwordHash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the supervisor boolean as a byte value.
     * @return
     */
    public byte isSupervisor() {
        if(supervisor)
            return 1;
        else
            return 0;
    }

    /**
     * Interprets a byte value to a supervisor boolean.
     * @param supervisor
     */
    public void setSupervisor(byte supervisor) {
        if(supervisor > 0)
            this.supervisor = true;
        else
            this.supervisor = false;
    }
}
