package expression.exceptions;

/**
 * Created by penguinni on 01.04.16.
 */

public class CalcException extends Exception {
    enum Operation {
        DIV("division"),
        LOG("logarithm"),
        POW("power"),
        SQRT("square root");

        private String name;

        Operation(String name) {
            this.name = name;
        }
    }

    CalcException(Operation operation, String details) {
        super("error occured in " + operation.name + ": " + details);
    }
}