
import java.util.*;
import java.nio.charset.StandardCharsets;

public class Main {

    static String encrypt(String data, String key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            result.append((char) (
                data.charAt(i) ^ key.charAt(i % key.length())
            ));
        }

        return Base64.getEncoder().encodeToString(
            result.toString().getBytes(StandardCharsets.ISO_8859_1)
        );
    }

    static String decrypt(String data, String key) {
        byte[] decoded = Base64.getDecoder().decode(data);
        String text = new String(decoded, StandardCharsets.ISO_8859_1);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            result.append((char) (
                text.charAt(i) ^ key.charAt(i % key.length())
            ));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Client Name: ");
        String client = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String clientKey = password;
        String tgsKey = "TGS_SECRET";

        System.out.println("\n1. Authentication Server (AS)");
        String tgt = encrypt(client + ":TGS", clientKey);
        System.out.println("Ticket Granting Ticket: " + tgt);

        System.out.println("\n2. Ticket Granting Server (TGS)");
        String ticket = decrypt(tgt, clientKey);

        if (ticket.equals(client + ":TGS")) {
            String serviceTicket = encrypt(
                client + ":SERVICE", tgsKey
            );

            System.out.println("Service Ticket: " + serviceTicket);
            System.out.println("\n3. Service Server");

            String verified = decrypt(serviceTicket, tgsKey);

            if (verified.equals(client + ":SERVICE")) {
                System.out.println("Authentication Successful!");
                System.out.println("Welcome, " + client);
            } else {
                System.out.println("Authentication Failed!");
            }
        } else {
            System.out.println("Authentication Failed!");
        }

        sc.close();
    }
}
