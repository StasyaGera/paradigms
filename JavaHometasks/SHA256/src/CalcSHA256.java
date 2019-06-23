import java.nio.file.*;
import java.util.*;
import java.security.MessageDigest;

public class CalcSHA256 {
    public static void main(String[] args) throws Exception {
        List<String> nameList = Files.readAllLines(Paths.get(args[0]));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        for (String name : nameList) {
            byte[] result = md.digest(Files.readAllBytes(Paths.get(name)));
            System.out.println(print(result));
        }
    }

    public static String print(byte[] result) {
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        return String.valueOf(sb);
    }
}