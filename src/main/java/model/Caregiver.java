package model;

/**
 * Caregivers take care of patients at NURSING home
 */
public class Caregiver extends Person {

    private long cid;
    private String phoneNumber;

    /**
    * constructs a caregiver from the given params
    *
    * @param firstname
    * @param surname
    * @param phoneNumber
    */
    public Caregiver(String firstname, String surname, String phoneNumber) {
        super(firstname, surname);
        this.phoneNumber = phoneNumber;
    }

    /**
     * constructs a patient from the given params
     *
     * @params cid
     * @params firstName
     * @params surname
     * @params phoneNumber
     */

    public Caregiver (long cid, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
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
                + "\nSurname: " + this.getSurname() + "\nPhoneNumber: " + this.getPhoneNumber() + "\n";
    }
}
