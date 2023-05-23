package model;

/**
 * Caregivers take care of patients at NURSING home
 */
public class Caregiver extends User {

    private long cid;
    private String phoneNumber;

    /**
    * constructs a caregiver from the given params
    *
    * @param firstname
    * @param surname
    * @param phoneNumber
     * @param role
    */
    public Caregiver(String firstname, String surname, String phoneNumber, String role) {
        super(firstname, surname, role);
        this.phoneNumber = phoneNumber;
    }

    /**
     * constructs a patient from the given params
     *
     * @param cid
     * @param firstName
     * @param surname
     * @param phoneNumber
     * @param role
     */

    public Caregiver (long cid, String firstName, String surname, String phoneNumber, String role) {
        super(firstName, surname,role);
        this.cid = cid;
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return caregiver id
     */

    public long getCid(){
        return cid;
    }

    /**
     * @return phoneNumber as string
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return string-representation of the caregiver
     */
    public String toString() {
        return "Caregiver" + "\nMNID: " + this.cid + "\nFirstname: " + this.getFirstName()
                + "\nSurname: " + this.getSurname() + "\nPhoneNumber: " + this.getPhoneNumber() +
                "\nRole: " + this.getRole() +"\n";
    }
}
