package model;

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

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte isSupervisor() {
        if(supervisor)
            return 1;
        else
            return 0;
    }

    public void setSupervisor(byte supervisor) {
        if(supervisor > 0)
            this.supervisor = true;
        else
            this.supervisor = false;
    }
}
