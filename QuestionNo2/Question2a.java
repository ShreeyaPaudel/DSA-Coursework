
package QuestionNo2;

import java.util.*;


//You have a team of n employees, and each employee is assigned a performance rating given in the
//integer array ratings. You want to assign rewards to these employees based on the following rules:
//Every employee must receive at least one reward.
//Employees with a higher rating must receive more rewards than their adjacent colleagues.
//Goal:
//Determine the minimum number of rewards you need to distribute to the employees.

public class Question2a {

    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        
        Integer[] indices = new Integer[n]; // Create an array of indices
        for (int i = 0; i < n; i++) {
            indices[i] = i; // Store indices
        }
        
        // Sort indices based on ratings (smallest rating first)
        Arrays.sort(indices, Comparator.comparingInt(i -> ratings[i]));
        
        int[] rewards = new int[n]; // Array to store rewards
        Arrays.fill(rewards, 1); // Start with 1 reward for each
        
        // Process employees in order of increasing rating
        for (int i = 0; i < n; i++) {
            int index = indices[i];
            
            // Check left neighbor and update rewards if necessary
            if (index > 0 && ratings[index] > ratings[index - 1]) {
                rewards[index] = Math.max(rewards[index], rewards[index - 1] + 1);
            }
            
            // Check right neighbor and update rewards if necessary
            if (index < n - 1 && ratings[index] > ratings[index + 1]) {
                rewards[index] = Math.max(rewards[index], rewards[index + 1] + 1);
            }
        }
        
        // Sum up total rewards
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }
        
        return totalRewards;
    }
    
    public static void main(String[] args) {
        int[] ratings1 = {1, 0, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings1)); // Output: 5

        int[] ratings2 = {1, 2, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings2)); // Output: 4
        }
}
    

