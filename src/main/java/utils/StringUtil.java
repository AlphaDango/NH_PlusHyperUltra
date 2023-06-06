package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StringUtil {

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

    public static Boolean StringIsNumber(String text){
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
