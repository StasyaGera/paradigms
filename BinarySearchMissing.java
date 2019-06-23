/**
 * Created by Стася on 26.02.2016.
 */
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BinarySearchMissing {

    //pre:
    //array.size > 0
    //array is sorted (array[i] <= array[i-1] for any i between 0 and array.size)
    public static int iterativeBS(long x, ArrayList<Long> arr) {
        int l = -1, r = arr.size(), m = (l + r) / 2;
        //inv:
        //l < r
        //there are no elements with x or less value before array[l]

        //pre:
        //inv && array[l] > x >= array[r]
        while (r - l > 1) {
            //inv
            if (arr.get(m) <= x) {
                //inv && array[m'] <= x
                r = m;
                //inv && array[r'] <= x
            } else {
                //inv && array[m'] > x
                l = m;
                //inv && array[l'] > x
            }
            m = (l + r) / 2;
            //inv && array[l'] > x >= array[r']
        }
        //post:
        //inv && r' <= l' + 1 && array[l'] > x >= array[r']

        //inv && r' <= l' + 1 | (r > l) && (r' <= l' + 1) => r' == l' + 1
        //r' == l' + 1 && array[l'] > x >= array[r'] | => (array[r' - 1] > x >= array[r']) => r' is the place for x
        if ((r < arr.size()) && (arr.get(r) == x)) {
            return r;
        } else {
            return (- r - 1);
        }

    }
    //post:
    //array[r] <= x
    //r == min of J, J: {j_k | array[jk] <= x} (k: [0 .. array.size])

    //pre:
    //array is sorted (array[i] < array[i-1] в€Ђi: 0 < i < array.size)
    //l < r
    public static int recursiveBS(long x, int l, int r, ArrayList<Long> arr) {
        int m = (l + r) / 2;
        if (1 < r - l) {
            if (arr.get(m) <= x) {
                //array is sorted && l' < r'
                //array[r'] <= x
                return recursiveBS(x, l, m, arr);
            } else {
                //array is sorted && l' < r'
                //array[l'] > x
                return recursiveBS(x, m, r, arr);
            }
            //array[l'] > x >= array[r']
        } else {
            //l' + 1 >= r'
            //array[r' - 1] > x >= array[r']
            if ((r < arr.size()) && (arr.get(r) == x)) {
                return r;
            } else {
                return (- r - 1);
            }
        }
    }
    //post:
    //array[r] <= x
    //m == min of J, J: {jk | array[jk] <= x} (k: [0 .. array.size])

    public static void main(String[] args) {
        long x = Long.parseLong(args[0]);

        ArrayList<Long> array = new ArrayList<>();

        for (int i = 1; i < Array.getLength(args); i++) {
            array.add(Long.parseLong(args[i]));
        }

        System.out.println(Integer.toString(iterativeBS(x, array)));
//        System.out.println(Integer.toString(recursiveBS(x, -1, array.size(), array)));
    }
}
