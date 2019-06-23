package generic.base;

import exceptions.CalcException;
import exceptions.OverflowException;
import generic.wrapper.Numeric;

public class GDiv<T extends Number> extends GBinaryOperation<T> {
    public GDiv(GExpression<T> first, GExpression<T> second, String type) {
        super(first, second);
    }

    protected Numeric<T> calc(Numeric<T> a, Numeric<T> b) throws CalcException, OverflowException {
        return a.div(b);
    }
}
