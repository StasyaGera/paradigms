package generic.base;

import exceptions.CalcException;
import exceptions.OverflowException;
import generic.wrapper.Numeric;

public interface GExpression<T extends Number> {
     Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) throws OverflowException, CalcException;
}
