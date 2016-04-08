package expression.exceptions;

import expression.*;
import expression.exceptions.ParserException.InputMismatch;
import expression.exceptions.Token.Lexem;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by penguinni on 28.03.16.
 */

public class ExpressionParser implements Parser {
    static Stack<String> constants = new Stack<>();
    static Stack<Lexem> operations = new Stack<>();

    private Tokeniser tokeniser;

    private void getOperationsStack() throws ParserException {
        Lexem lexem;
        int parenCnt = 0;

        operations.push(Lexem.END);

        while (tokeniser.hasTokens) {
            lexem = tokeniser.nextToken();

            if ((lexem == Lexem.SUB) && (operations.peek() == Lexem.CONSTANT)) {
                constants.push("-" + constants.pop());
                continue;
            } else if (lexem == Lexem.C_PAREN) {
                parenCnt++;
            } else if (lexem == Lexem.O_PAREN) {
                if (parenCnt == 0) {
                    throw new ParserException(InputMismatch.NO_CLOSING_PAREN);
                }
                parenCnt--;
            }

            if ((operations.peek() == Lexem.SUB) && (Arrays.asList(Lexem.SUB_TO_NEG).contains(lexem))) {
                operations.pop();
                operations.push(Lexem.NEG);
            }

            Token.checkNeighbourhood(lexem, operations.peek());
            operations.push(lexem);
        }

        if (parenCnt > 0) {
            throw new ParserException(InputMismatch.NO_OPENING_PAREN);
        }

        if (operations.peek() == Lexem.SUB) {
            operations.pop();
            operations.push(Lexem.NEG);
        }

        Token.checkNeighbourhood(Lexem.START, operations.peek());
    }

    private TripleExpression parse(int priority) throws Exception {
        TripleExpression operand;

        if (priority < Token.MAX_PRIORITY) {
            operand = parse(priority + 1);
        } else {
            if (operations.peek() == Lexem.O_PAREN) {
                operations.pop();
                TripleExpression result = parse(1);
                if (operations.peek() == Lexem.C_PAREN) {
                    operations.pop();
                }
                return result;
            } else {
                if (Token.isUnary(operations.peek())) {
                    return operations.pop().creator.create(parse(Token.MAX_PRIORITY));
                } else {
                    return operations.pop().creator.create();
                }
            }
        }

        while (operations.peek() != Lexem.END) {
            if (operations.peek().priority == priority) {
                operand = operations.pop().creator.create(operand, parse(priority + 1));
            } else {
                return operand;
            }
        }
        return operand;
    }

    public TripleExpression parse(String input) throws Exception {
        tokeniser = new Tokeniser(input);
        getOperationsStack();

        return parse(1);
    }
}
