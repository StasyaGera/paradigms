package expression.generic;

/**
 * Created by penguinni on 14.04.16.
 * penguinni hopes it will work.
 */

public interface Numeric<T> {
    T add(T summand);
    T sub(T summand);
    T div(T summand);
    T mul(T summand);
}
