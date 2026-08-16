import java.util.*;

public class Subset {
    static boolean[][] dp;

    static void print(int[] a, int i, int sum, ArrayList<Integer> list) {
        if (sum == 0) {
            Collections.reverse(list);
            System.out.println("Subset: " + list);
            Collections.reverse(list);
            return;
        }

        if (i == 0)
            return;

        if (dp[i - 1][sum])
            print(a, i - 1, sum, new ArrayList<>(list));

        if (sum >= a[i - 1] && dp[i - 1][sum - a[i - 1]]) {
            list.add(a[i - 1]);
            print(a, i - 1, sum - a[i - 1], list);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of public key values: ");
        int n = sc.nextInt();

        int[] key = new int[n];

        System.out.println("Enter public key values:");
        for (int i = 0; i < n; i++)
            key[i] = sc.nextInt();

        System.out.print("Enter cipher value: ");
        int target = sc.nextInt();

        dp = new boolean[n + 1][target + 1];

        for (int i = 0; i <= n; i++)
            dp[i][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                dp[i][j] = (key[i - 1] <= j)
                        ? dp[i - 1][j] || dp[i - 1][j - key[i - 1]]
                        : dp[i - 1][j];
            }
        }

        if (dp[n][target]) {
            System.out.println("Cipher Verified");
            print(key, n, target, new ArrayList<>());
        } else {
            System.out.println("Invalid Cipher");
        }

        sc.close();
    }
}