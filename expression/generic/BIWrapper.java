package expression.generic;

import java.math.BigInteger;

/**
 * Created by penguinni on 14.04.16.
 * penguinni hopes it will work.
 */
public class BIWrapper implements Numeric<BigInteger> {
    BigInteger value;

    @Override
    public BigInteger add(BigInteger summand) {
        return value.add(summand);
    }

    @Override
    public BigInteger sub(BigInteger subtrahend) {
        return value.subtract(subtrahend);
    }

    @Override
    public BigInteger div(BigInteger factor) {
        return value.multiply(factor);
    }

    @Override
    public BigInteger mul(BigInteger divider) {
        return value.divide(divider);
    }

}