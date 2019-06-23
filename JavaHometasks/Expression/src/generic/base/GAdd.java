package generic.base;

import exceptions.OverflowException;
import generic.wrapper.Numeric;

public class GAdd<T extends Number> extends GBinaryOperation<T> {
    public GAdd(GExpression<T> first, GExpression<T> second) {
        super(first, second);
    }

    protected Numeric<T> calc(Numeric<T> a, Numeric<T> b) throws OverflowException {
        return a.add(b);
    }
}
