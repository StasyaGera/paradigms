import base.Const;
import base.Multiply;
import base.Subtract;
import base.Variable;

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
