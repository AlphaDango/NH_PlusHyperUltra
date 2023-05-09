package model;

import java.time.LocalDate;

public class Caregiver extends Person{

    String UserName, HashedPasswort, Phone;

    Integer PermissionLevel;






    public Caregiver(String firstName, String surname) {
        super(firstName, surname);
    }
}
