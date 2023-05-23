package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class Patient extends Person {
    private long pid;
    private LocalDate dateOfBirth;
    private String careLevel;
    private String roomnumber;
    private LocalDate dateOfArchive;
    private List<Treatment> allTreatments = new ArrayList<Treatment>();

    /**
     * constructs a patient from the given params.
     * 
     * @param firstName
     * @param surname
     * @param dateOfBirth
     * @param careLevel
     * @param roomnumber
     * @param dateOfArchive
     */
    public Patient(String firstName, String surname, LocalDate dateOfBirth, String careLevel,
            String roomnumber) {
        super(firstName, surname);
        this.dateOfBirth = dateOfBirth;
        this.careLevel = careLevel;
        this.roomnumber = roomnumber;
    }


    public Patient(long id, String firstName, String surname, LocalDate dateOfBirth,
            String careLevel, String roomnumber, LocalDate dateOfArchive) {

        super(firstName, surname);
        this.pid = id;
        this.dateOfBirth = dateOfBirth;
        this.careLevel = careLevel;
        this.roomnumber = roomnumber;
        this.dateOfArchive = dateOfArchive;
    }


    /**
     *
     * @return patient id
     */
    public long getPid() {
        return pid;
    }

    /**
     *
     * @return date of birth as a string
     */
    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    /**
     * convert given param to a localDate and store as new <code>birthOfDate</code>
     * 
     * @param dateOfBirth as string in the following format: YYYY-MM-DD
     */
    public void setDateOfBirth(String dateOfBirth) {
        LocalDate birthday = DateConverter.convertStringToLocalDate(dateOfBirth);
        this.dateOfBirth = birthday;
    }

    /**
     * convert given param to a localDate and store as new <code>birthOfDate</code>
     * 
     * archive date string in the following format: YYYY-MM-DD
     */
    public void setDateOfArchive(LocalDate timeOfArchive) {
        this.dateOfArchive = timeOfArchive;
        // System.out.println(dateOfArchive);
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
     * @return careLevel
     */
    public String getCareLevel() {
        return careLevel;
    }

    /**
     *
     * @param careLevel new care level
     */
    public void setCareLevel(String careLevel) {
        this.careLevel = careLevel;
    }

    /**
     *
     * @return roomNumber as string
     */
    public String getRoomnumber() {
        return roomnumber;
    }

    /**
     *
     * @param roomnumber
     */
    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }


    /**
     * adds a treatment to the treatment-list, if it does not already contain it.
     * 
     * @param m Treatment
     * @return true if the treatment was not already part of the list. otherwise false
     */
    public boolean add(Treatment m) {
        if (!this.allTreatments.contains(m)) {
            this.allTreatments.add(m);
            return true;
        }
        return false;
    }

    /**
     *
     * @return string-representation of the patient
     */
    public String toString() {
        return "Patient" + "\nMNID: " + this.pid + "\nFirstname: " + this.getFirstName()
                + "\nSurname: " + this.getSurname() + "\nBirthday: " + this.dateOfBirth
                + "\nCarelevel: " + this.careLevel + "\nRoomnumber: " + this.roomnumber
                + "\nDateOfArchive:" + this.dateOfArchive + "\n";
    }
}
