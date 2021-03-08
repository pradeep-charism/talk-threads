package nus.edu.sg.basics;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 4:08 PM
 */
public class SynchronizationExample {

    public static void main(String[] args) {
        Printer printer = new Printer();
        OddPrinter oddPrinter = new OddPrinter(printer);
        EvenPrinter evenPrinter = new EvenPrinter(printer);

        Thread odd = new Thread(oddPrinter);
        odd.start();

        Thread even = new Thread(evenPrinter);
        even.start();
    }

}

class Printer {
    public synchronized void printNumbers(int n) {
        for (int i = 0; i < 21; i++) {
            System.out.println(i * n);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class OddPrinter implements Runnable {

    Printer printer;

    public OddPrinter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        printer.printNumbers(11);
    }
}

class EvenPrinter implements Runnable {

    Printer printer;

    public EvenPrinter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        printer.printNumbers(10);
    }
}