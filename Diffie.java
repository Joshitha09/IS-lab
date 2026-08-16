import java.math.BigInteger;
import java.util.Scanner;

public class Diffie {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Diffie-Hellman Key Exchange ===");

        System.out.print("Enter prime q: ");
        BigInteger q = sc.nextBigInteger();

        System.out.print("Enter primitive root alpha: ");
        BigInteger alpha = sc.nextBigInteger();

        System.out.print("Enter Alice private key: ");
        BigInteger Xa = sc.nextBigInteger();

        System.out.print("Enter Bob private key: ");
        BigInteger Xb = sc.nextBigInteger();

        BigInteger Ya = alpha.modPow(Xa, q);
        BigInteger Yb = alpha.modPow(Xb, q);

        System.out.println("\nAlice Public Key: " + Ya);
        System.out.println("Bob Public Key: " + Yb);

        BigInteger Ka = Yb.modPow(Xa, q);
        BigInteger Kb = Ya.modPow(Xb, q);

        System.out.println("\nAlice Shared Key: " + Ka);
        System.out.println("Bob Shared Key: " + Kb);

        if (Ka.equals(Kb))
            System.out.println("Shared Key Generated Successfully.");
        else
            System.out.println("Key Generation Failed.");

        sc.close();
    }
}