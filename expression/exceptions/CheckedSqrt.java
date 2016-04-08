package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by penguinni on 04.04.16.
 */

public class CheckedSqrt implements TripleExpression {
    private TripleExpression operand;
    private final int MAX = 46341;

    public CheckedSqrt(TripleExpression operand) {
        this.operand = operand;
    }

    public int evaluate(int x, int y, int z) throws OverflowException, CalcException {
        int value = operand.evaluate(x, y, z);

        if (value < 0) {
            throw new CalcException(CalcException.Operation.SQRT, "taking root of value under zero");
        }

        if (value == 1) {
            return 1;
        }

        int l = 0, r = (value < MAX ? value : MAX), m;

        while (r - l > 1) {
            m = (r + l) / 2;
            if (m * m > value) {
                r = m;
            } else {
                l = m;
            }
        }

        return l;
    }
}
