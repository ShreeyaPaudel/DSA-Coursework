package QuestionNo1;

import java.util.PriorityQueue;
import java.util.Arrays;

// Problem Statement:
// You have two sorted arrays of investment returns, returns1 and returns2, and a target number k.
// You need to find the kth lowest combined return that can be achieved by selecting one investment from each array.

public class QuestionNo1b {

    // Class to store pairs of indices and their product
    static class Pair {
        int i, j;  // Indices from the two arrays
        long product;  // Product of selected elements
        
        Pair(int i, int j, long product) {
            this.i = i;
            this.j = j;
            this.product = product;
        }
    }

    // Function to find the k-th smallest product formed by selecting one element from each array
    public static long kthSmallestProduct(int[] returns1, int[] returns2, int k) {
        Arrays.sort(returns1);  // Ensure the arrays are sorted
        Arrays.sort(returns2);  // Sorting is crucial for minHeap efficiency
        
        // Min-Heap to store the smallest product pairs
        PriorityQueue<Pair> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a.product, b.product));

        // Push the smallest product pairs initially (using the first element from returns2)
        for (int i = 0; i < returns1.length; i++) {
            minHeap.offer(new Pair(i, 0, (long) returns1[i] * returns2[0]));
        }

        // Extract the smallest product k times
        while (k-- > 1) {  // Run k-1 times to leave the k-th smallest product at the top
            Pair current = minHeap.poll();  // Remove the smallest element
            int i = current.i, j = current.j;  // Get the indices

            // If possible, push the next product from the same row (next element in returns2)
            if (j + 1 < returns2.length) {
                minHeap.offer(new Pair(i, j + 1, (long) returns1[i] * returns2[j + 1]));
            }
        }

        return minHeap.poll().product; // Return the k-th smallest product
    }

    public static void main(String[] args) {
        // Example input arrays
        int[] returns1 = {-4, -2, 0, 3}; // Sorted array 1
        int[] returns2 = {2, 4}; // Sorted array 2
        int k = 6; // Find the 6th smallest product

        // Compute and print the result
        System.out.println("The " + k + "th smallest product is: " + kthSmallestProduct(returns1, returns2, k));
    }

    /*
    Summary:
    - The problem requires finding the k-th smallest product by selecting one element from each array.
    - A min-heap (PriorityQueue) is used to efficiently extract the smallest product k times.
    - Initially, we push products using the first element of returns2 with each element of returns1.
    - We continuously extract the smallest product and insert the next possible product from the same row.
    - The solution works efficiently by limiting insertions and extractions using a heap.

    Test Results:
    Input:
    returns1 = {-4, -2, 0, 3}
    returns2 = {2, 4}
    k = 6

    Output:
    The 6th smallest product is: 0

    Explanation:
    The sorted product list (in ascending order) is:
    [-8, -4, -4, -2, 0, 6, 8, 12]
    The 6th smallest product is 0.
    */
}
