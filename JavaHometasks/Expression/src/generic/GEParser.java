package generic;

import exceptions.ParserException;
import exceptions.ParserException.InputMismatch;
import generic.GToken.Lexem;
import generic.base.GExpression;
import generic.wrapper.Numeric;

import static generic.GToken.*;

public class GEParser<T extends Number> {
    public GEParser(Numeric<T> instance) {
        this.instance = instance;
    }

    GTokenizer<T> tokenizer;
    static Lexem previous;

    private Numeric<T> instance;
    private Lexem current;
    private int parenCnt;
    
    private void checkNeighbourhood(Lexem left, Lexem right) throws ParserException {
        if ((isNumeric(left) || (left == Lexem.C_PAREN)) && (
                isNumeric(right) || right == Lexem.O_PAREN || isUnary(right)
        ) || left == Lexem.C_PAREN && (
                isNumeric(right) || isUnary(right)
        )
                ) {

            if (right == Lexem.CONSTANT) {
                throw new ParserException(InputMismatch.NO_OPERATOR, tokenizer.index - tokenizer.constant.length() - 1);
            }
            throw new ParserException(InputMismatch.NO_OPERATOR, tokenizer.index - right.key.length() - 1);
        }

        if ((left == Lexem.START) && isBinary(right)) {
            throw new ParserException(InputMismatch.NO_FIRST_ARGUMENT);
        }

        if (isBinary(left) || isUnary(left)) {
            if (isBinary(right) || right == Lexem.C_PAREN) {
                throw new ParserException(InputMismatch.NO_MID_ARGUMENT, tokenizer.index - right.key.length() - 1);
            }
            if (right == Lexem.END) {
                throw new ParserException(InputMismatch.NO_LAST_ARGUMENT);
            }
        }

        if (left == Lexem.O_PAREN && isBinary(right)) {
            throw new ParserException(InputMismatch.NO_MID_ARGUMENT, tokenizer.index - right.key.length() - 1);
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

    private GExpression<T> parse(int priority) throws Exception {
        GExpression<T> operand;

        if (priority < GToken.MAX_PRIORITY) {
            operand = parse(priority + 1);
        } else {
            getNextToken();

            if (current == Lexem.O_PAREN) {
                parenCnt++;
                GExpression<T> result = parse(1);

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
                    return create(current, parse(GToken.MAX_PRIORITY));
                    //return current.creator.create(parse(GToken.MAX_PRIORITY));
                } else {
                    getNextToken();
                    if (previous == Lexem.CONSTANT) {
                        return create(tokenizer.getConst());
                    } else {
                        return create(previous);
                    }
                    //return previous.creator.create();
                }
            }
        }

        while (current != Lexem.END) {
            if (current.priority == priority) {
                operand = create(current, operand, parse(priority + 1));
                //operand = current.creator.create(operand, parse(priority + 1));
            } else {
                return operand;
            }
        }

        if (parenCnt > 0) {
            throw new ParserException(InputMismatch.NO_CLOSING_PAREN);
        }

        return operand;
    }

    public GExpression<T> parse(String input) throws Exception {
        this.current = Lexem.START;
        this.parenCnt = 0;
        tokenizer = new GTokenizer<>(instance, input);

        return parse(1);
    }
}
