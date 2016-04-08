package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by penguinni on 30.03.16.
 */

public class CheckedNegate implements TripleExpression {
    private TripleExpression operand;

    public CheckedNegate (TripleExpression operand) {
        this.operand = operand;
    }

    public int evaluate(int x, int y, int z) throws OverflowException, CalcException {
        if (operand.evaluate(x, y, z) == Integer.MIN_VALUE) {
            throw new OverflowException(OverflowException.Operation.NEG);
        }

        return -operand.evaluate(x, y, z);
    }
}
