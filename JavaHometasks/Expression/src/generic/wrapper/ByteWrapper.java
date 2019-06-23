package generic.wrapper;

import exceptions.CalcException;
import exceptions.OverflowException;

public class ByteWrapper extends Numeric<Byte> {
    public ByteWrapper(Byte value) {
        super(value);
    }
    public ByteWrapper(int value) {
        super((byte) value);
    }

    @Override
    public Numeric<Byte> add(Numeric<Byte> number) throws OverflowException {
        return new ByteWrapper((byte) (this.value + number.value));
    }

    @Override
    public Numeric<Byte> sub(Numeric<Byte> number) throws OverflowException {
        return new ByteWrapper((byte) (this.value - number.value));
    }

    @Override
    public Numeric<Byte> mul(Numeric<Byte> number) throws OverflowException {
        return new ByteWrapper((byte) (this.value * number.value));
    }

    @Override
    public Numeric<Byte> div(Numeric<Byte> number) throws CalcException, OverflowException {
        return new ByteWrapper((byte) (this.value / number.value));
    }

    @Override
    public Numeric<Byte> mod(Numeric<Byte> number) {
        return new ByteWrapper((byte) (this.value % number.value));
    }

    @Override
    public Numeric<Byte> strToNum(String input) throws OverflowException {
        return new ByteWrapper(Byte.parseByte(input));
    }

    @Override
    public Numeric<Byte> create(int value) {
        return new ByteWrapper((byte) value);
    }

    @Override
    public Numeric<Byte> negate() throws OverflowException {
        return new ByteWrapper((byte) -this.value);
    }

    @Override
    public int compareTo(Numeric<Byte> number) {
        return this.value.compareTo(number.value);
    }
}
