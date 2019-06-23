import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureFunctionExpressionTest extends BaseTest<ClojureEngine> {
    public static final Dialect UNPARSED = dialect(
            "(variable \"%s\")",
            "(constant %s.0)",
            (op, args) -> "(" + op + " " + String.join(" ", args) + ")"
    );
    public static final Dialect PARSED = dialect(
            "%s",
            "%s.0",
            (op, args) -> "("+ op + " " + String.join(", ", args) + ")"
    );
    protected final boolean testMultiarg;

    protected ClojureFunctionExpressionTest(final Language language, final boolean testMultiarg, final Optional<String> evaluate) {
        super(new ClojureEngine("expression.clj", evaluate), language, true);
        this.testMultiarg = testMultiarg;

        if (testMultiarg) {
            language.tests.addAll(Util.list(
                    m("+", language.vx, language.vy, language.vz),
                    m("+", language.vx),
                    m("-", language.vx, language.vy, language.vz),
                    m("*", language.vx, language.vy, language.vz),
                    m("*", language.vx)
            ));
        }
    }

    protected ClojureFunctionExpressionTest(final boolean testMultiarg) {
        this(
                new ExpressionTest.ArithmeticLanguage(UNPARSED, PARSED, ExpressionTest.OPS),
                testMultiarg,
                Optional.<String>empty()
        );
    }

    @Override
    protected Expr<Double> generate(final double[] vars, final int depth) {
        if (depth == 0 || Util.randomInt(2) == 0 || !testMultiarg) {
            return super.generate(vars, depth);
        }
        return m(
                Util.random(language.bs.get("+"), language.bs.get("*")),
                Stream.generate(() -> generateP(vars, depth)).limit(1 + Util.randomInt(5)).collect(Collectors.toList())
        );
    }

    @SafeVarargs
    protected final Expr<TExpr> m(final String name, final Expr<TExpr>... as) {
        final Expr<BinaryOperator<Double>> op = language.bs.get(name);
        final BinaryOperator<TExpr> t = (q, r) -> (x, y, z) -> op.answer.apply(q.evaluate(x, y, z), r.evaluate(x, y, z));
        return m(Language.expr(op.parsed, op.unparsed, t), Arrays.asList(as));
    }

    private <T> Expr<T> m(final Expr<BinaryOperator<T>> op, final List<Expr<T>> as) {
        return Language.expr(
                "(" + op.parsed + " " + as.stream().map(e -> e.parsed).collect(Collectors.joining(" ")) + ")",
                "(" + op.unparsed + " " + as.stream().map(e1 -> e1.unparsed).collect(Collectors.joining(" ")) + ")",
                as.stream().map(e2 -> e2.answer).reduce(op.answer).get()
        );
    }

    @Override
    protected String parse(final String expression) {
        return "(parseFunction \"" + expression + "\")";
    }

    public static void main(final String... args) {
        new ClojureFunctionExpressionTest(mode(args, ClojureFunctionExpressionTest.class, "easy", "hard") == 1).test();
    }
}