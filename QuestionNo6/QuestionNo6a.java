package QuestionNo6;

class NumberPrinter {
    public synchronized void printZero() {
        System.out.print("0");
    }

    public synchronized void printEven(int number) {
        System.out.print(number);
    }

    public synchronized void printOdd(int number) {
        System.out.print(number);
    }
}

class ZeroThread extends Thread {
    private final NumberPrinter printer;
    private final ThreadController controller;

    public ZeroThread(NumberPrinter printer, ThreadController controller) {
        this.printer = printer;
        this.controller = controller;
    }

    @Override
    public void run() {
        synchronized (controller) {
            printer.printZero();
            controller.incrementCount();
            controller.notifyAll(); // Notify other threads
        }
    }
}

class EvenThread extends Thread {
    private final NumberPrinter printer;
    private final ThreadController controller;

    public EvenThread(NumberPrinter printer, ThreadController controller) {
        this.printer = printer;
        this.controller = controller;
    }

    @Override
    public void run() {
        synchronized (controller) {
            while (controller.getCount() <= controller.getMaxCount()) {
                if (controller.getCount() % 2 == 0) {
                    printer.printEven(controller.getCount());
                    controller.incrementCount();
                    controller.notifyAll(); // Notify other threads
                    try {
                        controller.wait(); // Wait for turn to print next
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        controller.wait(); // Wait until it's even number's turn
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class OddThread extends Thread {
    private final NumberPrinter printer;
    private final ThreadController controller;

    public OddThread(NumberPrinter printer, ThreadController controller) {
        this.printer = printer;
        this.controller = controller;
    }

    @Override
    public void run() {
        synchronized (controller) {
            while (controller.getCount() <= controller.getMaxCount()) {
                if (controller.getCount() % 2 != 0) {
                    printer.printOdd(controller.getCount());
                    controller.incrementCount();
                    controller.notifyAll(); // Notify other threads
                    try {
                        controller.wait(); // Wait for turn to print next
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        controller.wait(); // Wait until it's odd number's turn
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class ThreadController {
    private int count = 0;
    private final int maxCount;

    public ThreadController(int n) {
        this.maxCount = n;
    }

    public synchronized void incrementCount() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized int getMaxCount() {
        return maxCount;
    }

    public void startThreads() {
        NumberPrinter printer = new NumberPrinter();
        ZeroThread zeroThread = new ZeroThread(printer, this);
        EvenThread evenThread = new EvenThread(printer, this);
        OddThread oddThread = new OddThread(printer, this);

        zeroThread.start();
        evenThread.start();
        oddThread.start();

        try {
            zeroThread.join();
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class QuestionNo6a {
    public static void main(String[] args) {
        int n = 5; 
        ThreadController controller = new ThreadController(n);
        controller.startThreads();
    }
}
