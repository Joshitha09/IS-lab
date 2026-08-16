import java.util.Random;
import java.util.Scanner;

public class random {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        System.out.print("Enter the length of the random string: ");
        int length = sc.nextInt();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            result.append(chars.charAt(index));
        }

        System.out.println("Random String: " + result);

        sc.close();
    }
}