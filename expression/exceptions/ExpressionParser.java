package expression.exceptions;

import expression.TripleExpression;
import expression.exceptions.ParserException.InputMismatch;
import expression.exceptions.Token.Lexem;
import static expression.exceptions.Token.*;

/**
 * Created by penguinni on 28.03.16.
 */

public class ExpressionParser implements Parser {
    static Tokenizer tokenizer;
    static Lexem previous;

    private Lexem current;
    private int parenCnt;

    private void checkNeighbourhood(Lexem left, Lexem right) throws ParserException {
        if ((isNumeric(left) || (left == Lexem.C_PAREN)) && (
                isNumeric(right) || right == Lexem.O_PAREN || isUnary(right)
        ) || left == Lexem.C_PAREN && (
                isNumeric(right) || isUnary(right)
        )
                ) {

            throw new ParserException(InputMismatch.NO_OPERATOR);
        }

        if ((left == Lexem.START) && isBinary(right)) {
            throw new ParserException(InputMismatch.NO_FIRST_ARGUMENT);
        }

        if (isBinary(left) || isUnary(left)) {
            if (isBinary(right) || right == Lexem.C_PAREN) {
                throw new ParserException(InputMismatch.NO_MID_ARGUMENT);
            }
            if (right == Lexem.END) {
                throw new ParserException(InputMismatch.NO_LAST_ARGUMENT);
            }
        }

        if (left == Lexem.O_PAREN && isBinary(right)) {
            throw new ParserException(InputMismatch.NO_MID_ARGUMENT);
        }
    }

    private void getNextToken() throws ParserException {
        previous = current;
        if (tokenizer.hasTokens) {
            current = tokenizer.nextToken();
            if ((current == Lexem.C_PAREN) && (parenCnt == 0)) {
                throw new ParserException(InputMismatch.NO_OPENING_PAREN);
            }
        } else {
            current = Lexem.END;
        }
        checkNeighbourhood(previous, current);
    }

    private TripleExpression parse(int priority) throws Exception {
        TripleExpression operand;

        if (priority < Token.MAX_PRIORITY) {
            operand = parse(priority + 1);
        } else {
             getNextToken();

            if (current == Lexem.O_PAREN) {
                parenCnt++;
                TripleExpression result = parse(1);

                if (current == Lexem.C_PAREN) {
                    parenCnt--;
                    getNextToken();
                } else {
                    throw new ParserException(InputMismatch.NO_CLOSING_PAREN);
                }

                return result;
            } else if (current == Lexem.C_PAREN) {
                throw new ParserException(InputMismatch.NO_OPENING_PAREN);
            } else {
                if (isUnary(current)) {
                    return current.creator.create(parse(Token.MAX_PRIORITY));
                } else {
                    getNextToken();
                    return previous.creator.create();
                }
            }
        }

        while (current != Lexem.END) {
            if (current.priority == priority) {
                operand = current.creator.create(operand, parse(priority + 1));
            } else {
                return operand;
            }
        }

        if (parenCnt > 0) {
            throw new ParserException(InputMismatch.NO_CLOSING_PAREN);
        }

        return operand;
    }

    public TripleExpression parse(String input) throws Exception {
        this.current = Lexem.START;
        this.parenCnt = 0;
        tokenizer = new Tokenizer(input);

        return parse(1);
    }
}
