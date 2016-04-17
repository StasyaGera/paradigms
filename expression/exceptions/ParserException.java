package expression.exceptions;

/**
 * Created by penguinni on 31.03.16.
 */

class ParserException extends Exception {
    enum InputMismatch {
        NO_FIRST_ARGUMENT("missing first argument"),
        NO_MID_ARGUMENT("missing middle argument at position "),
        NO_LAST_ARGUMENT("missing last argument"),
        NO_OPENING_PAREN("missing opening parenthesis"),
        NO_CLOSING_PAREN("missing closing parenthesis"),
        UNEXPECTED_SYM("unexpected symbol at position "),
        NO_OPERATOR("missing binary operator at position ");

        private String message;

        InputMismatch(String message) {
            this.message = message;
        }
    }

    ParserException (InputMismatch error) {
        super(error.message);
    }
    ParserException (InputMismatch error, int i) {
        super (error.message + i);
    }
}
