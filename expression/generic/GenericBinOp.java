package expression.generic;

/**
 * Created by penguinni on 14.04.16.
 * penguinni hopes it will work.
 */

public abstract class GenericBinOp<Numeric> implements GenericExpression<Numeric> {
    private final GenericExpression<Numeric> first;
    private final GenericExpression<Numeric> second;

    protected GenericBinOp(GenericExpression<Numeric> first, GenericExpression<Numeric> second) {
        this.first = first;
        this.second = second;
    }

    protected abstract Numeric calc(Numeric a, Numeric b);


    public Numeric evaluate(int x, int y, int z) {
        return calc(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

}
