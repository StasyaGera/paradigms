package exceptions;

import base.BinaryOperation;
import base.TripleExpression;

public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) throws CalcException, OverflowException {
        if (b == 0) {
            throw new CalcException(CalcException.Operation.DIV, "division by zero");
        } else if ((a == Integer.MIN_VALUE) && (b == -1)) {
            throw new OverflowException(OverflowException.Operation.DIV);
        }

        return a / b;
    }
}
