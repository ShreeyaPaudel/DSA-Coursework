// Description:
// This program demonstrates *multi-threading synchronization* in Java.
// Three threads are used to print a sequence where '0' is printed before every number:
// - One thread prints '0'.
// - One thread prints odd numbers.
// - One thread prints even numbers.
// The threads are synchronized using the *wait-notify* mechanism to ensure correct order.
// Example Output for n = 3: 0 1 0 2 0 3

package QuestionNo6;

// Class to define printing methods
class NumberPrinter {
    public void printZero() {
        System.out.print("0"); // Print zero
    }

    public void printEven(int number) {
        System.out.print(number); // Print even number
    }

    public void printOdd(int number) {
        System.out.print(number); // Print odd number
    }
}

// Controller class to manage synchronization between threads
class ThreadController {
    private final int maxCount; // Maximum number to print
    private int count = 1; // Start with 1 since 0 is always printed first
    private int turn = 0; // 0 -> ZeroThread, 1 -> OddThread, 2 -> EvenThread

    public ThreadController(int maxCount) {
        this.maxCount = maxCount;
    }

    // Synchronization method for printing zero
    public synchronized void printZero(NumberPrinter printer) {
        for (int i = 0; i < maxCount; i++) {
            while (turn != 0) { // Wait until it is ZeroThread's turn
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printer.printZero(); // Print '0'
            turn = (count % 2 == 0) ? 2 : 1; // Switch turn to OddThread or EvenThread
            notifyAll(); // Notify all waiting threads
        }
    }

    // Synchronization method for printing odd numbers
    public synchronized void printOdd(NumberPrinter printer) {
        while (count <= maxCount) {
            while (turn != 1) { // Wait until it is OddThread's turn
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printer.printOdd(count++); // Print odd number and increment count
            turn = 0; // Switch back to ZeroThread
            notifyAll(); // Notify all waiting threads
        }
    }

    // Synchronization method for printing even numbers
    public synchronized void printEven(NumberPrinter printer) {
        while (count <= maxCount) {
            while (turn != 2) { // Wait until it is EvenThread's turn
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printer.printEven(count++); // Print even number and increment count
            turn = 0; // Switch back to ZeroThread
            notifyAll(); // Notify all waiting threads
        }
    }
}

// Thread class for printing '0'
class ZeroThread extends Thread {
    private final NumberPrinter printer;
    private final ThreadController controller;

    public ZeroThread(NumberPrinter printer, ThreadController controller) {
        this.printer = printer;
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.printZero(printer);
    }
}

// Thread class for printing odd numbers
class OddThread extends Thread {
    private final NumberPrinter printer;
    private final ThreadController controller;

    public OddThread(NumberPrinter printer, ThreadController controller) {
        this.printer = printer;
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.printOdd(printer);
    }
}

// Thread class for printing even numbers
class EvenThread extends Thread {
    private final NumberPrinter printer;
    private final ThreadController controller;

    public EvenThread(NumberPrinter printer, ThreadController controller) {
        this.printer = printer;
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.printEven(printer);
    }
}

// Main class to start the threads
public class QuestionNo6a {
    public static void main(String[] args) {
        int n = 3; // Define the max number to print
        NumberPrinter printer = new NumberPrinter(); // Create printer instance
        ThreadController controller = new ThreadController(n); // Create controller instance

        ZeroThread zeroThread = new ZeroThread(printer, controller);
        OddThread oddThread = new OddThread(printer, controller);
        EvenThread evenThread = new EvenThread(printer, controller);

        zeroThread.start(); // Start ZeroThread
        oddThread.start(); // Start OddThread
        evenThread.start(); // Start EvenThread
    }
}

// Summary:
// - This program prints numbers in a synchronized way using multiple threads.
// - *ZeroThread* prints '0', *OddThread* prints odd numbers, and *EvenThread* prints even numbers.
// - *wait-notify* mechanism ensures that threads execute in a controlled order.
// - Threads communicate through the *ThreadController* class, which manages turn-based execution.
// - Example Output for n = 3: 0 1 0 2 0 3
// - The order ensures that '0' is printed before each number, alternating between odd and even numbers.

// Input:
// n = 3

// Expected Output:
// 0 1 0 2 0 3
