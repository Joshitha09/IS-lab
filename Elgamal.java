import java.math.BigInteger;
import java.util.*;

public class ElGamal {

    static List<BigInteger> factors(BigInteger n) {
        List<BigInteger> f = new ArrayList<>();

        for (BigInteger i = BigInteger.TWO;
             i.multiply(i).compareTo(n) <= 0;
             i = i.add(BigInteger.ONE)) {

            if (n.mod(i).equals(BigInteger.ZERO)) {
                f.add(i);

                while (n.mod(i).equals(BigInteger.ZERO))
                    n = n.divide(i);
            }
        }

        if (n.compareTo(BigInteger.ONE) > 0)
            f.add(n);

        return f;
    }

    static BigInteger primitiveRoot(BigInteger p) {
        BigInteger phi = p.subtract(BigInteger.ONE);
        List<BigInteger> f = factors(phi);

        for (BigInteger e1 = BigInteger.TWO;
             e1.compareTo(p) < 0;
             e1 = e1.add(BigInteger.ONE)) {

            boolean flag = true;

            for (BigInteger x : f) {
                if (e1.modPow(phi.divide(x), p)
                        .equals(BigInteger.ONE)) {
                    flag = false;
                    break;
                }
            }

            if (flag)
                return e1;
        }

        return BigInteger.TWO;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter prime p: ");
        BigInteger p = sc.nextBigInteger();

        BigInteger e1 = primitiveRoot(p);

        System.out.println("Primitive Root e1: " + e1);

        System.out.print("Enter private key d: ");
        BigInteger d = sc.nextBigInteger();

        BigInteger e2 = e1.modPow(d, p);

        System.out.println("\nPublic Key: ");
        System.out.println("(e1 = " + e1 + ", e2 = " + e2 + ", p = " + p + ")");
        System.out.println("Private Key: " + d);

        sc.nextLine();

        System.out.print("\nEnter message: ");
        String msg = sc.nextLine();

        BigInteger m = new BigInteger(msg.getBytes());

        System.out.print("Enter random key r: ");
        BigInteger r = sc.nextBigInteger();

        BigInteger c1 = e1.modPow(r, p);
        BigInteger c2 = m.multiply(e2.modPow(r, p)).mod(p);

        System.out.println("\nCipher Text:");
        System.out.println("c1 = " + c1);
        System.out.println("c2 = " + c2);

        BigInteger secret = c1.modPow(d, p);

        BigInteger decrypted =
                c2.multiply(secret.modInverse(p)).mod(p);

        System.out.println("\nDecrypted Message: " +
                new String(decrypted.toByteArray()));

        sc.close();
    }
}
