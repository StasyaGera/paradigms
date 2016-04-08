package expression.exceptions;

/**
 * Created by penguinni on 30.03.16.
 */

public class Main {
    public static void main(String args[]) throws Exception {
        String str = "2 + 4 - (1 * 110) / x";
        System.out.print(new ExpressionParser().parse(str).evaluate(2, 1, 1) + "\n");
    }
}
