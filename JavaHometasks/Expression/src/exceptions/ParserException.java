package exceptions;

public class ParserException extends Exception {
    public enum InputMismatch {
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

    public ParserException (InputMismatch error) {
        super(error.message);
    }
    public ParserException (InputMismatch error, int i) {
        super (error.message + i);
    }
}
