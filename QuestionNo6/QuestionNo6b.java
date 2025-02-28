package QuestionNo6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.*;

public class QuestionNo6b {

    // Data structure to store crawled URLs and results
    private static final ConcurrentLinkedQueue<String> urlsToCrawl = new ConcurrentLinkedQueue<>();
    private static final HashSet<String> crawledUrls = new HashSet<>();
    private static final ConcurrentHashMap<String, String> crawledData = new ConcurrentHashMap<>();

    // Executor service to manage threads
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    // Adds URLs to be crawled
    public static void addUrlToCrawl(String url) {
        if (!crawledUrls.contains(url)) {
            urlsToCrawl.add(url);
        }
    }

    // Crawler task for fetching and processing pages
    static class CrawlerTask implements Runnable {
        private final String url;

        public CrawlerTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                // Simulate fetching and processing the page
                String pageContent = fetchPageContent(url);
                // Store the crawled data
                crawledData.put(url, pageContent);

                // Mark URL as crawled
                crawledUrls.add(url);

                // Process the content, e.g., extract more URLs (This part is simplified)
                extractUrls(pageContent);
            } catch (Exception e) {
                System.err.println("Error processing URL: " + url);
            }
        }

        private String fetchPageContent(String url) throws Exception {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }

        private void extractUrls(String pageContent) {
            // For simplicity, assume that all URLs start with "http" (in real cases, a regex would be used)
            // In a real-world scenario, you would use an HTML parser like Jsoup to extract links
            if (pageContent.contains("http")) {
                addUrlToCrawl("http://example.com/nextpage");
            }
        }
    }

    // Start crawling with an initial URL
    public static void startCrawling(String initialUrl) {
        addUrlToCrawl(initialUrl);

        while (!urlsToCrawl.isEmpty()) {
            String url = urlsToCrawl.poll();
            if (!crawledUrls.contains(url)) {
                executorService.submit(new CrawlerTask(url));
            }
        }

        executorService.shutdown();
    }

    public static void main(String[] args) {
        // Example URL to start crawling
        String startingUrl = "http://example.com";

        startCrawling(startingUrl);

        // For demonstration, wait until the crawling is done
        try {
            // Allow some time for threads to finish
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Display the crawled data
        System.out.println("Crawled Data:");
        crawledData.forEach((url, content) -> {
            System.out.println("URL: " + url);
            System.out.println("Content: " + content.substring(0, Math.min(content.length(), 200)) + "...");
        });
    }
}
