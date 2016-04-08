package expression.exceptions;

/**
 * Created by penguinni on 31.03.16.
 */

public class OverflowException extends Exception {
    enum Operation {
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

    OverflowException (Operation operation) {
        super("Overflow in " + operation.name);
    }
}
