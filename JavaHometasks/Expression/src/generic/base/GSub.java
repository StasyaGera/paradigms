package generic.base;

import exceptions.OverflowException;
import generic.wrapper.Numeric;

public class GSub<T extends Number> extends GBinaryOperation<T> {
    public GSub(GExpression<T> first, GExpression<T> second, String type) {
        super(first, second);
    }

    protected Numeric<T> calc(Numeric<T> a, Numeric<T> b) throws OverflowException {
        return a.sub(b);
    }
}
