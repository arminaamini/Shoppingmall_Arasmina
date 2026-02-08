package commons.util;
import java.security.MessageDigest;
                                                     /////////////////OK\\\\\\\\\\\\\\\\\\\\\\\\\\\\
public class PasswordHasherUtil {
    
    public static String hasher(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] inputBytes = password.getBytes();
            byte[] hashedBytes = md.digest(inputBytes);

            StringBuilder sb = new StringBuilder();
            for(byte b : hashedBytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch(Exception e){
            throw new RuntimeException("Error hashing password", e);
        }
    }
    public static void main(String[] args) {
        String password1 = "1357";
        String password2 = "2468";
        String hashedPass1 = hasher(password1);
        String hashedPass2 = hasher(password2);
        System.out.println(" hashedPass1: "+ hashedPass1);
        System.out.println(" hashedPass2: "+ hashedPass2);


    }
}

