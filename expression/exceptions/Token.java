package expression.exceptions;

import expression.*;
import expression.exceptions.ParserException.InputMismatch;

import java.util.Arrays;

import static expression.exceptions.ExpressionParser.constants;
import static expression.exceptions.ExpressionParser.operations;

/**
 * Created by penguinni on 04.04.16.
 */

class Token {
    final static int MAX_PRIORITY = 4;

    interface Creator {
        TripleExpression create(TripleExpression... operands) throws OverflowException;
    }

    enum Lexem {
        ADD("+", 1, (TripleExpression... operands) -> new CheckedAdd(operands[0], operands[1])),
        SUB("-", 1, (TripleExpression... operands) -> new CheckedSubtract(operands[0], operands[1])),
        MUL("*", 2, (TripleExpression... operands) -> new CheckedMultiply(operands[0], operands[1])),
        DIV("/", 2, (TripleExpression... operands) -> new CheckedDivide(operands[0], operands[1])),
        POW("**", 3, (TripleExpression... operands) -> new CheckedPower(operands[0], operands[1])),
        LOG("//", 3, (TripleExpression... operands) -> new CheckedLogarithm(operands[0], operands[1])),

        ABS("abs", 4, (TripleExpression... operands) -> new CheckedAbsolute(operands[0])),
        SQRT("sqrt", 4, (TripleExpression... operands) -> new CheckedSqrt(operands[0])),
        NEG("-", 4, (TripleExpression... operands) -> new CheckedNegate(operands[0])),

        X("x", 4, (TripleExpression... operands) -> new Variable("x")),
        Y("y", 4, (TripleExpression... operands) -> new Variable("y")),
        Z("z", 4, (TripleExpression... operands) -> new Variable("z")),
        CONSTANT("", 4, (TripleExpression... operands) -> new Const(wordToConst(constants.pop()))),

        O_PAREN("(", 4, null),
        C_PAREN(")", 4, null),

        END("", 0, null),
        START("", 0, null);

        final String key;
        final int priority;
        Creator creator;

        final static Lexem[] VOCAB = new Lexem[]{ADD, SUB, MUL, DIV, LOG, POW, ABS, SQRT, X, Y, Z, CONSTANT, O_PAREN, C_PAREN};
        final static Lexem[] SUB_TO_NEG = new Lexem[]{O_PAREN, SUB, ADD, MUL, DIV, LOG, POW, NEG, ABS, SQRT};
        final static Lexem[] SYMBOL_NAMED = new Lexem[]{ADD, SUB, MUL, DIV, O_PAREN, C_PAREN};

        final static Lexem[] BINARY = new Lexem[]{ADD, SUB, MUL, DIV, POW, LOG};
        final static Lexem[] UNARY = new Lexem[]{NEG, ABS, SQRT};
        final static Lexem[] NUMERIC = new Lexem[]{X, Y, Z, CONSTANT};

        Lexem(String key, int priority, Creator creator) {
            this.key = key;
            this.priority = priority;
            this.creator = creator;
        }
    }

    static boolean isUnary(Lexem lexem) {
        return Arrays.asList(Lexem.UNARY).contains(lexem);
    }

    private static boolean isBinary(Lexem lexem) {
        return Arrays.asList(Lexem.BINARY).contains(lexem);
    }

    private static boolean isNumeric(Lexem lexem) {
        return Arrays.asList(Lexem.NUMERIC).contains(lexem);
    }

    private static int wordToConst(String word) throws OverflowException {
        int i = 0, value = 0;
        boolean negative = false;
        while ((i < word.length()) && (word.charAt(i) == '-')) {
            negative = !negative;
            i++;
        }

        while (i < word.length()) {
            value = value * 10 - Character.getNumericValue(word.charAt(i++));
            if (value > 0) {
                throw new OverflowException(OverflowException.Operation.INPUT);
            }
        }

        if (negative) return value;
        return -value;
    }

    static void checkNeighbourhood(Lexem left, Lexem right) throws ParserException {
        if ((isNumeric(left) || (left == Lexem.C_PAREN)) && right == Lexem.CONSTANT) {
            String word = "";
            if (left == Lexem.CONSTANT) {
                word = constants.pop();
            }

            if (constants.peek().charAt(0) == '-') {
                constants.push(constants.pop().substring(1));
                if (left == Lexem.CONSTANT) {
                    constants.push(word);
                }
                operations.push(Lexem.SUB);
                right = Lexem.SUB;
            }
        }

        if (isNumeric(left) && (
                isNumeric(right) || right == Lexem.O_PAREN || isUnary(right)
            ) || left == Lexem.C_PAREN && (
                isNumeric(right) || isUnary(right)
            )
        ) {

            throw new ParserException(InputMismatch.NO_OPERATOR);
        }

        if ((isBinary(left) || left == Lexem.START || isUnary(left)) && (
                isBinary(right)|| right == Lexem.START || right == Lexem.C_PAREN || right == Lexem.END
        ) || (left == Lexem.O_PAREN && (right == Lexem.START || isBinary(right)))) {

            throw new ParserException(InputMismatch.NO_ARGUMENT);
        }
    }
}
