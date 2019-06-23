package exceptions;

import base.BinaryOperation;
import base.Const;
import base.TripleExpression;

public class CheckedPower extends BinaryOperation {
    public CheckedPower (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) throws CalcException, OverflowException {
        if (a == 0 && b == 0) {
            throw new CalcException(CalcException.Operation.POW, "base and exponent equal zero");
        }
        if (b < 0) {
            throw new CalcException(CalcException.Operation.POW, "base under zero");
        }

        int answer = 1;
        while (true) {
            if (b % 2 == 1) {
                try {
                    answer = new CheckedMultiply(new Const(answer), new Const(a)).evaluate(0, 0, 0);
                } catch (OverflowException e) {
                    throw new OverflowException(OverflowException.Operation.POW);
                }
            }
            b /= 2;
            if (b == 0) {
                break;
            }
            try {
                a = new CheckedMultiply(new Const(a), new Const(a)).evaluate(0, 0, 0);
            } catch (OverflowException e) {
                throw new OverflowException(OverflowException.Operation.POW);
            }
        }

        return answer;
    }
}
