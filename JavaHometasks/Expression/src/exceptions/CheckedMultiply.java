package exceptions;

import base.TripleExpression;
import base.BinaryOperation;

public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) throws OverflowException {
        if (((a > 0) && (b > 0) && (a > Integer.MAX_VALUE / b)) ||
                ((a < 0) && (b < 0) && (a < Integer.MAX_VALUE / b)) ||
                ((a < 0) && (b > 0) && (a < Integer.MIN_VALUE / b)) ||
                ((a > 0) && (b < 0) && (b != -1) && (a > Integer.MIN_VALUE / b))) {
            throw new OverflowException(OverflowException.Operation.MUL);
        }
        return a * b;
    }
}