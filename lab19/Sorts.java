import java.util.Collections;
import java.util.ArrayList;


import java.util.List;
import java.util.Random;


public class Sorts {

    // Ensures you get the same sequence of random numbers every single time.
    private static Random generator;
    private static final int RANDOM_SEED = 400;

    /* Returns the result of sorting the values in LIST using insertion sort. */
    public static void insertionSort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j > 0 && list.get(j) < list.get(j - 1); j--) {
                Collections.swap(list, j, j - 1);
            }
        }
    }

    /* Returns the result of sorting the values in LIST using selection sort. */
    public static void selectionSort(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            int lowestIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < list.get(lowestIndex)) {
                    lowestIndex = j;
                }
            }
            list.add(i, list.remove(lowestIndex));
        }
    }

    /* Returns the result of sorting the values in this list using quicksort. */
    public static void quickSort(List<Integer> list) {

        generator = new Random(RANDOM_SEED);

        if (list.size() <= 1) {
            return;
        }

        quickSort(list, 0, list.size() - 1, generator);

    }

    private static void quickSort(List<Integer> list, int start, int end, Random gen) {
        // Below are example of how to use the random number generator. You will
        // need to do this in your code somehow
        int pivotIndex = gen.nextInt((end - start)) + start;

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        List<Integer> centerList = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < list.get(pivotIndex)) {
                leftList.add(list.get(i));
            } else if (list.get(i) > list.get(pivotIndex)) {
                rightList.add(list.get(i));
            } else {
                centerList.add(list.get(i));
            }
        }

        quickSort(leftList);
        quickSort(rightList);

        list.clear();
        list.addAll(leftList);
        list.addAll(centerList);
        list.addAll(rightList);

    }

    /* Returns the result of sorting the values in this list using merge
       sort. */
    public static void mergeSort(List<Integer> list) {
        int size = list.size();
        if (size <= 1) {
            return;
        }

        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        for (int x = 0; x < size / 2; x++) {
            a.add(0);
        }
        for (int x = 0; x < size - size / 2; x++) {
            b.add(0);
        }

        for (int i = 0; i < a.size(); i++) {
            a.set(i, list.get(i));
        }

        for (int i = 0; i < b.size(); i++) {
            b.set(i, list.get(i + size / 2));
        }

        mergeSort(a);
        mergeSort(b);

        merge(a, b, list);
    }

    public static void merge(List<Integer> a, List<Integer> b, List<Integer> list) {
        List<Integer> c = new ArrayList<>();
        for (int x = 0; x < a.size() + b.size(); x++) {
            c.add(0);
        }
        int i = 0;
        int j = 0;
        for (int k = 0; k < c.size(); k++) {
            if (i >= a.size()) {
                c.set(k, b.get(j++));
            } else if (j >= b.size()) {
                c.set(k, a.get(i++));
            } else if (a.get(i) <= b.get(j)) {
                c.set(k, a.get(i++));
            } else {
                c.set(k, b.get(j++));
            }
        }
        for (int k = 0; k < c.size(); k++) {
            list.set(k, c.get(k));
        }

    }
}
