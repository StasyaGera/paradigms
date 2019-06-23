package expression.generic;

/**
 * Created by penguinni on 14.04.16.
 * penguinni hopes it will work.
 */

public class DoubleWrapper implements Numeric<Double> {
    Double value;

    @Override
    public Double add(Double summand) {
        return value + summand;
    }

    @Override
    public Double sub(Double subtrahend) {
        return value - subtrahend;
    }

    @Override
    public Double div(Double factor) {
        return value / factor;
    }

    @Override
    public Double mul(Double divider) {
        return value * divider;
    }

}
