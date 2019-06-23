package base;

public class Add extends BinaryOperation {
    public Add (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) {
        return a + b;
    }
}
