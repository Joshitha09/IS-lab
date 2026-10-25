
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    static BigInteger p = BigInteger.valueOf(7);
    static BigInteger q = BigInteger.valueOf(11);
    static BigInteger n = p.multiply(q);

    static BigInteger encrypt(BigInteger m) {
        return m.multiply(m).mod(n);
    }

    static BigInteger[] decrypt(BigInteger c) {
        BigInteger mp = c.mod(p).modPow(
            p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);

        BigInteger mq = c.mod(q).modPow(
            q.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), q);

        BigInteger yp = q.modInverse(p);
        BigInteger yq = p.modInverse(q);

        BigInteger r1 = yp.multiply(q).multiply(mp)
            .add(yq.multiply(p).multiply(mq)).mod(n);

        BigInteger r2 = yp.multiply(q).multiply(mp)
            .subtract(yq.multiply(p).multiply(mq)).mod(n);

        BigInteger r3 = n.subtract(r1).mod(n);
        BigInteger r4 = n.subtract(r2).mod(n);

        return new BigInteger[]{r1, r2, r3, r4};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Rabin Cryptosystem");
        System.out.println("Public Key n = " + n);
        System.out.println("Private Key p = " + p + ", q = " + q);
        System.out.print("Enter plaintext (0 to 76): ");

        BigInteger m = sc.nextBigInteger();

        if (m.signum() < 0 || m.compareTo(n) >= 0) {
            System.out.println("Plaintext must be between 0 and 76.");
            sc.close();
            return;
        }

        BigInteger c = encrypt(m);
        System.out.println("Encrypted Ciphertext: " + c);

        BigInteger[] roots = decrypt(c);
        System.out.println("Possible plaintexts:");

        for (BigInteger root : roots) {
            System.out.println(root);
        }

        System.out.println("Original plaintext: " + m);
        System.out.println("Decryption successful.");

        sc.close();
    }
}
