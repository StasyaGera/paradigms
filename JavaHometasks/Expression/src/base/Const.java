package base;

public class Const implements TripleExpression {
    public Const (int value) {
        this.value = value;
    }

    private final int value;

    public int evaluate(int x, int y, int z) {
        return value;
    }
}
