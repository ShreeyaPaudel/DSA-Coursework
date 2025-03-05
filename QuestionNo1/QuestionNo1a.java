package QuestionNo1;

// a)
// Problem Statement:
// You have a material with 'n' temperature levels. There exists a critical temperature 'f' (0 <= f <= n),
// where the material will react or change properties at temperatures higher than 'f', but remain unchanged at or below 'f'.
// Given 'k' samples, determine the minimum number of tests required to find the critical temperature 'f'.

public class QuestionNo1a {

    // Function to calculate the minimum number of measurements required to find the critical temperature
    public static int minMeasurements(int k, int n) {
        // Create a 2D array to store results for different samples (k) and temperature levels (n)
        int[][] dp = new int[k + 1][n + 1];

        // Base case: If we have only one sample, we need to check each level sequentially (worst-case scenario)
        for (int j = 1; j <= n; j++) {
            dp[1][j] = j; // Linear search is required for 1 sample
        }

        // Compute results for multiple samples (k) and temperature levels (n)
        for (int i = 2; i <= k; i++) {  // Loop over the number of samples
            for (int j = 1; j <= n; j++) {  // Loop over temperature levels
                dp[i][j] = Integer.MAX_VALUE; // Initialize with a large number

                // Try dropping at different temperature levels and find the minimum worst-case attempts
                for (int x = 1; x <= j; x++) {
                    // If the material reacts at x, we check below x (dp[i - 1][x - 1])
                    // If it doesn't react, we check above x (dp[i][j - x])
                    int worstCase = 1 + Math.max(dp[i - 1][x - 1], dp[i][j - x]);

                    // Store the minimum number of tests required in the worst case
                    dp[i][j] = Math.min(dp[i][j], worstCase);
                }
            }
        }

        // Return the minimum tests required for k samples and n temperature levels
        return dp[k][n];
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        // Test cases with different numbers of samples (k) and temperature levels (n)
        int k1 = 1, n1 = 2;
        int k2 = 2, n2 = 6;
        int k3 = 3, n3 = 6;

        // Print the results for given values of k and n
        System.out.println("Minimum tests for k = " + k1 + ", n = " + n1 + ": " + minMeasurements(k1, n1));
        System.out.println("Minimum tests for k = " + k2 + ", n = " + n2 + ": " + minMeasurements(k2, n2));
        System.out.println("Minimum tests for k = " + k3 + ", n = " + n3 + ": " + minMeasurements(k3, n3));
    }

    /*
    Summary:
    - The problem is a variation of the "Egg Dropping Problem," where we try to minimize the number of tests required to determine the critical temperature 'f'.
    - A dynamic programming approach is used with a dp[k][n] table, where dp[i][j] represents the minimum tests required using i samples for j temperature levels.
    - The worst-case scenario is considered when choosing the best temperature level to drop from.
    - The algorithm efficiently finds the minimum tests required.
    
    Test Results:
    Input & Output:
    Minimum tests for k = 1, n = 2: 2
    Minimum tests for k = 2, n = 6: 3
    Minimum tests for k = 3, n = 6: 3
    
    - For k = 1, n = 2: Since only one sample is available, it has to be tested sequentially.
    - For k = 2, n = 6: Using two samples, the optimal number of tests is 3.
    - For k = 3, n = 6: With three samples, the minimum tests remain at 3.
    */
}
