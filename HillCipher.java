
import java.util.Scanner;

public class HillCipher {

    static int modInverse(int a) {
        a = (a % 26 + 26) % 26;

        for (int i = 1; i < 26; i++) {
            if ((a * i) % 26 == 1)
                return i;
        }
        return -1;
    }

    static String process(String text, int[][] k) {
        String result = "";

        text = text.toUpperCase().replaceAll("[^A-Z]", "");

        if (text.length() % 2 != 0)
            text += "X";

        for (int i = 0; i < text.length(); i += 2) {
            int a = text.charAt(i) - 'A';
            int b = text.charAt(i + 1) - 'A';

            int x = Math.floorMod(
                k[0][0] * a + k[0][1] * b, 26);

            int y = Math.floorMod(
                k[1][0] * a + k[1][1] * b, 26);

            result += (char) (x + 'A');
            result += (char) (y + 'A');
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String text = sc.nextLine();

        int[][] k = new int[2][2];

        System.out.println("Enter 2x2 key matrix:");

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                k[i][j] = sc.nextInt();

        int det = Math.floorMod(
            k[0][0] * k[1][1] - k[0][1] * k[1][0], 26);

        int dInv = modInverse(det);

        if (dInv == -1) {
            System.out.println("Invalid key matrix!");
            sc.close();
            return;
        }

        int[][] inv = {
            {
                Math.floorMod(dInv * k[1][1], 26),
                Math.floorMod(-dInv * k[0][1], 26)
            },
            {
                Math.floorMod(-dInv * k[1][0], 26),
                Math.floorMod(dInv * k[0][0], 26)
            }
        };

        String encrypted = process(text, k);
        String decrypted = process(encrypted, inv);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        sc.close();
    }
}
