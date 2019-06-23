package generic.wrapper;

import exceptions.CalcException;
import exceptions.OverflowException;

public abstract class Numeric<T extends Number> {
    public final T value;

    public Numeric(T value) {
        this.value = value;
    }

    public abstract Numeric<T> add(Numeric<T> number) throws OverflowException;
    public abstract Numeric<T> sub(Numeric<T> number) throws OverflowException;
    public abstract Numeric<T> mul(Numeric<T> number) throws OverflowException;
    public abstract Numeric<T> div(Numeric<T> number) throws CalcException, OverflowException;
    public abstract Numeric<T> mod(Numeric<T> number);
    public abstract Numeric<T> strToNum(String input) throws OverflowException;
    public abstract Numeric<T> create(int value);

    public abstract Numeric<T> negate() throws OverflowException;
    public abstract int compareTo(Numeric<T> number);
}
