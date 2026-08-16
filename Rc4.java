import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Rc4 {
    private final int[] S = new int[256];

    private void ksa(byte[] key) {
        for (int i = 0; i < 256; i++)
            S[i] = i;

        for (int i = 0, j = 0; i < 256; i++) {
            j = (j + S[i] + (key[i % key.length] & 0xFF)) & 0xFF;
            swap(i, j);
        }
    }

    private int prga(int[] idx) {
        idx[0] = (idx[0] + 1) & 0xFF;
        idx[1] = (idx[1] + S[idx[0]]) & 0xFF;
        swap(idx[0], idx[1]);
        return S[(S[idx[0]] + S[idx[1]]) & 0xFF];
    }

    public byte[] crypt(byte[] key, byte[] input) {
        ksa(key);
        byte[] output = new byte[input.length];
        int[] idx = {0, 0};

        for (int i = 0; i < input.length; i++)
            output[i] = (byte) (input[i] ^ prga(idx));

        return output;
    }

    private void swap(int i, int j) {
        int temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02X ", b));
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomRC4 rc4 = new CustomRC4();

        System.out.println("=== RC4 Custom Encryption Tool ===");

        System.out.print("Enter Secret Key: ");
        byte[] key = sc.nextLine().getBytes(StandardCharsets.UTF_8);

        System.out.print("Enter Plaintext to encrypt: ");
        byte[] plain = sc.nextLine().getBytes(StandardCharsets.UTF_8);

        byte[] cipher = rc4.crypt(key, plain);

        System.out.println("\nEncrypted (Hex): " + bytesToHex(cipher));

        byte[] decrypted = rc4.crypt(key, cipher);

        System.out.println("Decrypted Text : " +
                new String(decrypted, StandardCharsets.UTF_8));

        sc.close();
    }
}