
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {
    static SecureRandom random = new SecureRandom();

    static BigInteger encrypt(int bit, BigInteger n, BigInteger z) {
        BigInteger r;

        do {
            r = new BigInteger(n.bitLength(), random).mod(n);
        } while (r.equals(BigInteger.ZERO)
                || !r.gcd(n).equals(BigInteger.ONE));

        BigInteger c = r.multiply(r).mod(n);

        if (bit == 1)
            c = c.multiply(z).mod(n);

        return c;
    }

    static int decrypt(BigInteger c, BigInteger p, BigInteger q) {
        BigInteger result = c.mod(p);
        BigInteger exp = p.subtract(BigInteger.ONE)
                .divide(BigInteger.TWO);

        return result.modPow(exp, p).equals(BigInteger.ONE) ? 0 : 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BigInteger p = BigInteger.valueOf(7);
        BigInteger q = BigInteger.valueOf(11);
        BigInteger n = p.multiply(q);
        BigInteger z = BigInteger.valueOf(2);

        System.out.println("Goldwasser-Micali Cryptosystem");
        System.out.println("Public Key (n, z): (" + n + ", " + z + ")");
        System.out.println("Private Key (p, q): (" + p + ", " + q + ")");

        System.out.print("Enter a binary message: ");
        String message = sc.nextLine();

        StringBuilder cipher = new StringBuilder();

        System.out.print("Encrypted message: ");

        for (char ch : message.toCharArray()) {
            if (ch != '0' && ch != '1') {
                System.out.println("\nEnter only 0s and 1s.");
                sc.close();
                return;
            }

            BigInteger c = encrypt(ch - '0', n, z);
            cipher.append(c).append(" ");
            System.out.print(c + " ");
        }

        System.out.println();
        System.out.print("Decrypted message: ");

        for (String value : cipher.toString().trim().split("\\s+")) {
            BigInteger c = new BigInteger(value);
            System.out.print(decrypt(c, p, q));
        }

        System.out.println();
        sc.close();
    }
}
