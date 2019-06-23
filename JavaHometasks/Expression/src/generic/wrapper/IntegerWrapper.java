package generic.wrapper;

import exceptions.CalcException;
import exceptions.OverflowException;

public class IntegerWrapper extends Numeric<Integer> {
    public IntegerWrapper(Integer value) {
        super(value);
    }

    public Numeric<Integer> strToNum(String input) throws OverflowException {
        int i = 0;
        Integer value = 0;
        boolean negative = false;

        while ((i < input.length()) && (input.charAt(i) == '-')) {
            negative = !negative;
            i++;
        }

        while (i < input.length()) {
            value = value * 10 - Character.getNumericValue(input.charAt(i++));
            if (value > 0.0) {
                throw new OverflowException(OverflowException.Operation.INPUT);
            }
        }

        if (negative) return new IntegerWrapper(value);
        return new IntegerWrapper(-value);
    }

    @Override
    public Numeric<Integer> create(int value) {
        return new IntegerWrapper(value);
    }

    @Override
    public Numeric<Integer> add(Numeric<Integer> number) throws OverflowException {
        if (((this.value > 0) && (number.value > 0) && (this.value > Integer.MAX_VALUE - number.value)) ||
                ((this.value < 0) && (number.value < 0) && (this.value < Integer.MIN_VALUE - number.value))) {
            throw new OverflowException(OverflowException.Operation.ADD);
        }
        return new IntegerWrapper(this.value + number.value);
    }

    @Override
    public Numeric<Integer> sub(Numeric<Integer> number) throws OverflowException {
        if (((this.value >= 0) && (number.value < 0) && (this.value > Integer.MAX_VALUE + number.value)) ||
                ((this.value < 0) && (number.value > 0) && (this.value < Integer.MIN_VALUE + number.value))) {
            throw new OverflowException(OverflowException.Operation.SUB);
        }
        return new IntegerWrapper(this.value - number.value);
    }

    @Override
    public Numeric<Integer> mul(Numeric<Integer> number) throws OverflowException {
        if (((this.value > 0) && (number.value > 0) && (this.value > Integer.MAX_VALUE / number.value)) ||
                ((this.value < 0) && (number.value < 0) && (this.value < Integer.MAX_VALUE / number.value)) ||
                ((this.value < 0) && (number.value > 0) && (this.value < Integer.MIN_VALUE / number.value)) ||
                ((this.value > 0) && (number.value < 0) && (number.value != -1) && (this.value > Integer.MIN_VALUE / number.value))) {
            throw new OverflowException(OverflowException.Operation.MUL);
        }
        return new IntegerWrapper(this.value * number.value);
    }

    @Override
    public Numeric<Integer> div(Numeric<Integer> number) throws CalcException, OverflowException {
        if (number.value == 0) {
            throw new CalcException(CalcException.Operation.DIV, "division by zero");
        } else if ((this.value == Integer.MIN_VALUE) && (number.value == -1)) {
            throw new OverflowException(OverflowException.Operation.DIV);
        }
        return new IntegerWrapper(this.value / number.value);
    }

    @Override
    public Numeric<Integer> mod(Numeric<Integer> number) {
        return new IntegerWrapper(this.value % number.value);
    }

    @Override
    public Numeric<Integer> negate() throws OverflowException {
        if (this.value == Integer.MIN_VALUE) {
            throw new OverflowException(OverflowException.Operation.NEG);
        }
        return new IntegerWrapper(-this.value);
    }

    @Override
    public int compareTo(Numeric<Integer> number) {
        return value.compareTo(number.value);
    }
}