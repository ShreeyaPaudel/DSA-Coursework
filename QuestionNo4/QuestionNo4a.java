package QuestionNo4;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;


public class QuestionNo4a {
    public static String getTopTrendingHashtags(String[][] tweets) {
        Map<String, Integer> hashtagCount = new HashMap<>();
        
        // Count occurrences of hashtags in tweets
        for (String[] tweet : tweets) {
            String hashtag = tweet[1]; // Assuming tweet[1] contains the hashtag
            hashtagCount.put(hashtag, hashtagCount.getOrDefault(hashtag, 0) + 1);
        }
        
        // Sort hashtags by count (descending) and then alphabetically
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCount.entrySet());
        sortedHashtags.sort((a, b) -> {
            if (!b.getValue().equals(a.getValue())) {
                return b.getValue() - a.getValue(); // Sort by count descending
            }
            return a.getKey().compareTo(b.getKey()); // Sort alphabetically
        });
        
        // Get the top 3 hashtags
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Math.min(3, sortedHashtags.size()); i++) {
            result.append(sortedHashtags.get(i).getKey()).append(" ").append(sortedHashtags.get(i).getValue()).append("\n");
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        // Example tweet dataset (tweet ID, hashtag)
        String[][] tweets = {
            {"1", "#TechLife"},
            {"2", "#HappyDay"},
            {"3", "#WorkLife"},
            {"4", "#TechLife"},
            {"5", "#HappyDay"},
            {"6", "#HappyDay"}
        };

        System.out.println(getTopTrendingHashtags(tweets));
    }
}
