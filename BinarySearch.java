/**
 * Created by Стася on 26.02.2016.
 */
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BinarySearch {

    //pre:
    //array.size > 0
    //array is sorted (array[i] < array[i-1] в€Ђi: 0 < i < array.size)
    //в€ѓi : array[i] <= x
    public static int iterativeBS(int x, ArrayList<Integer> arr) {
        int l = -1, r = arr.size(), m = (l + r) / 2;
        //inv:
        //l < r
        //there are no elements with x value before array[l]

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
        //r' == l' + 1 && array[l'] > x >= array[r'] | => (array[r' - 1] > x >= array[r']) => r' is answer
        return r;
    }
    //post:
    //array[r] <= x
    //r == min of J, J: {jk | array[jk] <= x} (k: [0 .. array.size])

    //pre:
    //array is sorted (array[i] < array[i-1] в€Ђi: 0 < i < array.size)
    //l < r
    public static int recursiveBS(int x, int l, int r, ArrayList<Integer> arr) {
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
            return r;
        }
    }
    //post:
    //array[r] <= x
    //m == min of J, J: {jk | array[jk] <= x} (k: [0 .. array.size])

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        ArrayList<Integer> array = new ArrayList<>();

        for (int i = 1; i < Array.getLength(args); i++) {
            array.add(Integer.parseInt(args[i]));
        }

//        System.out.println("iterative: " + Integer.toString(iterativeBS(x, array)));
//        System.out.println("recursive: " + Integer.toString(recursiveBS(x, -1, array.size(), array)));
        System.out.println(Integer.toString(iterativeBS(x, array)));
    }
}
