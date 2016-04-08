package expression;

import expression.exceptions.CalcException;
import expression.exceptions.OverflowException;

/**
 * Created by penguinni on 24.03.16.
 */

public interface TripleExpression {
    int evaluate(int x, int y, int z) throws OverflowException, CalcException;
}
