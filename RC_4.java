public class RC_4 {

    static byte[] rc4(byte[] data, String key) {

        int[] S = new int[256];
        int j = 0;

        // KSA
        for (int i = 0; i < 256; i++)
            S[i] = i;

        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + key.charAt(i % key.length())) % 256;

            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }

        // PRGA
        byte[] result = new byte[data.length];

        int i = 0;
        j = 0;

        for (int k = 0; k < data.length; k++) {

            i = (i + 1) % 256;
            j = (j + S[i]) % 256;

            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;

            int t = (S[i] + S[j]) % 256;
            int keyByte = S[t];

            result[k] = (byte)(data[k] ^ keyByte);
        }

        return result;
    }

    public static void main(String[] args) {

        String text = "Hello";
        String key = "secret";

        byte[] encrypted = rc4(text.getBytes(), key);

        System.out.println("Encrypted: " +
                java.util.Base64.getEncoder().encodeToString(encrypted));

        byte[] decrypted = rc4(encrypted, key);

        System.out.println("Decrypted: " +
                new String(decrypted));
    }
}