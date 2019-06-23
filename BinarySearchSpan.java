/**
 * Created by Стася on 26.02.2016.
 */
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BinarySearchSpan {

    public static class Result {
        public int place;
        public int length;
    }

    //pre:  array is not empty
    //      array is sorted in non-increasing (array[i] >= array[i+1] for any i between 0 and array.size)
    public static int iterativeBSS(int x, ArrayList<Integer> arr, boolean getLeftBound) {
        int l = -1, r = arr.size(), m = (l + r) / 2;
        boolean cond;

        //inv:  l < r
        //      there are no elements with x value before array[l] if getLeftBound
        //      there are no elements with x value after array[r] if not getLeftBound
        while (r - l > 1) {
            cond = (getLeftBound ? (arr.get(m) <= x) : (arr.get(m) < x));
            if (cond) {
                r = m;
                //inv &&
                //    && array[r'] <= x if getLeftBound
                //    && array[r'] < x if not getLeftBound
            } else {
                l = m;
                //inv &&
                //    && array[l'] > x if getLeftBound
                //    && array[l'] >= x if not getLeftBound
            }
            m = (l + r) / 2;
            //inv &&
            //    && array[l'] > x >= array[r'] if getLeftBound
            //    && array[l'] >= x > array[r'] if not getLeftBound
        }
        //post:
        //inv &&
        //    && r' <= l' + 1 && array[l'] > x >= array[r']
        //    && array[l'] >= x > array[r']

        //(r > l) && (r' <= l' + 1) => r' == l' + 1
        //r' - 1 == l' && array[l'] > x >= array[r'] => (array[r' - 1] > x >= array[r']) => r' is answer if getLeftBound
        //r' == l' + 1 && array[l'] >= x > array[r'] => (array[l'] >= x > array[l' + 1]) => l' is answer if not getLeftBound
        return (getLeftBound ? r : l);
    }
    //post:
    //r is the smallest index of those [i], which fit the condition: array[i] <= x if getLeftBound
    //l is the biggest index of those [i], which fit: array[i] >= x if not getLeftBound

    //pre:  array is not empty
    //      array is sorted in non-increasing (array[i] >= array[i+1] for any i between 0 and array.size)
    //      l < r
    public static int recursiveBSS(int x, int l, int r, ArrayList<Integer> arr, boolean getLeftBound) {
        int m = (l + r) / 2;
        boolean cond;
        if (1 < r - l) {
            cond = getLeftBound ? (arr.get(m) <= x) : (arr.get(m) < x);
            if (cond) {
                //array[r'] <= x if getLeftBound
                //array[r'] < x if not getLeftBound
                return recursiveBSS(x, l, m, arr, getLeftBound);
            } else {
                //array[l'] > x if getLeftBound
                //array[l'] >= x if not getLeftBound
                return recursiveBSS(x, m, r, arr, getLeftBound);
            }
            //array[l'] > x >= array[r'] if getLeftBound
            //array[l'] >= x > array[r'] if not getLeftBound
        } else {
            //l' + 1 == r'
            //array[r' - 1] > x >= array[r'] if getLeftBound
            //array[l'] >= x > array[l' + 1]  if not getLeftBound
            return (getLeftBound ? r : l);
        }
    }
    //post:
    //r is the smallest index of those [i] which fit the condition: array[i] <= x if getLeftBound
    //l is the biggest index of those [i] which fit: array[i] >= x if not getLeftBound

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        ArrayList<Integer> array = new ArrayList<>();

        for (int i = 1; i < Array.getLength(args); i++) {
            array.add(Integer.parseInt(args[i]));
        }

        Result ans = new Result();

        ans.place = iterativeBSS(x, array, true);
        ans.length = iterativeBSS(x, array, false) - ans.place + 1;

/*
        ans.place = recursiveBSS(x, -1, array.size(), array, true);
        ans.length = recursiveBSS(x, -1, array.size(), array, false) - ans.place + 1;
*/
        System.out.println(Integer.toString(ans.place) + " " + Integer.toString(ans.length));
    }
}
