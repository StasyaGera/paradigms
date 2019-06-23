package base;

public class Divide extends BinaryOperation {
    public Divide (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) {
        return a / b;
    }
}
