
import javax.crypto.Mac;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Scanner;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message: ");
        String message = sc.nextLine();

        KeyGenerator kg = KeyGenerator.getInstance("HmacSHA256");
        SecretKey key = kg.generateKey();

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);

        byte[] tag = mac.doFinal(
                message.getBytes(StandardCharsets.UTF_8));

        String code = Base64.getEncoder().encodeToString(tag);

        System.out.println("Message Authentication Code: " + code);

        mac.init(key);
        byte[] verify = mac.doFinal(
                message.getBytes(StandardCharsets.UTF_8));

        if (MessageDigest.isEqual(tag, verify))
            System.out.println("Message Authenticated Successfully");
        else
            System.out.println("Authentication Failed");

        sc.close();
    }
}
