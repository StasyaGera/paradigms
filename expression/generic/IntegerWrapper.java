package expression.generic;

/**
 * Created by penguinni on 14.04.16.
 * penguinni hopes it will work.
 */

public class IntegerWrapper implements Numeric<Integer> {
    Integer value;

    @Override
    public Integer add(Integer summand) {
        return value + summand;
    }

    @Override
    public Integer sub(Integer subtrahend) {
        return value - subtrahend;
    }

    @Override
    public Integer div(Integer factor) {
        return value / factor;
    }

    @Override
    public Integer mul(Integer divider) {
        return value * divider;
    }

}