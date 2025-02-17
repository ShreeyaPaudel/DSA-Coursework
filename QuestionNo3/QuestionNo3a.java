package QuestionNo3;

//You have a network of n devices. Each device can have its own communication module installed at a
//cost of modules [i - 1]. Alternatively, devices can communicate with each other using direct connections.
//The cost of connecting two devices is given by the array connections where each connections[j] =
//[device1j, device2j, costj] represents the cost to connect devices device1j and device2j. Connections are
//bidirectional, and there could be multiple valid connections between the same two devices with different
//costs.

import java.util.*;

public class QuestionNo3a {
    static class KruskalSolution {
        static class DSU {
            int[] parent, rank;

            public DSU(int n) {
                parent = new int[n];
                rank = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    rank[i] = 1;
                }
            }

            public int find(int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]); // Path compression
                }
                return parent[x];
            }

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

        public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
            List<int[]> edges = new ArrayList<>();

            // Step 1: Add direct connection edges
            for (int[] conn : connections) {
                int u = conn[0] - 1, v = conn[1] - 1, cost = conn[2];
                edges.add(new int[]{cost, u, v});
            }

            // Step 2: Add module installation as an edge connecting device to itself
            for (int i = 0; i < n; i++) {
                edges.add(new int[]{modules[i], i, n}); // Virtual node n represents module installation
            }

            // Step 3: Sort all edges by cost (Kruskal’s Algorithm)
            edges.sort(Comparator.comparingInt(a -> a[0]));

            // Step 4: Apply Union-Find (DSU) to build MST
            DSU dsu = new DSU(n + 1);
            int totalCost = 0, edgesUsed = 0;

            for (int[] edge : edges) {
                int cost = edge[0], u = edge[1], v = edge[2];

                if (dsu.union(u, v)) { // If adding this edge doesn't form a cycle
                    totalCost += cost;
                    edgesUsed++;

                    if (edgesUsed == n) break; // MST is complete
                }
            }

            return totalCost;
        }

        public static void main(String[] args) {
            int n = 3;
            int[] modules = {1, 2, 2};
            int[][] connections = {{1, 2, 1}, {2, 3, 1}};
            System.out.println(minCostToConnectDevices(n, modules, connections)); // Output: 3
        }
    }
}
