package generic;

import exceptions.CalcException;
import exceptions.OverflowException;
import generic.base.GExpression;
import generic.wrapper.*;

import java.util.HashMap;
import java.util.Map;

public class GenericTabulator implements Tabulator {
    public static Map<String, Numeric<? extends Number>> WRAPPER = new HashMap<>();
    static {
        WRAPPER.put("i", new IntegerWrapper(0));
        WRAPPER.put("d", new DoubleWrapper(0));
        WRAPPER.put("bi", new BIWrapper(0));
        WRAPPER.put("u", new UIntegerWrapper(0));
        WRAPPER.put("b", new ByteWrapper(0));
        WRAPPER.put("f", new FloatWrapper(0));
    }

    public Object[][][] tabulate(String mode, String input, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return tabulate(WRAPPER.get(mode), input, x1, x2, y1, y2, z1, z2);
    }

    public <T extends Number> Object[][][] tabulate(Numeric<T> instance, String input, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        GExpression<T> expression = new GEParser<>(instance).parse(input);

        Object[][][] table = new Object[Math.abs(x1 - x2) + 1][Math.abs(y1 - y2) + 1][Math.abs(z1 - z2) + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    table[Math.abs(i - x1)][Math.abs(j - y1)][Math.abs(k - z1)] = eval(instance, expression, i, j, k);
                }
            }
        }

        return table;
    }

    private <T extends Number> T eval(Numeric<T> instance, GExpression<T> expression, int x, int j, int k) {
        try {
            return expression.evaluate(instance.create(x), instance.create(j), instance.create(k)).value;
        } catch (OverflowException | CalcException | ArithmeticException e) {
            return null;
        }
    }
}
