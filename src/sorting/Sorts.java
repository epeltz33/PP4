/*----------------------------------------------------------------------------
* SortTestHarness.java
*
*   based on program developed by Dale/Joyce/Weems
*   for Object-Oriented Data Structures Using Java, 3rd Edition, Chapter 10
*
---------------------------------------------------------------------------- */
package sorting;

import java.util.*;
import java.text.DecimalFormat;

public class Sorts {

    static final int SIZE = 50;            // size of array to be sorted
    static int[] values = new int[SIZE];   // values to be sorted

    static int[] valuesBup = new int[SIZE];   // backup of values

    static int comparisons, swaps = 0;   // comparison and swap counts

    static boolean randomValues = true;

    static int[] fixedTestValues = new int[]
            /**/
            {
                    29, 32, 62, 64, 34, 90,  7, 67, 43,  2,
                    13, 65, 65, 01, 10, 23, 76, 95,  3, 23,
                    78, 80, 69, 95, 29, 90, 70, 68, 23, 58,
                    80, 23, 92, 55, 80, 86, 11, 42, 12, 64,
                    2, 25,  0,  8, 23, 99, 77, 16, 48, 49
            }; /**/

    // initialize the values array with random integers from 0 to 99
    static void initializeValues() {
        if (randomValues) {
            Random rand = new Random();
            for (int index = 0; index < SIZE; index++)
                values[index] = Math.abs(rand.nextInt()) % 100;
        }
        else {
            for (int index = 0; index < SIZE; index++)
                values[index] = fixedTestValues[index];
        }
    }

    // checker method that returns true if the array values are sorted, false otherwise
    static public boolean isSorted() {
        boolean sorted = true;
        for (int index = 0; index < (SIZE - 1); index++)
            if (values[index] > values[index + 1])
                sorted = false;
        return sorted;
    }

    // swap the integers at locations index1 and index2 in the values array
    static public void swap(int index1, int index2) {
        int temp = values[index1];
        values[index1] = values[index2];
        values[index2] = temp;
    }

    // displays the contents of the values array
    static public void displayValues() {
        int value;
        DecimalFormat fmt = new DecimalFormat("00");
        System.out.println("----------------------------------");
        for (int index = 0; index < SIZE; index++) {
            value = values[index];
            if (((index + 1) % 10) == 0)
                System.out.println(fmt.format(value));
            else
                System.out.print(fmt.format(value) + " ");
        }
        System.out.println();
    }

    static void backupValues() {
        valuesBup = Arrays.copyOf(values, values.length);
    }

    static void restoreValues() {
        values = Arrays.copyOf(valuesBup, valuesBup.length);
    }

