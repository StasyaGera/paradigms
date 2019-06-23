package generic.wrapper;

import exceptions.CalcException;
import exceptions.OverflowException;

public class UIntegerWrapper extends Numeric<Integer> {
    public UIntegerWrapper(Integer value) {
        super(value);
    }
    
    @Override
    public Numeric<Integer> add(Numeric<Integer> number) throws OverflowException {
        return new UIntegerWrapper(this.value + number.value);
    }

    @Override
    public Numeric<Integer> sub(Numeric<Integer> number) throws OverflowException {
        return new UIntegerWrapper(this.value - number.value);
    }

    @Override
    public Numeric<Integer> mul(Numeric<Integer> number) throws OverflowException {
        return new UIntegerWrapper(this.value * number.value);
    }

    @Override
    public Numeric<Integer> div(Numeric<Integer> number) throws CalcException, OverflowException {
        return new UIntegerWrapper(this.value / number.value);
    }

    @Override
    public Numeric<Integer> mod(Numeric<Integer> number) {
        return new UIntegerWrapper(this.value % number.value);
    }

    @Override
    public Numeric<Integer> strToNum(String input) throws OverflowException {
        return new UIntegerWrapper(Integer.parseInt(input));
    }

    @Override
    public Numeric<Integer> create(int value) {
        return new UIntegerWrapper(value);
    }

    @Override
    public Numeric<Integer> negate() throws OverflowException {
        return new UIntegerWrapper(-this.value);
    }

    @Override
    public int compareTo(Numeric<Integer> number) {
        return this.value.compareTo(number.value);
    }
}
