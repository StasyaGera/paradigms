package expression;

/**
 * Created by Penguinni on 14.03.2016.
 * Penguinni does not promise that this code's going to work.
 */
public class Multiply extends BinaryOperation {
    public Multiply (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) {
        return a * b;
    }
}