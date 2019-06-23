package expression.generic;

/**
 * Created by penguinni on 14.04.16.
 * penguinni hopes it will work.
 */

public class GenericAdd<Numeric> extends GenericBinOp<Numeric> {
    public GenericAdd(GenericExpression<Numeric> first, GenericExpression<Numeric> second) {
        super(first, second);
    }

    protected Numeric calc(Numeric a, Numeric b) {
        return a;
    }
}
