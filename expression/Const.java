package expression;

/**
 * Created by Penguinni on 14.03.2016.
 * Penguinni does not promise that this code's going to work.
 */

public class Const implements TripleExpression {
    public Const (int value) {
        this.value = value;
    }

    private final int value;

    public int evaluate(int x, int y, int z) {
        return value;
    }
}
