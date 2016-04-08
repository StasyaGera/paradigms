package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by penguinni on 05.04.16.
 */

public class CheckedAbsolute implements TripleExpression {
    private TripleExpression operand;

    public CheckedAbsolute (TripleExpression operand) {
        this.operand = operand;
    }

    public int evaluate(int x, int y, int z) throws OverflowException, CalcException {
        int value = operand.evaluate(x, y, z);

        if (value == Integer.MIN_VALUE) {
            throw new OverflowException(OverflowException.Operation.ABS);
        }

        if (value < 0) return -value;
        return value;
    }
}
