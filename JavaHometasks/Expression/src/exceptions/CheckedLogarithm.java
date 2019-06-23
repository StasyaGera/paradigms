package exceptions;

import base.BinaryOperation;
import base.TripleExpression;

public class CheckedLogarithm extends BinaryOperation {
    public CheckedLogarithm (TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int a, int b) throws CalcException {
        if (b == 1) {
            throw new CalcException(CalcException.Operation.LOG, "logarithm base is 1");
        }
        if (b <= 0) {
            throw new CalcException(CalcException.Operation.LOG, "logarithm base under zero");
        }
        if (a <= 0) {
            throw new CalcException(CalcException.Operation.LOG, "logarithm argument under zero");
        }

        int answer = 0;
        while (a >= b) {
            a /= b;
            answer++;
        }

        return answer;
    }
}
