package model;

public class User extends Person{

    private String role;

    /**
     * Constructor
     * @param firstname
     * @param surname
     * @param role
     */
    public User (String firstname, String surname, String role){
        super(firstname,surname);
        this.role = role;
    }

    /**
     * Get the Value stored in the role variable
     * @return role
     */
    public String getRole(){return role;}

    /**
     * set the new Value for the role variable
     * @param role
     */
    public void setRole(String role) {this.role = role;}
}
