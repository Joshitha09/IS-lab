
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DES {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String text = sc.nextLine();

        System.out.print("Enter 8-character key: ");
        String key = sc.nextLine();

        if (key.length() != 8) {
            System.out.println("Key must contain 8 characters.");
            sc.close();
            return;
        }

        SecretKeySpec secretKey =
            new SecretKeySpec(key.getBytes("UTF-8"), "DES");

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // Encryption
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes =
            cipher.doFinal(text.getBytes("UTF-8"));

        String encrypted =
            Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println("Encrypted: " + encrypted);

        // Decryption
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes =
            cipher.doFinal(Base64.getDecoder().decode(encrypted));

        String decrypted = new String(decryptedBytes, "UTF-8");

        System.out.println("Decrypted: " + decrypted);

        sc.close();
    }
}
