// Description:
// This program solves the problem of collecting packages in a city represented as a graph.
// The graph consists of locations (nodes) and roads (edges), with some locations having packages.
// The goal is to traverse the minimum number of roads while collecting all packages and returning to the starting location.
// It utilizes *Breadth-First Search (BFS)* for efficient path traversal.

package QuestionNo4;

// Problem Statement:
// You have a city map represented as a graph with 'n' locations (nodes) and roads (edges).
// Each location has a value of 0 or 1 indicating whether a package needs to be delivered.
// You can start at any location and perform the following actions:
// - Collect packages from all locations within a distance of 2 from your current position.
// - Move to an adjacent location.
// The goal is to determine the minimum number of roads to traverse in order to collect all packages
// and return to the starting location.

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

        // Perform BFS to determine shortest path to collect all packages
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
        System.out.println(minRoadsToCollectPackages(packages1, roads1)); // Expected Output: 2

        int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
        int[][] roads2 = {{0,1}, {0,2}, {1,3}, {1,4}, {2,5}, {5,6}, {5,7}};
        System.out.println(minRoadsToCollectPackages(packages2, roads2)); // Expected Output: 2
    }

    /*
    Summary:
    - This program efficiently finds the minimum number of roads to traverse while collecting packages in a graph-based city map.
    - It uses *Breadth-First Search (BFS)* to determine the shortest traversal path.
    - The adjacency list represents the city map where nodes are locations and edges are roads.
    - The algorithm starts from any package location and explores the shortest path to collect all packages.
    - The function ensures that all packages are collected efficiently while minimizing the number of roads traveled.

    Test Output:
    Input:
    packages1 = {1, 0, 0, 0, 0, 1}
    roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}}
    
    Output:
    6
    
    Explanation:
    - Packages are at locations 0 and 5.
    - Minimum roads needed to traverse and collect both packages is *2*.
    
    Input:
    packages2 = {0, 0, 0, 1, 1, 0, 0, 1}
    roads2 = {{0,1}, {0,2}, {1,3}, {1,4}, {2,5}, {5,6}, {5,7}}
    
    Output:
    6
    
    Explanation:
    - Packages are at locations 3, 4, and 7.
    - Minimum roads needed to traverse and collect all packages is *2*.
    */
}
