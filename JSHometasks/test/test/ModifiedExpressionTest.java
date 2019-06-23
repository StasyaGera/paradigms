package test;

import static test.Util.list;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ModifiedExpressionTest extends ExpressionTest {
    protected ModifiedExpressionTest(final boolean hard) {
        super(hard);
        language.binary.addAll(list(
                Language.expr("mod", "%", (a, b) -> a % (double) b),
                Language.expr("power", "**", StrictMath::pow)
        ));
        language.unary.addAll(list(
                Language.expr("abs", "abs", StrictMath::abs),
                Language.expr("log", "log", StrictMath::log)
        ));
        language.tests.addAll(list(
                Language.expr("abs(subtract(variable('x'), variable('y')))", "x y - abs", (x, y, z) -> StrictMath.abs(x - y)),
                Language.expr("log(add(variable('x'), variable('y')))", "x y + log", (x, y, z) -> StrictMath.log(x + y)),
                Language.expr("mod(variable('x'), variable('y'))", "x y %", (x, y, z) -> x % y),
                Language.expr("power(variable('x'), variable('y'))", "x y **", (x, y, z) -> StrictMath.pow(x, y))
        ));
    }

    public static void main(final String[] args) {
        new ModifiedExpressionTest(mode(args, ModifiedExpressionTest.class, "easy", "hard") == 1).test();
    }
}