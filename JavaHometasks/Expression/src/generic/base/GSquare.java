package generic.base;

import exceptions.CalcException;
import exceptions.OverflowException;
import generic.wrapper.Numeric;

public class GSquare<T extends Number> implements GExpression<T> {
    private GExpression<T> operand;

    public GSquare(GExpression<T> operand) {
        this.operand = operand;
    }

    @Override
    public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) throws OverflowException, CalcException {
        Numeric<T> result = operand.evaluate(x, y, z);

        return result.mul(result);
    }
}
