import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SearchAnalysis {
    private static final int ARRAY_SIZE = 1000;
    private static final int MIN_SEARCH_VALUE = 1000;
    private static final int MAX_SEARCH_VALUE = 9999;

    // Generate unique random 4-digit numbers
    private static int[] generateUniqueNumbers() {
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();

        while (uniqueNumbers.size() < ARRAY_SIZE) {
            int num = random.nextInt(9000) + 1000; // Generate 4-digit number
            uniqueNumbers.add(num);
        }

        int[] result = new int[ARRAY_SIZE];
        int index = 0;
        for (int num : uniqueNumbers) {
            result[index++] = num;
        }
        return result;
    }

    // Sequential search implementation
    public static int sequentialSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Iterative binary search implementation
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    // Recursive binary search implementation
    public static int recursiveBinarySearch(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        }

        if (arr[mid] > target) {
            return recursiveBinarySearch(arr, target, left, mid - 1);
        }

        return recursiveBinarySearch(arr, target, mid + 1, right);
    }

    // Search analysis method
    private static void analyzeSearch(int[] arr, String searchType, boolean isRecursive) {
        long startTime = System.nanoTime();
        int count = 0;
        long sum = 0;

        for (int i = MIN_SEARCH_VALUE; i <= MAX_SEARCH_VALUE; i++) {
            int result;
            if (searchType.equals("Sequential")) {
                result = sequentialSearch(arr, i);
            } else if (searchType.equals("Binary") && !isRecursive) {
                result = binarySearch(arr, i);
            } else {
                result = recursiveBinarySearch(arr, i, 0, arr.length - 1);
            }

            if (result != -1) {
                count++;
                sum += arr[result];
            }
        }

        long endTime = System.nanoTime();
        double elapsedTimeMs = (endTime - startTime) / 1_000_000.0;

        System.out.println("\n" + searchType + (isRecursive ? " Recursive" : "") + " Search Results:");
        System.out.println("Number of matches: " + count);
        System.out.println("Sum of matches: " + sum);
        System.out.println("Time elapsed: " + String.format("%.2f", elapsedTimeMs) + " ms");
    }

    public static void main(String[] args) {
        // Generate and copy arrays
        int[] originalArray = generateUniqueNumbers();
        int[] sortedArray = Arrays.copyOf(originalArray, originalArray.length);

        // Analyze sequential search on unsorted array
        System.out.println("Analysis on Unsorted Array:");
        analyzeSearch(originalArray, "Sequential", false);

        // Sort the copy and measure sorting time
        long sortStartTime = System.nanoTime();
        Arrays.sort(sortedArray);
        long sortEndTime = System.nanoTime();
        double sortTimeMs = (sortEndTime - sortStartTime) / 1_000_000.0;
        System.out.println("\nTime to sort array: " + String.format("%.2f", sortTimeMs) + " ms");

        // Analyze all search methods on sorted array
        System.out.println("\nAnalysis on Sorted Array:");
        analyzeSearch(sortedArray, "Sequential", false);
        analyzeSearch(sortedArray, "Binary", false);
        analyzeSearch(sortedArray, "Binary", true);
    }
}