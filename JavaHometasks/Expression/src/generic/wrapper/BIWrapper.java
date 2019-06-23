package generic.wrapper;

import java.math.BigInteger;

public class BIWrapper extends Numeric<BigInteger> {
    public BIWrapper(BigInteger value) {
        super(value);
    }
    public BIWrapper(Integer value) {
        super(BigInteger.valueOf(value));
    }

    public Numeric<BigInteger> strToNum(String input) {
        int i = 0;
        BigInteger value = BigInteger.ZERO;
        boolean negative = false;

        while ((i < input.length()) && (input.charAt(i) == '-')) {
            negative = !negative;
            i++;
        }

        while (i < input.length()) {
            value = value.multiply(BigInteger.TEN).subtract(BigInteger.valueOf(Character.getNumericValue(input.charAt(i++))));
        }

        if (negative) return new BIWrapper(value);
        return new BIWrapper(value.negate());
    }

    @Override
    public Numeric<BigInteger> create(int value) {
        return new BIWrapper(value);
    }

    @Override
    public Numeric<BigInteger> mod(Numeric<BigInteger> number) {
        return new BIWrapper(value.mod(number.value));
    }

    @Override
    public Numeric<BigInteger> add(Numeric<BigInteger> number) {
        return new BIWrapper(this.value.add(number.value));
    }

    @Override
    public Numeric<BigInteger> sub(Numeric<BigInteger> number) {
        return new BIWrapper(this.value.subtract(number.value));
    }

    @Override
    public Numeric<BigInteger> mul(Numeric<BigInteger> number) {
        return new BIWrapper(this.value.multiply(number.value));
    }

    @Override
    public Numeric<BigInteger> div(Numeric<BigInteger> number) {
        return new BIWrapper(this.value.divide(number.value));
    }

    @Override
    public Numeric<BigInteger> negate() {
        return new BIWrapper(value.negate());
    }

    @Override
    public int compareTo(Numeric<BigInteger> number) {
        return value.compareTo(number.value);
    }
}