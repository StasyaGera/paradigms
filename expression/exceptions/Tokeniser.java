package expression.exceptions;

import java.util.Arrays;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;

/**
 * Created by penguinni on 07.04.16.
 * penguinni hopes it will work.
 */

class Tokeniser {
    Tokeniser (String input) {
        this.input = input;
        this.index = input.length() - 1;

        cutWS();
        if (index >= 0) {
            this.hasTokens = true;
        }
    }

    boolean hasTokens;

    private String input;
    private int index;

    private Token.Lexem lexemByName(String name) throws ParserException {
        for (Token.Lexem lexem : Token.Lexem.VOCAB) {
            if (name.equals(lexem.key)) {
                return lexem;
            }
        }
        throw new ParserException(ParserException.InputMismatch.UNEXPECTED_SYM);
    }

    private boolean isSymbol(char symbol) {
        for (Token.Lexem lexem : Token.Lexem.SYMBOL_NAMED) {
            if (lexem.key.contains("" + symbol)) {
                return true;
            }
        }
        return false;
    }

    private void checkState() {
        if (index < 0) {
            hasTokens = false;
        }
    }

    private void cutWS() {
        while ((index >= 0) && isWhitespace(input.charAt(index))) {
            index--;
            checkState();
        }
    }

    Token.Lexem nextToken() throws ParserException {
        String word = "";

        while ((index >= 0) && isDigit(input.charAt(index))) {
            word = input.charAt(index--) + word;
            checkState();
        }
        if (!word.equals("")) {
            ExpressionParser.constants.push(word);
            cutWS();
            return Token.Lexem.CONSTANT;
        }

        while ((index >= 0) && isLetter(input.charAt(index))) {
            word = input.charAt(index--) + word;
            checkState();
        }
        if (!word.equals("")) {
            cutWS();
            return lexemByName(word);
        }

        if ((index >= 0) && isSymbol(input.charAt(index))) {
            word = input.charAt(index--) + word;
            checkState();
            if ((index >= 0) && ((input.charAt(index) == '*') || (input.charAt(index) == '/')) && (input.charAt(index) == word.charAt(0))) {
                word = input.charAt(index--) + word;
                checkState();
            }
        }
        if (!word.equals("")) {
            cutWS();
            return lexemByName(word);
        }

        throw new ParserException(ParserException.InputMismatch.UNEXPECTED_SYM);
    }
}
