package exceptions;

public class CalcException extends Exception {
    public enum Operation {
        DIV("division"),
        LOG("logarithm"),
        POW("power"),
        SQRT("square root");

        private String name;

        Operation(String name) {
            this.name = name;
        }
    }

    public CalcException(Operation operation, String details) {
        super("error occured in " + operation.name + ": " + details);
    }
}