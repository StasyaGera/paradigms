package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by penguinni on 28.03.16.
 */

public interface Parser {
    TripleExpression parse(String expression) throws Exception;
}
