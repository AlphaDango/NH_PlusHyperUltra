package model;

/**
 * Caregivers take care of patients at NURSING home
 */
public class Caregiver extends Person {
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
}
