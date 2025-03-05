package QuestionNo2;

// Problem Statement:
// You have a team of n employees, and each employee is assigned a performance rating given in the
// integer array ratings. Rewards must be assigned based on the following rules:
// - Every employee must receive at least one reward.
// - Employees with a higher rating must receive more rewards than their adjacent colleagues.
//
// Goal:
// Determine the minimum number of rewards required to satisfy these conditions.

import java.util.Arrays;

public class Question2a {

    // Function to determine the minimum number of rewards needed
    public static int minRewards(int[] ratings) {
        int n = ratings.length; // Number of employees
        if (n == 0) return 0; // Edge case: No employees
        
        // Initialize rewards array with 1 (each employee gets at least one reward)
        int[] rewards = new int[n];
        Arrays.fill(rewards, 1);
        
        // Left to right pass: Ensure each employee gets more rewards than their left neighbor if needed
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) { // If current employee has a higher rating than the left neighbor
                rewards[i] = rewards[i - 1] + 1; // Give one more reward than the left neighbor
            }
        }
        
        // Right to left pass: Ensure each employee gets more rewards than their right neighbor if needed
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) { // If current employee has a higher rating than the right neighbor
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1); // Give max of the existing reward or one more than the right neighbor
            }
        }
        
        // Sum up all the rewards to get the minimum total required
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }
        
        return totalRewards; // Return the minimum number of rewards required
    }

    public static void main(String[] args) {
        // Test case 1
        int[] ratings1 = {1, 0, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings1)); // Expected Output: 5

        // Test case 2
        int[] ratings2 = {1, 2, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings2)); // Expected Output: 4
    }

    /*
    Summary:
    - The problem requires assigning rewards based on performance ratings while ensuring fairness.
    - A greedy approach is used with two passes:
        1. Left-to-right: Ensures each employee gets more rewards than their left neighbor if needed.
        2. Right-to-left: Ensures each employee gets more rewards than their right neighbor if needed.
    - The rewards array stores the minimum rewards each employee must receive.
    - The final total is calculated by summing up the rewards array.

    Test Results:
    Input:
    ratings1 = {1, 0, 2}
    ratings2 = {1, 2, 2}

    Output:
    Minimum rewards needed: 5
    Minimum rewards needed: 4

    Explanation:
    - For ratings1 = {1, 0, 2}:
      Rewards: {2, 1, 2} → Total = 5
    - For ratings2 = {1, 2, 2}:
      Rewards: {1, 2, 1} → Total = 4
    */
}
