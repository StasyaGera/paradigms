package generic.wrapper;

import exceptions.CalcException;
import exceptions.OverflowException;

public class FloatWrapper extends Numeric<Float> {
    public FloatWrapper(Float value) {
        super(value);
    }
    public FloatWrapper(int value) {
        super((float) value);
    }

    @Override
    public Numeric<Float> add(Numeric<Float> number) throws OverflowException {
        return new FloatWrapper(this.value + number.value);
    }

    @Override
    public Numeric<Float> sub(Numeric<Float> number) throws OverflowException {
        return new FloatWrapper(this.value - number.value);
    }

    @Override
    public Numeric<Float> mul(Numeric<Float> number) throws OverflowException {
        return new FloatWrapper(this.value * number.value);
    }

    @Override
    public Numeric<Float> div(Numeric<Float> number) throws CalcException, OverflowException {
        return new FloatWrapper(this.value / number.value);
    }

    @Override
    public Numeric<Float> mod(Numeric<Float> number) {
        return new FloatWrapper(this.value % number.value);
    }

    @Override
    public Numeric<Float> strToNum(String input) throws OverflowException {
        return new FloatWrapper(Float.parseFloat(input));
    }

    @Override
    public Numeric<Float> create(int value) {
        return new FloatWrapper((float) value);
    }

    @Override
    public Numeric<Float> negate() throws OverflowException {
        return new FloatWrapper(-this.value);
    }

    @Override
    public int compareTo(Numeric<Float> number) {
        return this.value.compareTo(number.value);
    }
}
