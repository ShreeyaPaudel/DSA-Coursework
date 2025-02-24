package QuestionNo4;

//You have a map of a city represented by a graph with n nodes (representing locations) and edges where
//edges[i] = [ai, bi] indicates a road between locations ai and bi. Each location has a value of either 0 or 1,
//indicating whether there is a package to be delivered. You can start at any location and perform the
//following actions:
//Collect packages from all locations within a distance of 2 from your current location.
//Move to an adjacent location.
//Your goal is to collect all packages and return to your starting location.
//Goal:
//Determine the minimum number of roads you need to traverse to collect all packages.
//Input:
//packages: An array of package values for each location.
//roads: A 2D array representing the connections between locations.
//Output:
//The minimum number of roads to traverse.


import java.util.*;

public class QuestionNo4b {
    public static int minRoadsToCollectPackages(int[] packages, int[][] roads) {
        int n = packages.length;
        List<List<Integer>> graph = new ArrayList<>();

        // Initialize the graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Build adjacency list for the graph
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }

        // Find all locations with packages
        Set<Integer> packageLocations = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) {
                packageLocations.add(i);
            }
        }

        // BFS to find shortest paths
        return bfs(graph, packageLocations);
    }

    private static int bfs(List<List<Integer>> graph, Set<Integer> packageLocations) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        int steps = 0;

        // Start BFS from any package location
        int start = packageLocations.iterator().next();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int node = queue.poll();

                // If all packages are collected, return the steps taken
                if (packageLocations.isEmpty()) return steps;

                // Explore neighbors
                for (int neighbor : graph.get(node)) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        packageLocations.remove(neighbor);
                    }
                }
            }
            steps++;
        }

        return steps;
    }

    public static void main(String[] args) {
        int[] packages1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println(minRoadsToCollectPackages(packages1, roads1)); // Output: 2

        int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
        int[][] roads2 = {{0,1}, {0,2}, {1,3}, {1,4}, {2,5}, {5,6}, {5,7}};
        System.out.println(minRoadsToCollectPackages(packages2, roads2)); // Output: 2
    }
}
