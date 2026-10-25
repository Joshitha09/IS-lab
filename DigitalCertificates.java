
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hybrid Encryption using RSA and AES");
        System.out.print("Enter message: ");
        String message = sc.nextLine();

        // Generate RSA key pair
        KeyPairGenerator rsaGen = KeyPairGenerator.getInstance("RSA");
        rsaGen.initialize(2048);
        KeyPair rsaPair = rsaGen.generateKeyPair();

        // Generate AES key
        KeyGenerator ag = KeyGenerator.getInstance("AES");
        ag.init(256);
        SecretKey key = ag.generateKey();

        // Generate IV
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        // AES encryption
        Cipher aes = Cipher.getInstance("AES/GCM/NoPadding");
        aes.init(Cipher.ENCRYPT_MODE, key,
                new GCMParameterSpec(128, iv));
        byte[] ct = aes.doFinal(message.getBytes(StandardCharsets.UTF_8));

        // RSA encryption of AES key
        Cipher rsa = Cipher.getInstance(
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        rsa.init(Cipher.ENCRYPT_MODE, rsaPair.getPublic());
        byte[] ek = rsa.doFinal(key.getEncoded());

        System.out.println("Encrypted Message: "
                + Base64.getEncoder().encodeToString(ct));
        System.out.println("Encrypted AES Key: "
                + Base64.getEncoder().encodeToString(ek));

        // RSA decryption of AES key
        rsa.init(Cipher.DECRYPT_MODE, rsaPair.getPrivate());
        SecretKey dk = new SecretKeySpec(rsa.doFinal(ek), "AES");

        // AES decryption
        aes.init(Cipher.DECRYPT_MODE, dk,
                new GCMParameterSpec(128, iv));
        String result = new String(
                aes.doFinal(ct), StandardCharsets.UTF_8);

        System.out.println("Decrypted Message: " + result);
        System.out.println("Hybrid Encryption Successful");

        sc.close();
    }
}
