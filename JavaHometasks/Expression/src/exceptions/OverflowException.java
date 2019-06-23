package exceptions;

public class OverflowException extends Exception {
    public enum Operation {
        INPUT("input"),
        ADD("addition"),
        SUB("subtract"),
        MUL("multiplication"),
        DIV("division"),
        POW("power"),
        ABS("absolute"),
        NEG("negation");

        private String name;

        Operation(String name) {
            this.name = name;
        }
    }

    public OverflowException (Operation operation) {
        super("Overflow in " + operation.name);
    }
}
