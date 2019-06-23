/**
 * Created by Стася on 26.02.2016.
 */
import javax.xml.bind.DatatypeConverter;
import java.nio.file.*;
import java.security.MessageDigest;

public class SHA256Sum {
    public static void main(String[] args) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        for (String name : args) {
            md.reset();
            byte[] result = md.digest(Files.readAllBytes(Paths.get(name)));
            System.out.println(DatatypeConverter.printHexBinary(result) + " *" + name);
        }

        md.reset();
        if (args.length == 0) {
            int n;
            while ((n = System.in.read()) != -1) {
                md.update((byte) n);
            }
            byte[] result = md.digest();
            System.out.println(DatatypeConverter.printHexBinary(result) + " *-");
        }
    }
}
