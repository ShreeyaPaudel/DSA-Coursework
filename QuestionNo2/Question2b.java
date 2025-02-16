package QuestionNo2;

import java.util.*;


//You have two points in a 2D plane, represented by the arrays x_coords and y_coords. The goal is to find
//the lexicographically pair i.e. (i, j) of points (one from each array) that are closest to each other

public class Question2b {



    // Function to find the pair of points with the shortest distance
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Number of points
        int minDist = Integer.MAX_VALUE; // Initialize the minimum distance to a very high value
        int[] result = new int[2]; // This will store the indices of the closest points

        // Go through every combination of two points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // Ensure i < j to avoid duplicate checks
                // Calculate the Manhattan distance between points (i, j)
                int dist = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // If this distance is the smallest we've seen, update the result
                if (dist < minDist) {
                    minDist = dist; // Update the smallest distance
                    result[0] = i; // Store the first index
                    result[1] = j; // Store the second index
                }
                // If the distance is the same as the current minimum, check for lexicographical order
                else if (dist == minDist) {
                    if (i < result[0] || (i == result[0] && j < result[1])) {
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }

        return result; // Return the indices of the closest pair
    }

    public static void main(String[] args) {
        // Example 1
        int[] x_coords1 = {1, 2, 3, 2, 4};
        int[] y_coords1 = {2, 3, 1, 2, 3};
        int[] closest1 = findClosestPair(x_coords1, y_coords1);
        System.out.println("Closest pair of indices: " + Arrays.toString(closest1)); // Output: [0, 3]

        // Example 2
        int[] x_coords2 = {0, 1, 2, 3};
        int[] y_coords2 = {0, 2, 4, 6};
        int[] closest2 = findClosestPair(x_coords2, y_coords2);
        System.out.println("Closest pair of indices: " + Arrays.toString(closest2)); // Output: [0, 1]
        }
}
    

