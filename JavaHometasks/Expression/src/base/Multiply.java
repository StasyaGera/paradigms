package base;

public class Multiply extends BinaryOperation {
    public Multiply (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) {
        return a * b;
    }
}
