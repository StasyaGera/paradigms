package exceptions;

import base.Const;
import base.TripleExpression;
import base.Variable;

import java.util.Arrays;

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
        CONSTANT("", 4, (TripleExpression... operands) -> new Const(ExpressionParser.tokenizer.getConst())),

        O_PAREN("(", 4, null),
        C_PAREN(")", 4, null),

        END("", 0, null),
        START("", 0, null);

        final String key;
        final int priority;
        final Creator creator;

        final static Lexem[] VOCAB = new Lexem[]{ADD, SUB, MUL, DIV, LOG, POW, ABS, SQRT, X, Y, Z, CONSTANT, O_PAREN, C_PAREN};
        final static Lexem[] SUB_TO_NEG = new Lexem[]{START, O_PAREN, SUB, ADD, MUL, DIV, LOG, POW, NEG, ABS, SQRT};
        final static Lexem[] SYMBOLIC = new Lexem[]{ADD, SUB, MUL, DIV, O_PAREN, C_PAREN};

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

    static boolean isBinary(Lexem lexem) {
        return Arrays.asList(Lexem.BINARY).contains(lexem);
    }

    static boolean isNumeric(Lexem lexem) {
        return Arrays.asList(Lexem.NUMERIC).contains(lexem);
    }

}
