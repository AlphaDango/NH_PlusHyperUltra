package utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateConverter {
    /**
     * Converts a Sting to a LocalDate.
     * @param date Data as a String.
     * @return LocalDate of the Stringinput.
     */
    public static LocalDate convertStringToLocalDate(String date) {
        if (date == null) {
            return null;
        }
        String[] array = date.split("-");
        LocalDate result = LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
                Integer.parseInt(array[2]));
        return result;
    }

    /**
     * Converts a String to a LocalTime variable.
     * @param time Time as a String.
     * @return LocalTime variable converted from the String.
     */
    public static LocalTime convertStringToLocalTime(String time) {
        String[] array = time.split(":");
        LocalTime result = LocalTime.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
        return result;
    }
}
