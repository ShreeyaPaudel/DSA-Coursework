package QuestionNo3;

import java.util.*;

// Problem Statement:
// You have a network of 'n' devices. Each device can have its own communication module installed at a
// cost given by the array modules[i - 1]. Alternatively, devices can communicate with each other using direct connections.
// The cost of connecting two devices is given by the array connections, where each connection[j] =
// [device1j, device2j, costj] represents the cost to connect devices device1j and device2j.
// Connections are bidirectional, and there could be multiple valid connections between the same two devices with different costs.
// 
// Goal:
// Determine the minimum cost to connect all devices using the cheapest combination of module installations and direct connections.

public class QuestionNo3a {
    static class KruskalSolution {
        // Disjoint Set Union (DSU) or Union-Find to manage connected components
        static class DSU {
            int[] parent, rank;

            public DSU(int n) {
                parent = new int[n]; // Parent array for union-find structure
                rank = new int[n];   // Rank array to optimize union operations
                for (int i = 0; i < n; i++) {
                    parent[i] = i;  // Each node is initially its own parent
                    rank[i] = 1;    // Initial rank is 1
                }
            }

            // Find operation with path compression
            public int find(int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]); // Path compression to flatten tree structure
                }
                return parent[x];
            }

            // Union operation with rank optimization
            public boolean union(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                if (rootX == rootY) return false; // Already connected

                // Union by rank
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                return true;
            }
        }

        // Function to compute the minimum cost to connect all devices
        public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
            List<int[]> edges = new ArrayList<>();

            // Step 1: Add direct connection edges from connections array
            for (int[] conn : connections) {
                int u = conn[0] - 1, v = conn[1] - 1, cost = conn[2]; // Convert to 0-based indexing
                edges.add(new int[]{cost, u, v});
            }

            // Step 2: Add module installation as an edge connecting the device to itself
            // Virtual node n represents module installation for each device
            for (int i = 0; i < n; i++) {
                edges.add(new int[]{modules[i], i, n}); 
            }

            // Step 3: Sort all edges by cost (Kruskal’s Algorithm)
            edges.sort(Comparator.comparingInt(a -> a[0]));

            // Step 4: Apply Union-Find (DSU) to build the Minimum Spanning Tree (MST)
            DSU dsu = new DSU(n + 1); // Include virtual node
            int totalCost = 0, edgesUsed = 0;

            for (int[] edge : edges) {
                int cost = edge[0], u = edge[1], v = edge[2];

                if (dsu.union(u, v)) { // If adding this edge doesn't form a cycle
                    totalCost += cost;
                    edgesUsed++;

                    if (edgesUsed == n) break; // If we have connected all devices, stop
                }
            }

            return totalCost; // Return the minimum total cost
        }

        public static void main(String[] args) {
            // Example test case
            int n = 3; // Number of devices
            int[] modules = {1, 2, 2}; // Module installation costs
            int[][] connections = {{1, 2, 1}, {2, 3, 1}}; // Direct connection costs

            // Compute and print the minimum cost to connect all devices
            System.out.println("Minimum cost to connect devices: " + minCostToConnectDevices(n, modules, connections)); // Expected Output: 3
        }
    }

    /*
    Summary:
    - The problem requires finding the minimum cost to connect all devices using either direct connections or module installations.
    - This is solved using *Kruskal’s Algorithm* with *Union-Find (DSU)* to build a Minimum Spanning Tree (MST).
    - We treat module installation as a virtual node that connects each device with its corresponding installation cost.
    - Steps:
        1. Add all direct connection edges.
        2. Add module installation as edges to a virtual node.
        3. Sort all edges by cost.
        4. Use Kruskal’s Algorithm to form the MST.
    - The algorithm ensures that we use the cheapest possible connections to link all devices.

    Test Results:
    Input:
    n = 3
    modules = {1, 2, 2}
    connections = {{1, 2, 1}, {2, 3, 1}}

    Output:
    Minimum cost to connect devices: 3

    Explanation:
    - The best way to connect all devices is:
      - Connect device 1 to device 2 with cost 1
      - Connect device 2 to device 3 with cost 1
      - Install a module on device 1 with cost 1
    - Total cost: 1 + 1 + 1 = 3RY79
    */
}
