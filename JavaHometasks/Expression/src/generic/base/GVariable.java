package generic.base;

import generic.wrapper.Numeric;

public class GVariable<T extends Number> implements GExpression<T>{
    private final String name;

    public GVariable (String name) {
        this.name = name;
    }

    public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return null;
        }
    }
}
