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

    private String[][] board = new String[ROWS][COLS];
    private Queue<Block> blockQueue = new LinkedList<>();
    private Stack<String[][]> gameStack = new Stack<>();
    private Block currentBlock;
    private int score = 0;
    private int level = 1;
    private boolean isGameOver = false;

    public QuestionNo3b() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setBackground(Color.BLACK);
        initializeBoard();
        generateNewBlock();
        addKeyListener(new KeyHandler());
        setFocusable(true);

        Timer timer = new Timer(500, e -> gameLoop());
        timer.start();
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = "empty";
            }
        }
    }

    private void generateNewBlock() {
        int[][] shape = getRandomShape();
        currentBlock = new Block(shape, getRandomColor());
        blockQueue.add(currentBlock);
    }

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

    private void move(int dx) {
        if (isValidPosition(currentBlock.x, currentBlock.y + dx, currentBlock.shape)) {
            currentBlock.y += dx;
            repaint();
        }
    }

    private void rotate() {
        int[][] rotatedShape = rotateMatrix(currentBlock.shape);
        if (isValidPosition(currentBlock.x, currentBlock.y, rotatedShape)) {
            currentBlock.shape = rotatedShape;
            repaint();
        }
    }
    
    



    private boolean moveDown() {
        if (isValidPosition(currentBlock.x + 1, currentBlock.y, currentBlock.shape)) {
            currentBlock.x++;
            return true;
        }
        return false;
    }

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

    private String[][] copyBoard() {
        String[][] copy = new String[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, COLS);
        }
        return copy;
    }

    private void checkRows() {
        for (int i = 0; i < ROWS; i++) {
            boolean fullRow = true;
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].equals("empty")) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) {
                removeRow(i);
                score += 10;
                if (score % 50 == 0) level++;
            }
        }
    }

    private void removeRow(int row) {
        for (int i = row; i > 0; i--) {
            System.arraycopy(board[i - 1], 0, board[i], 0, COLS);
        }
        for (int j = 0; j < COLS; j++) {
            board[0][j] = "empty";
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!board[i][j].equals("empty")) {
                    g.setColor(Color.decode(board[i][j]));
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private class KeyHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) move(-1);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) move(1);
            if (e.getKeyCode() == KeyEvent.VK_UP) rotate();
            if (e.getKeyCode() == KeyEvent.VK_DOWN) moveDown();
        }
    }

    private int[][] getRandomShape() {
        int[][][] shapes = {
            {{1, 1, 1}, {0, 1, 0}},  // T-shape
            {{1, 1}, {1, 1}},  // Square
            {{1, 1, 1, 1}},  // Line
            {{1, 0, 0}, {1, 1, 1}},  // L-shape
            {{0, 0, 1}, {1, 1, 1}}   // Reverse L-shape
        };
        return shapes[new Random().nextInt(shapes.length)];
    }

    private String getRandomColor() {
        String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF"};
        return colors[new Random().nextInt(colors.length)];
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        QuestionNo3b game = new QuestionNo3b();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

// Block Class for Tetris Pieces
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
