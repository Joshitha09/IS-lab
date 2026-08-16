import java.security.MessageDigest;
import java.util.Scanner;

public class MD5 {
    static String getMD5(String message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(message.getBytes());

        StringBuilder sb = new StringBuilder();

        for (byte b : hash)
            sb.append(String.format("%02x", b));

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message: ");
        String message = sc.nextLine();

        String hash = getMD5(message);

        System.out.println("\nGenerated MD5 Hash: " + hash);

        System.out.print("\nEnter received hash for verification: ");
        String receivedHash = sc.nextLine();

        if (hash.equals(receivedHash))
            System.out.println("Signature Verified Successfully.");
        else
            System.out.println("Signature Verification Failed.");

        sc.close();
    }
}