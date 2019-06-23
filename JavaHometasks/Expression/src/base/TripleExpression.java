package base;

import exceptions.CalcException;
import exceptions.OverflowException;

public interface TripleExpression {
    int evaluate(int x, int y, int z) throws OverflowException, CalcException;
}
