package expression.generic;

/**
 * Created by penguinni on 14.04.16.
 * penguinni hopes it will work.
 */
public interface Tabulator {
    Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception;
}