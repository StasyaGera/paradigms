package generic.base;

import generic.wrapper.Numeric;

public class GConst<T extends Number> implements GExpression<T>{
    public GConst (Numeric<T> value) {
        this.value = value;
    }

    private final Numeric<T> value;

    public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) {
        return value;
    }
}
