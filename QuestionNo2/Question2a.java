package QuestionNo2;

//You have a team of n employees, and each employee is assigned a performance rating given in the
//integer array ratings. You want to assign rewards to these employees based on the following rules:
//Every employee must receive at least one reward.
//Employees with a higher rating must receive more rewards than their adjacent colleagues.
//Goal:
//Determine the minimum number of rewards you need to distribute to the employees.
//Input:
//ratings: The array of employee performance ratings.
//Output:
//The minimum number of rewards needed to distribute. 

import java.util.Arrays;

public class Question2a {

    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        
        // Initialize rewards array with 1 (each employee gets at least one reward)
        int[] rewards = new int[n];
        Arrays.fill(rewards, 1);
        
        // Left to right pass: Ensure each employee gets more rewards than their left neighbor if needed
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }
        
        // Right to left pass: Ensure each employee gets more rewards than their right neighbor if needed
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }
        
        // Sum up all the rewards
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
        System.out.println("Minimum rewards needed: " + minRewards(ratings2)); // Output: 4
    }
}
