package QuestionNo6;

class NumberPrinter {
    public void printZero() {
        System.out.print("0");
    }

    public void printEven(int number) {
        System.out.print(number);
    }

    public void printOdd(int number) {
        System.out.print(number);
    }
}

class ThreadController {
    private final int maxCount;
    private int count = 1; // Start with 1 since 0 is always printed first
    private int turn = 0; // 0 -> ZeroThread, 1 -> OddThread, 2 -> EvenThread

    public ThreadController(int maxCount) {
        this.maxCount = maxCount;
    }

    public synchronized void printZero(NumberPrinter printer) {
        for (int i = 0; i < maxCount; i++) {
            while (turn != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printer.printZero();
            turn = (count % 2 == 0) ? 2 : 1; // Switch turn to OddThread or EvenThread
            notifyAll();
        }
    }

    public synchronized void printOdd(NumberPrinter printer) {
        while (count <= maxCount) {
            while (turn != 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printer.printOdd(count++);
            turn = 0; // Switch back to ZeroThread
            notifyAll();
        }
    }

    public synchronized void printEven(NumberPrinter printer) {
        while (count <= maxCount) {
            while (turn != 2) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printer.printEven(count++);
            turn = 0; // Switch back to ZeroThread
            notifyAll();
        }
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
        controller.printZero(printer);
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
        controller.printOdd(printer);
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
        controller.printEven(printer);
    }
}

public class QuestionNo6a {
    public static void main(String[] args) {
        int n = 3;
        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(n);

        ZeroThread zeroThread = new ZeroThread(printer, controller);
        OddThread oddThread = new OddThread(printer, controller);
        EvenThread evenThread = new EvenThread(printer, controller);

        zeroThread.start();
        oddThread.start();
        evenThread.start();
    }
}
