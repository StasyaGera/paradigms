package generic;

public class Main {
    public static void main(String[] args) throws Exception {
        int x1 = -15, x2 = 3;
        int y1 = -18, y2 = 8;
        int z1 = -5, z2 = 5;
        Tabulator tabulator = new GenericTabulator();
        Object[][][] table = tabulator.tabulate("i", "y / z", x1, x2, y1, y2, z1, z2);
        /*for (int i = 0; i <= Math.abs(x2 - x1); i++) {
            for (int j = 0; j <= Math.abs(y2 - y1); j++) {
                for (int k = 0; k <= Math.abs(z2 - z1); k++){
                    System.out.print(table[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        */
        System.out.println(table[0][0][5]);
        //System.out.println(new GEParser<Integer>().parse("5 / 6", "d").evaluate(new IntegerWrapper(1), new IntegerWrapper(2), new IntegerWrapper(3)).value);

    }
}
