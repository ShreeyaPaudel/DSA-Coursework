package QuestionNo3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class QuestionNo3b extends JPanel {
    private static final int ROWS = 20;
    private static final int COLS = 10;
    private static final int CELL_SIZE = 30;
    private static final int PREVIEW_SIZE = 4; // Size of the preview block

    private String[][] board = new String[ROWS][COLS];
    private Queue<Block> blockQueue = new LinkedList<>();
    private Stack<String[][]> gameStack = new Stack<>();
    private Block currentBlock;
    private int score = 0;
    private int level = 1;
    private boolean isGameOver = false;

    public QuestionNo3b() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE + 150, ROWS * CELL_SIZE));  // Added extra space for preview
        setBackground(Color.BLACK);
        initializeBoard();
        generateNewBlock();
        addKeyListener(new KeyHandler());
        setFocusable(true);

        Timer timer = new Timer(500, e -> gameLoop());
        timer.start();
    }

    // Initialize the game board with "empty" values
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = "empty";
            }
        }
    }

    // Generate a new random block from the queue
    private void generateNewBlock() {
        if (blockQueue.isEmpty()) {
            blockQueue.add(new Block(getRandomShape(), getRandomColor()));
        }
        currentBlock = blockQueue.poll();
        blockQueue.add(new Block(getRandomShape(), getRandomColor()));
    }

    // The main game loop for checking and updating the game state
    private void gameLoop() {
        if (isGameOver) return;

        if (!moveDown()) {
            placeBlock();
            checkRows();
            generateNewBlock();

            if (!isValidPosition(currentBlock.x, currentBlock.y, currentBlock.shape)) {
                isGameOver = true;
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
            }
        }
        repaint();
    }

    // Check if the block can move down
    private boolean moveDown() {
        if (isValidPosition(currentBlock.x + 1, currentBlock.y, currentBlock.shape)) {
            currentBlock.x++;
            return true;
        }
        return false;
    }

    // Method to move the block
    public void move(int deltaX, int deltaY) {
        if (isValidPosition(currentBlock.x + deltaX, currentBlock.y + deltaY, currentBlock.shape)) {
            currentBlock.x += deltaX;
            currentBlock.y += deltaY;
        }
    }

    // Rotate the block
    private void rotate() {
        int[][] rotatedShape = rotateMatrix(currentBlock.shape);
        if (isValidPosition(currentBlock.x, currentBlock.y, rotatedShape)) {
            currentBlock.shape = rotatedShape;
        }
    }

    // Check and clear full rows
    private void checkRows() {
        for (int i = 0; i < ROWS; i++) {
            boolean isFull = true;
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].equals("empty")) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                for (int k = i; k > 0; k--) {
                    System.arraycopy(board[k - 1], 0, board[k], 0, COLS);
                }
                for (int j = 0; j < COLS; j++) {
                    board[0][j] = "empty";  // Clear the top row
                }
                score += 100;  // Increment score for clearing a row
            }
        }
    }

    // Check if the block's position is valid on the board
    private boolean isValidPosition(int newX, int newY, int[][] shape) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardX = newX + i, boardY = newY + j;
                    if (boardX >= ROWS || boardY < 0 || boardY >= COLS || !board[boardX][boardY].equals("empty")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Place the current block on the board
    private void placeBlock() {
        for (int i = 0; i < currentBlock.shape.length; i++) {
            for (int j = 0; j < currentBlock.shape[i].length; j++) {
                if (currentBlock.shape[i][j] != 0) {
                    board[currentBlock.x + i][currentBlock.y + j] = currentBlock.color;
                }
            }
        }
        gameStack.push(copyBoard());
    }

    // Copy the current game board for undo functionality
    private String[][] copyBoard() {
        String[][] copy = new String[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, COLS);
        }
        return copy;
    }

    // Rotate the block's shape matrix
    private int[][] rotateMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }

    // Draw the game board and preview block
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the main game board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!board[i][j].equals("empty")) {
                    g.setColor(Color.decode(board[i][j]));
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // Draw the preview block
        drawPreviewBlock(g);
    }

    // Draw the preview block next to the game area
    private void drawPreviewBlock(Graphics g) {
        Block previewBlock = blockQueue.peek();  // Get the next block in the queue
        int[][] shape = previewBlock.shape;
        String color = previewBlock.color;

        g.setColor(Color.decode(color));

        // Draw the preview block in a small area
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    g.fillRect((COLS * CELL_SIZE) + (j * CELL_SIZE), i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    // Key handler to move and rotate the block
    private class KeyHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) move(-1, 0);  // Move left
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) move(1, 0);   // Move right
            if (e.getKeyCode() == KeyEvent.VK_UP) rotate();        // Rotate block
            if (e.getKeyCode() == KeyEvent.VK_DOWN) move(0, 1);    // Move down
        }
    }

    // Generate a random shape for the block
    private int[][] getRandomShape() {
        int[][][] shapes = {
            {{1, 1, 1}, {0, 1, 0}},
            {{1, 1}, {1, 1}},
            {{1, 1, 1, 1}},
            {{1, 0, 0}, {1, 1, 1}},
            {{0, 0, 1}, {1, 1, 1}}
        };
        return shapes[new Random().nextInt(shapes.length)];
    }

    // Generate a random color for the block
    private String getRandomColor() {
        String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF"};
        return colors[new Random().nextInt(colors.length)];
    }

    // Main method to start the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        QuestionNo3b game = new QuestionNo3b();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

// Block class representing each block's shape and color
class Block {
    int[][] shape;
    String color;
    int x, y;

    public Block(int[][] shape, String color) {
        this.shape = shape;
        this.color = color;
        this.x = 0;
        this.y = 4;
    }
}
