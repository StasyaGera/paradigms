package expression;

import expression.exceptions.CalcException;
import expression.exceptions.OverflowException;

/**
 * Created by Penguinni on 14.03.2016.
 * Penguinni does not promise that this code's going to work.
 */

public abstract class BinaryOperation implements TripleExpression {
    private final TripleExpression first;
    private final TripleExpression second;

    protected BinaryOperation(TripleExpression first, TripleExpression second) {
        this.first = first;
        this.second = second;
    }

    protected abstract int calc(int a, int b) throws OverflowException, CalcException;

    public int evaluate(int x, int y, int z) throws OverflowException, CalcException {
        return calc(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
