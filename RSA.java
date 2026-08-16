import java.util.Scanner;

public class RSA {
    static int findD(int e, int phi) {
        int d = 1;
        while ((d * e) % phi != 1)
            d++;
        return d;
    }

    static long modPower(long base, long exp, long mod) {
        long result = 1;
        base %= mod;

        while (exp > 0) {
            if (exp % 2 == 1)
                result = (result * base) % mod;

            base = (base * base) % mod;
            exp /= 2;
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter prime number p: ");
        int p = sc.nextInt();

        System.out.print("Enter prime number q: ");
        int q = sc.nextInt();

        int n = p * q;
        int phi = (p - 1) * (q - 1);

        System.out.print("Enter public key e: ");
        int e = sc.nextInt();

        int d = findD(e, phi);

        System.out.println("\nPublic Key = (" + e + ", " + n + ")");
        System.out.println("Private Key = (" + d + ", " + n + ")");

        sc.nextLine();

        System.out.print("\nEnter Plain Text: ");
        String msg = sc.nextLine();

        long[] signature = new long[msg.length()];

        for (int i = 0; i < msg.length(); i++)
            signature[i] = modPower(msg.charAt(i), d, n);

        System.out.print("\nDigital Signature: ");

        for (long s : signature)
            System.out.print(s + " ");

        StringBuilder recovered = new StringBuilder();

        for (long s : signature)
            recovered.append((char) modPower(s, e, n));

        System.out.println("\n\nRecovered Plain Text : " + recovered);

        if (msg.equals(recovered.toString()))
            System.out.println("Signature Verified Successfully.");
        else
            System.out.println("Signature Verification Failed.");

        sc.close();
    }
}