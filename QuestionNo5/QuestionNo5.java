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
    private Graph networkGraph;
    private JTextArea outputArea;
    private JTextField nodeField, edgeField, costField, bandwidthField;
    private JComboBox<String> startNode, endNode;

    public QuestionNo5() {
        setTitle("Network Optimizer");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        networkGraph = new Graph();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        nodeField = new JTextField();
        edgeField = new JTextField();
        costField = new JTextField();
        bandwidthField = new JTextField();
        startNode = new JComboBox<>();
        endNode = new JComboBox<>();

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

        JButton addNodeButton = new JButton("Add Node");
        JButton addEdgeButton = new JButton("Add Edge");
        JButton optimizeButton = new JButton("Optimize Network");
        JButton shortestPathButton = new JButton("Find Shortest Path");

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        addNodeButton.addActionListener(e -> addNode());
        addEdgeButton.addActionListener(e -> addEdge());
        optimizeButton.addActionListener(e -> optimizeNetwork());
        shortestPathButton.addActionListener(e -> findShortestPath());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addNodeButton);
        buttonPanel.add(addEdgeButton);
        buttonPanel.add(optimizeButton);
        buttonPanel.add(shortestPathButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    }

    private void addNode() {
        String node = nodeField.getText();
        if (!node.isEmpty()) {
            networkGraph.addNode(node);
            startNode.addItem(node);
            endNode.addItem(node);
            outputArea.append("Node added: " + node + "\n");
            nodeField.setText("");
        }
    }

    private void addEdge() {
        String[] nodes = edgeField.getText().split("-");
        if (nodes.length == 2) {
            String node1 = nodes[0].trim();
            String node2 = nodes[1].trim();
            int cost = Integer.parseInt(costField.getText());
            int bandwidth = Integer.parseInt(bandwidthField.getText());

            networkGraph.addEdge(node1, node2, cost, bandwidth);
            outputArea.append("Edge added: " + node1 + " - " + node2 + " (Cost: " + cost + ", Bandwidth: " + bandwidth + ")\n");

            edgeField.setText("");
            costField.setText("");
            bandwidthField.setText("");
        }
    }

    private void optimizeNetwork() {
        outputArea.append("Optimized Network Cost: " + networkGraph.findMST() + "\n");
    }

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

class Graph {
    private Map<String, java.util.List<Edge>> adjList = new HashMap<>();

    public void addNode(String node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String node1, String node2, int cost, int bandwidth) {
        adjList.get(node1).add(new Edge(node2, cost, bandwidth));
        adjList.get(node2).add(new Edge(node1, cost, bandwidth));
    }

    public int findMST() {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        Set<String> visited = new HashSet<>();
        int totalCost = 0;

        String start = adjList.keySet().iterator().next();
        visited.add(start);
        pq.addAll(adjList.get(start));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            if (!visited.contains(edge.node)) {
                visited.add(edge.node);
                totalCost += edge.cost;
                pq.addAll(adjList.get(edge.node));
            }
        }
        return totalCost;
    }

    public int dijkstra(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        distances.put(start, 0);
        pq.add(new Edge(start, 0, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            if (current.node.equals(end)) return current.cost;

            for (Edge neighbor : adjList.get(current.node)) {
                int newDist = current.cost + neighbor.cost;
                if (newDist < distances.getOrDefault(neighbor.node, Integer.MAX_VALUE)) {
                    distances.put(neighbor.node, newDist);
                    pq.add(new Edge(neighbor.node, newDist, neighbor.bandwidth));
                }
            }
        }
        return -1;
    }
}

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
