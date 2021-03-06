package generic;

import exceptions.OverflowException;
import exceptions.ParserException;
import generic.GToken.Lexem;
import generic.wrapper.Numeric;

import java.util.Arrays;

import static generic.GEParser.previous;
import static java.lang.Character.*;

class GTokenizer<T extends Number> {
    boolean hasTokens;
    int index;
    String constant;
    final Numeric<T> instance;

    private String input;

    GTokenizer(Numeric<T> instance, String input) {
        this.instance = instance;
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

    Numeric<T> getConst() throws OverflowException {
        return instance.strToNum(constant);
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

        throw new ParserException(ParserException.InputMismatch.UNEXPECTED_SYM, index - name.length());
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

        throw new ParserException(ParserException.InputMismatch.UNEXPECTED_SYM, index);
    }
}
