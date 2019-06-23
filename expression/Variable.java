package expression;

/**
 * Created by Penguinni on 14.03.2016.
 * Penguinni does not promise that this code's going to work.
 */

public class Variable implements TripleExpression {
    public Variable (String name) {
        this.name = name;
    }

    private final String name;

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return 0;
        }
    }
}
