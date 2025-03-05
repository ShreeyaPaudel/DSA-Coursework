package QuestionNo4;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

// Problem Statement:
// This program analyzes a set of tweets and determines the top trending hashtags.
// Each tweet contains a unique ID and a hashtag.
// The goal is to count occurrences of hashtags and return the top 3 trending ones,
// sorted by frequency (descending) and then alphabetically in case of ties.

public class QuestionNo4a {
    // Function to get the top trending hashtags from tweets
    public static String getTopTrendingHashtags(String[][] tweets) {
        Map<String, Integer> hashtagCount = new HashMap<>(); // HashMap to store hashtag frequencies
        
        // Count occurrences of hashtags in tweets
        for (String[] tweet : tweets) {
            String hashtag = tweet[1]; // Assuming tweet[1] contains the hashtag
            hashtagCount.put(hashtag, hashtagCount.getOrDefault(hashtag, 0) + 1);
        }
        
        // Convert hashmap to a list of entries for sorting
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCount.entrySet());
        
        // Sort hashtags first by count (descending) and then alphabetically
        sortedHashtags.sort((a, b) -> {
            if (!b.getValue().equals(a.getValue())) {
                return b.getValue() - a.getValue(); // Sort by count descending
            }
            return a.getKey().compareTo(b.getKey()); // Sort alphabetically if count is the same
        });
        
        // Get the top 3 hashtags
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Math.min(3, sortedHashtags.size()); i++) {
            result.append(sortedHashtags.get(i).getKey()).append(" ")
                  .append(sortedHashtags.get(i).getValue()).append("\n");
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        // Example dataset containing tweet ID and hashtag
        String[][] tweets = {
            {"1", "#UniLife"},
            {"2", "#HappyFlowerDay"},
            {"3", "#WorkLife"},
            {"4", "#UniLife"},
            {"5", "#HappyFlowerDay"},
            {"6", "#HappyChocolateDay"},
            {"7", "#UniLife"},
            {"8", "#UniLife"},
            {"9", "#WorkLife"},
            {"10", "#HappyFlowerDay"},
        };

        // Display the top trending hashtags
        System.out.println(getTopTrendingHashtags(tweets));
    }
    
    /*
    Summary:
    - This program identifies the most frequently occurring hashtags in a dataset of tweets.
    - It uses a *HashMap* to store hashtag frequencies.
    - The hashtags are sorted based on their frequency in *descending order*.
    - If multiple hashtags have the same frequency, they are sorted *alphabetically*.
    - The program returns the *top 3 trending hashtags* with their respective counts.

    Test Output:
    Input:
    tweets = {
        {"1", "#UniLife"},
        {"2", "#HappyFlowerDay"},
        {"3", "#WorkLife"},
        {"4", "#UniLife"},
        {"5", "#HappyFlowerDay"},
        {"6", "#HappyChocolateDay"},
        {"7", "#UniLife"},
        {"8", "#UniLife"},
        {"9", "#WorkLife"},
        {"10", "#HappyFlowerDay"},
    }
    
    Output:
    #UniLife 4
    #HappyFlowerDay 3
    #WorkLife 2
    
    Explanation:
    - "#UniLife" appears *4 times*, making it the top trending hashtag.
    - "#HappyFlowerDay" appears *3 times*, making it the second trending hashtag.
    - "#WorkLife" appears *2 times*, making it the third trending hashtag.
    - "#HappyChocolateDay" appears only once and is not included in the top 3.
    */
}
