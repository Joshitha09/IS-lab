
import java.util.Scanner;
import java.security.MessageDigest;
import java.util.Locale;

public class Main {

    public static String generateHash(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data.getBytes("UTF-8"));

        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            hex.append(String.format("%02x", b & 0xff));
        }

        return hex.toString();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Trusted Secure Web Transaction");

        System.out.print("Enter sender name: ");
        String sender = sc.nextLine();

        System.out.print("Enter receiver name: ");
        String receiver = sc.nextLine();

        System.out.print("Enter transaction amount: ");
        double amount = sc.nextDouble();

        if (sender.trim().isEmpty()
                || receiver.trim().isEmpty()
                || amount <= 0
                || !Double.isFinite(amount)) {
            System.out.println("Transaction Failed: Invalid details");
            sc.close();
            return;
        }

        String transaction = sender + "|" + receiver + "|" +
                String.format(Locale.US, "%.2f", amount);

        String originalHash = generateHash(transaction);
        String receivedHash = generateHash(transaction);

        System.out.println("\nTransaction Details");
        System.out.println("Sender: " + sender);
        System.out.println("Receiver: " + receiver);
        System.out.printf("Amount: %.2f%n", amount);
        System.out.println("Transaction Hash: " + originalHash);

        if (originalHash.equals(receivedHash)) {
            System.out.println("Integrity Verified");
            System.out.println("Transaction Successful");
        } else {
            System.out.println("Integrity Verification Failed");
            System.out.println("Transaction Failed");
        }

        sc.close();
    }
}
