// Description:
// This program is a *Network Optimizer* that helps optimize network connections between nodes.
// It allows users to:
// - *Add nodes* to the network.
// - *Connect nodes* with edges that have cost and bandwidth.
// - *Find the shortest path* between two nodes using *Dijkstra's Algorithm*.
// - *Optimize network cost* using *Minimum Spanning Tree (MST)* with *Prim's Algorithm*.
//
// The program provides a graphical user interface (GUI) built with *Java Swing* to manage and analyze networks.



package QuestionNo5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class QuestionNo5 extends JFrame {
    private Graph networkGraph; // Graph instance to store nodes and edges
    private JTextArea outputArea;  // Text area to display results
    private JTextField nodeField, edgeField, costField, bandwidthField;  // Input fields
    private JComboBox<String> startNode, endNode; // Dropdowns for selecting start and end nodes

    public QuestionNo5() {
        // Set the main frame properties
        setTitle("Network Optimizer");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        networkGraph = new Graph(); // Initialize the graph

        // Create input panel for user interaction
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));  // 6 rows, 2 columns layout

        // Initialize input fields
        nodeField = new JTextField();
        edgeField = new JTextField();
        costField = new JTextField();
        bandwidthField = new JTextField();
        startNode = new JComboBox<>();
        endNode = new JComboBox<>();

        // Add input fields and labels to the panel
        inputPanel.add(new JLabel("Node:"));
        inputPanel.add(nodeField);
        inputPanel.add(new JLabel("Edge (A-B):"));
        inputPanel.add(edgeField);
        inputPanel.add(new JLabel("Cost:"));
        inputPanel.add(costField);
        inputPanel.add(new JLabel("Bandwidth:"));
        inputPanel.add(bandwidthField);
        inputPanel.add(new JLabel("Start Node:"));
        inputPanel.add(startNode);
        inputPanel.add(new JLabel("End Node:"));
        inputPanel.add(endNode);

        // Buttons for different actions
        JButton addNodeButton = new JButton("Add Node");
        JButton addEdgeButton = new JButton("Add Edge");
        JButton optimizeButton = new JButton("Optimize Network");
        JButton shortestPathButton = new JButton("Find Shortest Path");

        outputArea = new JTextArea(); // Text area for displaying results
        outputArea.setEditable(false);  // Prevent user from editing the output

        // Add action listeners to buttons
        addNodeButton.addActionListener(e -> addNode());
        addEdgeButton.addActionListener(e -> addEdge());
        optimizeButton.addActionListener(e -> optimizeNetwork());
        shortestPathButton.addActionListener(e -> findShortestPath());

        // Create a panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addNodeButton);
        buttonPanel.add(addEdgeButton);
        buttonPanel.add(optimizeButton);
        buttonPanel.add(shortestPathButton);

        // Add components to the main frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH); // Scrollable text area
    }

    // Method to add a new node to the graph
    private void addNode() {
        String node = nodeField.getText();  // Get user input
        if (!node.isEmpty()) {
            networkGraph.addNode(node); // Add node to graph
            startNode.addItem(node);  // Update dropdown options
            endNode.addItem(node);
            outputArea.append("Node added: " + node + "\n");  // // Show confirmation
            nodeField.setText("");  // Clear input field
        }
    }

    // Method to add an edge between two nodes

    private void addEdge() {
        String[] nodes = edgeField.getText().split("-");  // Split input format "A-B"
        if (nodes.length == 2) {
            String node1 = nodes[0].trim();
            String node2 = nodes[1].trim();
            int cost = Integer.parseInt(costField.getText());  // Convert cost to integer
            int bandwidth = Integer.parseInt(bandwidthField.getText());  // Convert bandwidth to integer

            networkGraph.addEdge(node1, node2, cost, bandwidth);  // Add edge to graph
            outputArea.append("Edge added: " + node1 + " - " + node2 + " (Cost: " + cost + ", Bandwidth: " + bandwidth + ")\n");

             // Clear input fields after adding edge
            edgeField.setText("");
            costField.setText("");
            bandwidthField.setText("");
        }
    }

    // Method to optimize network cost using Prim’s MST algorithm
    private void optimizeNetwork() {
        outputArea.append("Optimized Network Cost: " + networkGraph.findMST() + "\n");
    }

    // Method to find the shortest path between selected nodes using Dijkstra's Algorithm
    private void findShortestPath() {
        String start = (String) startNode.getSelectedItem();
        String end = (String) endNode.getSelectedItem();
        if (start != null && end != null) {
            outputArea.append("Shortest Path from " + start + " to " + end + ": " + networkGraph.dijkstra(start, end) + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuestionNo5().setVisible(true));
    }
}

// Graph class to store nodes and edges
class Graph {
    private Map<String, java.util.List<Edge>> adjList = new HashMap<>(); //// Adjacency list representation

     // Add a new node to the graph
    public void addNode(String node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    // Add an edge between two nodes with cost and bandwidth
    public void addEdge(String node1, String node2, int cost, int bandwidth) {
        adjList.get(node1).add(new Edge(node2, cost, bandwidth));
        adjList.get(node2).add(new Edge(node1, cost, bandwidth));
    }

    // Find the Minimum Spanning Tree (MST) using Prim's Algorithm
    public int findMST() {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));  // Min heap for edges
        Set<String> visited = new HashSet<>();
        int totalCost = 0;

        // Start from any node in the graph
        String start = adjList.keySet().iterator().next();
        visited.add(start);
        pq.addAll(adjList.get(start));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();  // Get the edge with the lowest cost
            if (!visited.contains(edge.node)) {
                visited.add(edge.node);
                totalCost += edge.cost;  // Add cost to total
                pq.addAll(adjList.get(edge.node));  // Add edges of the new node
            }
        }
        return totalCost;
    }

    
    // Find shortest path using Dijkstra’s Algorithm
    public int dijkstra(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        distances.put(start, 0);
        pq.add(new Edge(start, 0, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();   // Get the shortest known path
            if (current.node.equals(end)) return current.cost;  // Return cost if destination reached

            for (Edge neighbor : adjList.get(current.node)) {
                int newDist = current.cost + neighbor.cost;
                if (newDist < distances.getOrDefault(neighbor.node, Integer.MAX_VALUE)) {
                    distances.put(neighbor.node, newDist);
                    pq.add(new Edge(neighbor.node, newDist, neighbor.bandwidth));
                }
            }
        }
        return -1; // Return -1 if no path exists
    }
}

// Edge class to represent connections between nodes
class Edge {
    String node;
    int cost, bandwidth;
    public Edge(String node, int cost, int bandwidth) {
        this.node = node;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }
}

// Summary:
// - This program helps manage and optimize networks by providing a GUI for adding nodes and edges.
// - *Prim’s Algorithm* is used to find the *Minimum Spanning Tree (MST)* for network cost optimization.
// - *Dijkstra's Algorithm* is implemented to determine the *shortest path* between two nodes.
// - The GUI allows users to interactively build and analyze network structures.
// - Outputs are displayed in a text area, showing added nodes, edges, shortest paths, and optimized network costs.
