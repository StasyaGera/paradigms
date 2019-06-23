package expression.exceptions;

import expression.BinaryOperation;
import expression.TripleExpression;

/**
 * Created by penguinni on 30.03.16.
 */

public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) throws OverflowException {

        if (((a > 0) && (b > 0) && (a > Integer.MAX_VALUE - b)) ||
                ((a < 0) && (b < 0) && (a < Integer.MIN_VALUE - b))) {
            throw new OverflowException(OverflowException.Operation.ADD);
        }

        return a + b;
    }
}
