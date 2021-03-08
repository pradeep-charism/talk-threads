package nus.edu.sg.basics;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 4:08 PM
 */
public class OddEvenSynchronizationExample {

    public static void main(String[] args) {
        NumberPrinter printer = new NumberPrinter();
        OddNumberPrinter OddNumberPrinter = new OddNumberPrinter(printer);
        EvenNumberPrinter EvenNumberPrinter = new EvenNumberPrinter(printer);

        Thread odd = new Thread(OddNumberPrinter);
        odd.start();

        Thread even = new Thread(EvenNumberPrinter);
        even.start();
    }

}

class NumberPrinter {

    volatile int number = 0;

    public  synchronized int printNumbers(String printerName) {
        if (number != 20) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                System.out.println("Sleep is interrupted");
            }
            System.out.println(printerName + "::" + number++);
            return number;
        }
        return -1;
    }
}

class OddNumberPrinter implements Runnable {

    int limitReached;
    NumberPrinter printer;

    public OddNumberPrinter(NumberPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        do {
            limitReached = printer.printNumbers("OddNumberPrinter");
        } while (limitReached != -1);
    }
}

class EvenNumberPrinter implements Runnable {

    int limitReached;
    NumberPrinter printer;

    public EvenNumberPrinter(NumberPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        do {
            limitReached = printer.printNumbers("EvenNumberPrinter");
        } while (limitReached != -1);
    }
}