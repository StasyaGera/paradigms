package generic.wrapper;

public class DoubleWrapper extends Numeric<Double> {
    public DoubleWrapper(Double value) {
        super(value);
    }
    public DoubleWrapper(int value) {
        super((double) value);
    }

    public Numeric<Double> strToNum(String input) {
        int i = 0;
        Double value = 0.0;
        boolean negative = false;

        while ((i < input.length()) && (input.charAt(i) == '-')) {
            negative = !negative;
            i++;
        }

        while (i < input.length()) {
            value = value * 10.0 - Character.getNumericValue(input.charAt(i++));
        }

        if (negative) return new DoubleWrapper(value);
        return new DoubleWrapper(-value);
    }

    @Override
    public Numeric<Double> create(int value) {
        return new DoubleWrapper(value);
    }

    @Override
    public Numeric<Double> add(Numeric<Double> number) {
        return new DoubleWrapper(this.value + number.value);
    }

    @Override
    public Numeric<Double> sub(Numeric<Double> number) {
        return new DoubleWrapper(this.value - number.value);
    }

    @Override
    public Numeric<Double> mul(Numeric<Double> number) {
        return new DoubleWrapper(this.value * number.value);
    }

    @Override
    public Numeric<Double> div(Numeric<Double> number) {
        return new DoubleWrapper(this.value / number.value);
    }

    @Override
    public Numeric<Double> mod(Numeric<Double> number) {
        return new DoubleWrapper(this.value % number.value);
    }

    @Override
    public Numeric<Double> negate() {
        return new DoubleWrapper(-value);
    }

    @Override
    public int compareTo(Numeric<Double> number) {
        return value.compareTo(number.value);
    }
}
