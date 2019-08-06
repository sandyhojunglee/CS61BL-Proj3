import java.util.Collections;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.swap;

public class Sorts {

    // Ensures you get the same sequence of random numbers every single time.
    private static Random generator;
    private static final int RANDOM_SEED = 400;

    /* Returns the result of sorting the values in LIST using insertion sort. */
    public static void insertionSort(List<Integer> list) {
        // TODO: YOUR CODE HERE\
        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j > 0 && list.get(j) < list.get(j-1); j-- ) {
                Collections.swap(list, j, j-1);
            }
        }
//        //Annie's
//        for (int i = 1; i < list.size(); i ++) {
//            int key = list.get(i);
//            int j = i - 1;
//
//            while (j >= 0 && list.get(j) > key) {
//                list.set(j+1, list.get(j));
//                j--;
//            }
//
//            list.set(j+1, key);
//        }

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

        quickSort(list, 0, list.size() - 1, generator);


    }

    private static void quickSort(List<Integer> list, int start, int end, Random generator) {

        generator = new Random(RANDOM_SEED);

        if (list.size() <= 1) {
            return;
        }
        // Below are example of how to use the random number generator. You will

        // need to do this in your code somehow
        int pivotIndex = generator.nextInt((end - start)) + start;

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
    // TODO: YOUR CODE HERE (You may use a helper function if you wish.)

//Sandy's

//        if (list.size() == 1) {
////            int mid = (1 + list.size()) / 2;
////            //List<Integer> firstHalf = mergeSortHelper(list, 0, mid);
////            //List<Integer> lastHalf = mergeSortHelper(list, mid + 1, list.size() - 1);
////            //firstHalf.addAll(lastHalf);
//            return;
//        }
//        mergeSortHelper(list, 0, list.size());
//    }
//    private static List<Integer> mergeSortHelper(List<Integer> list, int start, int end) {
//        if (list.size() > 1){
//            int mid = (list.size() + 1) / 2;
//
//            mergeSortHelper(list, 0, mid);
//            mergeSortHelper(list, mid + 1, list.size());
        int size = list.size();
        if (size <= 1) {
            return;
        }
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        for (int x = 0; x < size/2; x ++) {
            a.add(0);
        }
        for (int x = 0; x < size-size/2; x ++) {
            b.add(0);
        }

        for (int i = 0; i < a.size(); i++) {
            a.set(i, list.get(i));
        }

        for (int i = 0; i < b.size(); i++) {
            b.set(i, list.get(i + size/2));
        }

        mergeSort(a);
        mergeSort(b);

        merge(a, b, list);

        //sort(list, 0, list.size()-1);
    }

//    public static void sort(List<Integer> list, int start, int end) {
//        if (start < end) {
//            int middle = (start + end) / 2;
//
//            sort(list, start, middle);
//            sort(list, middle + 1, end);
//
//            merge(list, start, middle, end);
//        }
//    }

    public static void merge(List<Integer> a, List<Integer> b, List<Integer> list) {
        List<Integer> c = new ArrayList<>();
        for (int x = 0; x < a.size()+b.size(); x ++) {
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

        for(int k = 0; k < c.size(); k++) {
            list.set(k, c.get(k));
        }
    }


//    public static void merge(List<Integer> list, int start, int middle, int end) {
//        int firstArray;
//        int secondArray;
//        int mergedArray;
//        int firstHalf = middle - start + 1;
//        int secondHalf = end - middle;
//
//        List<Integer> temp1 = new ArrayList<>();
//        List<Integer> temp2 = new ArrayList<>();
//        for (int x = 0; x < firstHalf; x ++) {
//            temp1.add(0);
//        }
//        for (int x = 0; x < secondHalf; x ++) {
//            temp2.add(0);
//        }
//
//
//        for (firstArray = 0; firstArray < firstHalf; firstArray++) {
//            temp1.set(firstArray, list.get(start + firstArray));
//        }
//
//        for (secondArray = 0; secondArray < secondHalf; secondArray++) {
//            temp2.set(secondArray, list.get(middle + 1 + secondArray));
//        }
//        firstArray = 0;
//        secondArray = 0;
//        mergedArray = 1;
//
//        while (firstArray < firstHalf && secondArray < secondHalf) {
//            if (temp1.get(firstArray) <= temp2.get(secondArray)) {
//                list.set(mergedArray, temp1.get(firstArray));
//                firstArray++;
//            }
//            else {
//                list.set(mergedArray, temp2.get(secondArray));
//                secondArray++;
//            }
//            mergedArray++;
//        }
//
//        while (firstArray < firstHalf) {
//            list.set(mergedArray, temp1.get(firstArray));
//            firstArray++;
//            mergedArray++;
//        }
//
//        while (secondArray < secondHalf) {
//            list.set(mergedArray, temp2.get(secondArray));
//            secondArray++;
//            mergedArray++;
//        }
//
//
//    }


}
