public class DistributionSorts {

    /* Destructively sorts ARR using counting sort. Assumes that ARR contains
       only 0, 1, ..., 9. */
    public static void countingSort(int[] arr) {
        // TODO: YOUR CODE HERE

        //keep track of how many of each index from 0-9 arr has
        int[] counts = new int[10];

        //at each arr's index, find what the value is, and add 1 to counts[arr's index value]
        for (int i = 0; i < arr.length; i++) {
            counts[arr[i]] ++;
        }

        int[] sorted = new int[arr.length];
        int[] starts = new int[counts.length];
        //counts[0] is always 0
        //put start indices
        int sum = counts[0];
        for (int countIndex = 1; countIndex < counts.length; countIndex++) {
            starts[countIndex] = sum;
            sum += counts[countIndex];
        }

        for (int arrIndex = arr.length - 1; arrIndex >= 0; arrIndex--) {
            sorted[starts[arr[arrIndex]]] = arr[arrIndex];
            starts[arr[arrIndex]] ++;
        }

        //Destructive oohhhh
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sorted[i];
        }
    }

    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {
        int maxDigit = mostDigitsIn(arr);
        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }
    }

    /* A helper method for radix sort. Modifies ARR to be sorted according to
       DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the
       rightmost digit of each number. */
    private static void countingSortOnDigit(int[] arr, int digit) {
        // TODO: YOUR CODE HERE
    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }
}
