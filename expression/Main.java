package expression;

/**
 * Created by Penguinni on 14.03.2016.
 * Penguinni does not promise that this code's going to work.
 */

public class Main {
    public static void main(String args[]) throws Exception {
        System.out.print(
        new Subtract(
                new Multiply(
                        new Const(2),
                        new Variable("x")
                ),
                new Variable("y")
        ).evaluate(5, 4, 3)
        );
    }
}
