package base;

public class Subtract extends BinaryOperation {
    public Subtract (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) {
        return a - b;
    }
}
