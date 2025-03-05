package QuestionNo2;

import java.util.*;

// Problem Statement:
// You have two points in a 2D plane, represented by the arrays x_coords and y_coords.
// The goal is to find the lexicographically smallest pair (i, j) of points (one from each array) that are closest to each other.

public class Question2b {

    // Function to find the pair of points with the shortest Manhattan distance
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Number of points
        int minDist = Integer.MAX_VALUE; // Initialize the minimum distance to a very high value
        int[] result = new int[2]; // Array to store the indices of the closest points

        // Iterate through all possible pairs of points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // Ensure i < j to avoid duplicate checks
                // Calculate the Manhattan distance between points (i, j)
                int dist = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // If this distance is the smallest seen so far, update the result
                if (dist < minDist) {
                    minDist = dist; // Update the smallest distance found
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
        int[] x_coords1 = {1, 2, 3, 2, 4}; // X-coordinates of points
        int[] y_coords1 = {2, 3, 1, 2, 3}; // Y-coordinates of points
        int[] closest1 = findClosestPair(x_coords1, y_coords1);
        System.out.println("Closest pair of indices: " + Arrays.toString(closest1)); // Expected Output: [0, 3]

        // Example 2
        int[] x_coords2 = {0, 1, 2, 3}; // X-coordinates of points
        int[] y_coords2 = {0, 2, 4, 6}; // Y-coordinates of points
        int[] closest2 = findClosestPair(x_coords2, y_coords2);
        System.out.println("Closest pair of indices: " + Arrays.toString(closest2)); // Expected Output: [0, 1]
    }

    /*
    Summary:
    - This problem requires finding the closest pair of points in a 2D plane using Manhattan distance.
    - Manhattan distance is calculated as: |x1 - x2| + |y1 - y2|.
    - A brute-force approach is used to check all possible pairs, as the number of points is relatively small.
    - If multiple pairs have the same minimum distance, the lexicographically smallest pair (smallest i, then smallest j) is selected.
    
    Test Results:
    Input:
    x_coords1 = {1, 2, 3, 2, 4}
    y_coords1 = {2, 3, 1, 2, 3}
    
    Output:
    Closest pair of indices: [0, 3]

    Explanation:
    - The pair (0,3) has the minimum Manhattan distance of 1.

    Input:
    x_coords2 = {0, 1, 2, 3}
    y_coords2 = {0, 2, 4, 6}

    Output:
    Closest pair of indices: [0, 3]
    Closest pair of indices: [0, 1]

    Explanation:
    - The pair (0,1) has the minimum Manhattan distance of 2.
    */
}
