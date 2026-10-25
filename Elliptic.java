
import javax.crypto.KeyAgreement;
import java.security.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(256);

        KeyPair alice = kpg.generateKeyPair();
        KeyPair bob = kpg.generateKeyPair();

        KeyAgreement ka = KeyAgreement.getInstance("ECDH");
        ka.init(alice.getPrivate());
        ka.doPhase(bob.getPublic(), true);
        byte[] aliceSecret = ka.generateSecret();

        KeyAgreement kb = KeyAgreement.getInstance("ECDH");
        kb.init(bob.getPrivate());
        kb.doPhase(alice.getPublic(), true);
        byte[] bobSecret = kb.generateSecret();

        System.out.println("Alice Shared Secret: "
                + Arrays.toString(aliceSecret));

        System.out.println("Bob Shared Secret: "
                + Arrays.toString(bobSecret));

        if (Arrays.equals(aliceSecret, bobSecret)) {
            System.out.println(
                    "Shared Secret Successfully Established!");
        } else {
            System.out.println(
                    "Shared Secret Establishment Failed!");
        }
    }
}
