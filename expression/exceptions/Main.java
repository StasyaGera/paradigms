package expression.exceptions;

/**
 * Created by penguinni on 30.03.16.
 */

public class Main {
    public static void main(String args[]) throws Exception {
        String str = "5 ** //-1";
        System.out.print(new ExpressionParser().parse(str).evaluate(0, 0, 0) + "\n");
    }
}
