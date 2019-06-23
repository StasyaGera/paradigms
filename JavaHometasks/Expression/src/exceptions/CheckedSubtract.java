package exceptions;

import base.BinaryOperation;
import base.TripleExpression;

public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) throws OverflowException {
        if (((a >= 0) && (b < 0) && (a > Integer.MAX_VALUE + b)) ||
                ((a < 0) && (b > 0) && (a < Integer.MIN_VALUE + b))) {
            throw new OverflowException(OverflowException.Operation.SUB);
        }
        return a - b;
    }
}