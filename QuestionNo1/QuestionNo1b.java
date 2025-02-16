package QuestionNo1;

import java.util.PriorityQueue;
import java.util.Arrays;



//You have two sorted arrays of investment returns, returns1 and returns2, and a target number k. You
//want to find the kth lowest combined return that can be achieved by selecting one investment from each
//of the array.


public class QuestionNo1b {

    
    // Class to store pairs of indices and their product
    static class Pair {
        int i, j;
        long product;
        
        Pair(int i, int j, long product) {
            this.i = i;
            this.j = j;
            this.product = product;
        }
    }
    
    public static long kthSmallestProduct(int[] returns1, int[] returns2, int k) {
        Arrays.sort(returns1);  // Ensure the arrays are sorted
        Arrays.sort(returns2);
        
        PriorityQueue<Pair> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a.product, b.product));
        
        // Push the smallest product pairs initially
        for (int i = 0; i < returns1.length; i++) {
            minHeap.offer(new Pair(i, 0, (long) returns1[i] * returns2[0]));
        }
        
        // Extract k times from the heap
        while (k-- > 1) {
            Pair current = minHeap.poll();
            int i = current.i, j = current.j;
            
            // If possible, push the next product from the same row
            if (j + 1 < returns2.length) {
                minHeap.offer(new Pair(i, j + 1, (long) returns1[i] * returns2[j + 1]));
            }
        }
        
        return minHeap.poll().product; // The kth smallest product
    }

    public static void main(String[] args) {
        int[] returns1 = {-4, -2, 0, 3}; // Example array 1
        int[] returns2 = {2, 4}; // Example array 2
        int k = 6; // Find the 6th smallest product
        System.out.println("The " + k + "th smallest product is: " + kthSmallestProduct(returns1, returns2, k)); // Print result
        }
}


    

