package generic.base;

import exceptions.CalcException;
import exceptions.OverflowException;
import generic.wrapper.Numeric;

public abstract class GBinaryOperation<T extends Number> implements GExpression<T> {
    private final GExpression<T> first;
    private final GExpression<T> second;

    protected GBinaryOperation(GExpression<T> first, GExpression<T> second) {
        this.first = first;
        this.second = second;
    }

    protected abstract Numeric<T> calc(Numeric<T> a, Numeric<T> b) throws OverflowException, CalcException;

    public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) throws OverflowException, CalcException {
        return calc(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

}
