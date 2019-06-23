package generic;

import java.util.Arrays;

import exceptions.OverflowException;
import generic.base.*;
import generic.wrapper.Numeric;

class GToken<T extends Number> {
    final static int MAX_PRIORITY = 4;

    interface Creator<T extends Number> {
        GExpression<T> create(GExpression<T>... operands) throws OverflowException;
    }

    enum Lexem {
        ADD("+", 1),
        SUB("-", 1),
        MUL("*", 2), //(GExpression<T>... operands) -> new CheckedMultiply(operands[0], operands[1])),
        DIV("/", 2), //(GExpression<T>... operands) -> new CheckedDivide(operands[0], operands[1])),
        MOD("mod", 2),
        POW("**", 3), //(GExpression<T>... operands) -> new CheckedPower(operands[0], operands[1])),
        LOG("//", 3), //(GExpression<T>... operands) -> new CheckedLogarithm(operands[0], operands[1])),

        ABS("abs", 4), //(GExpression<T>... operands) -> new CheckedAbsolute(operands[0])),
        SQRT("sqrt", 4), //(GExpression<T>... operands) -> new CheckedSqrt(operands[0])),
        NEG("-", 4), //(GExpression<T>... operands) -> new CheckedNegate(operands[0])),
        SQUARE("square", 4),

        X("x", 4), //(GExpression<T>... operands) -> new Variable("x")),
        Y("y", 4), //(GExpression<T>... operands) -> new Variable("y")),
        Z("z", 4), //(GExpression<T>... operands) -> new Variable("z")),
        CONSTANT("", 4), //(GExpression<T>... operands) -> new Const(ExpressionParser.tokenizer.getConst())),

        O_PAREN("(", 4),// null),
        C_PAREN(")", 4),// null),

        END("", 0),// null),
        START("", 0);// null);

        final String key;
        final int priority;
        //final Creator creator;

        final static Lexem[] VOCAB = new Lexem[]{ADD, SUB, MUL, DIV, MOD, LOG, POW, ABS, SQRT, SQUARE, X, Y, Z, CONSTANT, O_PAREN, C_PAREN};
        final static Lexem[] SUB_TO_NEG = new Lexem[]{START, O_PAREN, SUB, ADD, MUL, DIV, MOD, SQUARE, ABS, LOG, POW, NEG, ABS, SQRT};
        final static Lexem[] SYMBOLIC = new Lexem[]{ADD, SUB, MUL, DIV, O_PAREN, C_PAREN};

        final static Lexem[] BINARY = new Lexem[]{ADD, SUB, MUL, DIV, POW, LOG, MOD};
        final static Lexem[] UNARY = new Lexem[]{NEG, ABS, SQRT, SQUARE};
        final static Lexem[] NUMERIC = new Lexem[]{X, Y, Z, CONSTANT};

        Lexem(String key, int priority/*, Creator creator*/) {
            this.key = key;
            this.priority = priority;
            //this.creator = creator;
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


    static <T extends Number> GExpression<T> create(Numeric<T> numeric) throws OverflowException {
        return new GConst<>(numeric);
    }

    static <T extends Number> GExpression<T> create(Lexem object) throws OverflowException {
        if (object == Lexem.X) {
            return new GVariable<>("x");
        } else if (object == Lexem.Y) {
            return new GVariable<>("y");
        } else if (object == Lexem.Z) {
            return new GVariable<>("z");
        }
        return null;
    }

    static <T extends Number> GExpression<T> create(Lexem object, GExpression<T> expression) {
        switch (object) {
            case SQUARE:
                return new GSquare<>(expression);
            case NEG:
                return new GNegate<>(expression);
            case ABS:
                return new GAbs<>(expression);
            case SQRT:
                //return new CheckedSqrt(expression);
            default:
                return null;
        }
    }

    static <T extends Number> GExpression<T> create(Lexem object, GExpression<T> first, GExpression<T> second) throws Exception {
        switch (object) {
            case ADD:
                return new GAdd<>(first, second);
            case SUB:
                return new GSub<>(first, second, "");
            case MUL:
                return new GMul<>(first, second, "");
            case DIV:
                return new GDiv<>(first, second, "");
            case MOD:
                return new GMod<>(first, second);
            case POW:
                //return new CheckedPower(first, second);
            case LOG:
                //return new CheckedLogarithm(first, second);
            default:
                return null;
        }
    }
}
