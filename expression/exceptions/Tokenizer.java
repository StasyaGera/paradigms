package expression.exceptions;

import java.util.Arrays;
import expression.exceptions.Token.Lexem;
import static expression.exceptions.ExpressionParser.previous;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;

/**
 * Created by penguinni on 07.04.16.
 * penguinni hopes it will work.
 */

class Tokenizer {
    boolean hasTokens;

    private int index;
    private String input;
    private String constant;

    Tokenizer(String input) {
        this.input = input;
        this.index = 0;

        skipWS();
        this.hasTokens = (index < input.length());
    }

    private void checkState() {
        hasTokens = (index < input.length());
    }

    private void skipWS() {
        while ((hasTokens) && isWhitespace(input.charAt(index))) {
            index++;
            checkState();
        }
    }

    private boolean isSymbol(char symbol) {
        for (Lexem lexem : Lexem.SYMBOLIC) {
            if (lexem.key.contains("" + symbol)) {
                return true;
            }
        }
        return false;
    }

    int getConst() throws OverflowException {
        int i = 0, value = 0;
        boolean negative = false;
        while ((i < constant.length()) && (constant.charAt(i) == '-')) {
            negative = !negative;
            i++;
        }

        while (i < constant.length()) {
            value = value * 10 - Character.getNumericValue(constant.charAt(i++));
            if (value > 0) {
                throw new OverflowException(OverflowException.Operation.INPUT);
            }
        }

        if (negative) return value;
        return -value;
    }

    private Lexem lexemByName(String name) throws ParserException {
        if (isDigit(name.charAt(0))) {
            return Lexem.CONSTANT;
        }
        
        for (Lexem lexem : Lexem.VOCAB) {
            if (name.equals(lexem.key)) {

                if (lexem == Lexem.SUB) {
                    if (Arrays.asList(Lexem.SUB_TO_NEG).contains(previous)) {
                        int saved = index;
                        Lexem next = nextToken();

                        if (next == Lexem.CONSTANT) {
                            constant = "-" + constant;
                            return next;
                        }

                        index = saved;
                        checkState();

                        return Lexem.NEG;
                    }
                }
                return lexem;
            }
        }

        throw new ParserException(ParserException.InputMismatch.UNEXPECTED_SYM);
    }

    Lexem nextToken() throws ParserException {
        skipWS();

        if (!hasTokens) {
            return Lexem.END;
        }

        String word = "";

        while ((hasTokens) && isDigit(input.charAt(index))) {
            word += input.charAt(index++);
            checkState();
        }
        if (!word.equals("")) {
            constant = word;
            return Lexem.CONSTANT;
        }

        while ((hasTokens) && isLetter(input.charAt(index))) {
            word += input.charAt(index++);
            checkState();
        }
        if (!word.equals("")) {
            return lexemByName(word);
        }

        if ((hasTokens) && isSymbol(input.charAt(index))) {
            word += input.charAt(index++);
            checkState();
            if ((hasTokens) && ((input.charAt(index) == '*') || (input.charAt(index) == '/')) && (input.charAt(index) == word.charAt(0))) {
                word += input.charAt(index++);
                checkState();
            }
        }
        if (!word.equals("")) {
            return lexemByName(word);
        }

        throw new ParserException(ParserException.InputMismatch.UNEXPECTED_SYM);
    }
}
