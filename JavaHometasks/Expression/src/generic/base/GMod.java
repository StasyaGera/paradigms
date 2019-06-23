package generic.base;

import exceptions.CalcException;
import exceptions.OverflowException;
import generic.wrapper.Numeric;

public class GMod<T extends Number> extends GBinaryOperation<T> {
    private GExpression<T> operand;

    public GMod(GExpression<T> first, GExpression<T> second) {
        super(first, second);
    }


    @Override
    protected Numeric<T> calc(Numeric<T> a, Numeric<T> b) throws OverflowException, CalcException {
        return a.mod(b);
    }
}
