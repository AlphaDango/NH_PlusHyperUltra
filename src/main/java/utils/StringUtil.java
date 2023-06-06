package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Oliver Neumann
 * @version 1.0
 * Helper Class for some StringUtils
 */
public class StringUtil {

    /**
     * Hashes a String with the SHA256 algorithm.
     * @param textToHash String that is to be hashed.
     * @return Hashvalue of the String.
     */
    public static String StringToSHA256(String textToHash){
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            assert false: "MessageDigest couldn't initialise";
        }

        byte[] hashData = md.digest(textToHash.getBytes(UTF_8));
        StringBuilder buffer = new StringBuilder();

        for (byte b : hashData)
            buffer.append(String.format("%02x",b));

        return buffer.toString();
    }

    /**
     * Checks if the given String only contains numbers.
     * @param text String to check.
     * @return True if the String only contains numbers.
     */
    public static Boolean StringIsNumber(String text){
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
