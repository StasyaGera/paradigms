package generic.base;

import exceptions.OverflowException;
import generic.wrapper.Numeric;

public class GMul<T extends Number> extends GBinaryOperation<T> {
    public GMul(GExpression<T> first, GExpression<T> second, String type) {
        super(first, second);
    }

    protected Numeric<T> calc(Numeric<T> a, Numeric<T> b) throws OverflowException {
        return a.mul(b);
    }
}
