package model;

import java.time.LocalDate;

/**
 * Caregivers take care of patients at NURSING home
 */
public class Caregiver extends User {

    private long cid;
    private String phoneNumber;
    private LocalDate dateOfArchive;

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
        this.dateOfArchive = dateOfArchive;
    }

    /**
     * constructs a patient from the given params
     *
     * @param cid
     * @param firstName
     * @param surname
     * @param phoneNumber
     * @param role
     * @param dateOfArchive
     */

    public Caregiver (long cid, String firstName, String surname, String phoneNumber, String role, LocalDate dateOfArchive) {
        super(firstName, surname,role);
        this.cid = cid;
        this.phoneNumber = phoneNumber;
        this.dateOfArchive = dateOfArchive;
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
     * convert given param to a localDate and store as new <code>dateOfArchive</code>
     *
     * archive date string in the following format: YYYY-MM-DD
     */
    public void setDateOfArchive(LocalDate timeOfArchive) {
        this.dateOfArchive = timeOfArchive;
    }

    /**
     *
     *
     * @return dateOfArchived as string
     */
    public String getDateOfArchive() {
        if (this.dateOfArchive != null) {
            return this.dateOfArchive.toString();
        }
        return null;
    }

    /**
     *
     * @return string-representation of the caregiver
     */
    public String toString() {
        return "Caregiver" + "\nMNID: " + this.cid + "\nFirstname: " + this.getFirstName()
                + "\nSurname: " + this.getSurname() + "\nPhoneNumber: " + this.getPhoneNumber() +
                "\nRole: " + this.getRole() +"\nDateOfArchive: " + this.getDateOfArchive() + "\n";
    }
}