    // Implementation of Selection Sort with comparison and swap counting
    static void selectionSort() {
        for (int i = 0; i < SIZE - 1; i++) {
            int minIndex = i;
            // Find minimum element in unsorted portion
            for (int j = i + 1; j < SIZE; j++) {
                comparisons++;  // Count comparison before potential swap
                if (values[j] < values[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap if minimum element is not at current position
            if (minIndex != i) {
                swap(i, minIndex);
                swaps++;  // Count swap when it occurs
            }
        }
    }

    // Implementation of Bubble Sort with comparison and swap counting
    static void bubbleSort() {
        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1 - i; j++) {
                comparisons++;  // Count comparison before potential swap
                if (values[j] > values[j + 1]) {
                    swap(j, j + 1);
                    swaps++;  // Count swap when it occurs
                }
            }
        }
    }

    // Implementation of Better Bubble Sort with comparison and swap counting
    static void betterBubble() {
        boolean swapped;
        for (int i = 0; i < SIZE - 1; i++) {
            swapped = false;
            for (int j = 0; j < SIZE - 1 - i; j++) {
                comparisons++;  // Count comparison before potential swap
                if (values[j] > values[j + 1]) {
                    swap(j, j + 1);
                    swaps++;  // Count swap when it occurs
                    swapped = true;
                }
            }
            // If no swapping occurred, array is sorted
            if (!swapped) {
                break;
            }
        }
    }

    // Implementation of Insertion Sort with comparison and swap counting
    static void insertionSort() {
        for (int i = 1; i < SIZE; i++) {
            int key = values[i];
            int j = i - 1;

            while (j >= 0) {
                comparisons++;  // Count comparison before potential move
                if (values[j] > key) {
                    values[j + 1] = values[j];
                    swaps++;  // Count swap when it occurs
                    j--;
                } else {
                    break;
                }
            }
            values[j + 1] = key;
        }
    }

    static void merge (int leftFirst, int leftLast, int rightFirst, int rightLast) {
        int[] tempArray = new int [SIZE];
        int index = leftFirst;
        int saveFirst = leftFirst;

        while ((leftFirst <= leftLast) && (rightFirst <= rightLast)) {
            if (values[leftFirst] < values[rightFirst]) {
                tempArray[index] = values[leftFirst];
                leftFirst++;
            }
            else {
                tempArray[index] = values[rightFirst];
                rightFirst++;
            }
            index++;
        }

        while (leftFirst <= leftLast) {
            tempArray[index] = values[leftFirst];
            leftFirst++;
            index++;
        }

        while (rightFirst <= rightLast) {
            tempArray[index] = values[rightFirst];
            rightFirst++;
            index++;
        }

        for (index = saveFirst; index <= rightLast; index++)
            values[index] = tempArray[index];
    }

    static void mergeSort(int first, int last) {
        if (first < last) {
            int middle = (first + last) / 2;
            mergeSort(first, middle);
            mergeSort(middle + 1, last);
            merge(first, middle, middle + 1, last);
        }
    }

    static int split(int first, int last) {
        int splitVal = values[first];
        int saveF = first;
        boolean onCorrectSide;

        first++;

        do {
            onCorrectSide = true;
            while (onCorrectSide) {
                if (values[first] > splitVal) {
                    onCorrectSide = false;
                }
                else {
                    first++;
                    onCorrectSide = (first <= last);
                }
            }
            onCorrectSide = (first <= last);
            while (onCorrectSide) {
                if (values[last] <= splitVal) {
                    onCorrectSide = false;
                }
                else {
                    last--;
                    onCorrectSide = (first <= last);
                }
            }

            if (first < last) {
                swap(first, last);
                first++;
                last--;
            }
        } while (first <= last);

        swap(saveF, last);
        return last;
    }

    static void quickSort(int first, int last) {
        if (first < last) {
            int splitPoint = split(first, last);
            quickSort(first, splitPoint - 1);
            quickSort(splitPoint + 1, last);
        }
    }

    static int newHole(int hole, int lastIndex, int item) {
        int left  = (hole * 2) + 1;
        int right = (hole * 2) + 2;
        if (left > lastIndex)
            return hole;
        else {
            if (left == lastIndex) {
                if (item < values[left])
                    return left;
                else
                    return hole;
            }
            else {
                if (values[left] < values[right]) {
                    if (values[right] <= item)
                        return hole;
                    else
                        return right;
                }
                else {
                    if (values[left] <= item)
                        return hole;
                    else
                        return left;
                }
            }
        }
    }

    static void reheapDown(int item, int root, int lastIndex) {
        int hole = root;
        int newhole;
        boolean newHoleForItem = false;

        newhole = newHole(hole, lastIndex, item);
        while (newhole != hole) {
            newHoleForItem = true;
            values[hole] = values[newhole];
            hole = newhole;
            newhole = newHole(hole, lastIndex, item);
        }
        values[hole] = item;
    }

    static void heapSort() {
        int index;
        for (index = SIZE/2 - 1; index >= 0; index--)
            reheapDown(values[index], index, SIZE - 1);

        for (index = SIZE - 1; index >=1; index--) {
            swap(0, index);
            reheapDown(values[0], 0, index - 1);
        }
    }

    public static void main(String[] args) {
        initializeValues();
        backupValues();
        System.out.println("\nValues to be sorted");
        displayValues();
        System.out.println("values are sorted: " + isSorted());

        System.out.println("\n\nSelection Sort");
        swaps = 0;
        comparisons = 0;
        selectionSort();
        displayValues();
        System.out.println("values are Selection sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);

        System.out.println("\n\nBubble Sort");
        restoreValues();
        swaps = 0;
        comparisons = 0;
        bubbleSort();
        displayValues();
        System.out.println("values are Bubble sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);

        System.out.println("\n\nBetter Bubble Sort");
        restoreValues();
        swaps = 0;
        comparisons = 0;
        betterBubble();
        displayValues();
        System.out.println("values are Better Bubble sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);

        System.out.println("\n\nInsertion Sort");
        restoreValues();
        swaps = 0;
        comparisons = 0;
        insertionSort();
        displayValues();
        System.out.println("values are Insertion sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);

        System.out.println("\n\nMerge Sort");
        restoreValues();
        mergeSort(0,SIZE-1);
        displayValues();
        System.out.println("values are Merge sorted: " + isSorted());

        System.out.println("\n\nQuick Sort");
        restoreValues();
        quickSort(0,SIZE-1);
        displayValues();
        System.out.println("values are Quick sorted: " + isSorted());

        System.out.println("\n\nHeap Sort");
        restoreValues();
        heapSort();
        displayValues();
        System.out.println("values are Heap sorted: " + isSorted());
    }
}