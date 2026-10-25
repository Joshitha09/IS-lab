
import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a message: ");
        String text = sc.nextLine();

        System.out.print("Enter key: ");
        int key = sc.nextInt();

        key = ((key % 26) + 26) % 26;

        String encrypted = "";
        String decrypted = "";

        // Encryption
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch >= 'A' && ch <= 'Z') {
                encrypted += (char) ((ch - 'A' + key) % 26 + 'A');
            } else if (ch >= 'a' && ch <= 'z') {
                encrypted += (char) ((ch - 'a' + key) % 26 + 'a');
            } else {
                encrypted += ch;
            }
        }

        // Decryption
        for (int i = 0; i < encrypted.length(); i++) {
            char ch = encrypted.charAt(i);

            if (ch >= 'A' && ch <= 'Z') {
                decrypted += (char) ((ch - 'A' - key + 26) % 26 + 'A');
            } else if (ch >= 'a' && ch <= 'z') {
                decrypted += (char) ((ch - 'a' - key + 26) % 26 + 'a');
            } else {
                decrypted += ch;
            }
        }

        System.out.println("Encrypted message: " + encrypted);
        System.out.println("Decrypted message: " + decrypted);

        sc.close();
    }
}
