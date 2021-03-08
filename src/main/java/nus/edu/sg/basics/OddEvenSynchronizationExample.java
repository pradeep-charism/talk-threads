package nus.edu.sg.basics;

import java.util.concurrent.atomic.AtomicInteger;

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

    Object lock = new Object();

    AtomicInteger number = new AtomicInteger(0);

    public  int increment() {
        return number.incrementAndGet();
    }

    public  int currentValue() {
        return number.get();
    }
}

class OddNumberPrinter implements Runnable {

    NumberPrinter printer;

    public OddNumberPrinter(NumberPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        while (printer.currentValue() < 11) {
            if (printer.currentValue() % 2 == 0) {
                int increment = printer.increment();
                System.out.println(Thread.currentThread().getName() + "->" + increment);
            }

        }
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
        while (printer.currentValue() < 11) {
            if (printer.currentValue() % 2 == 1) {
                int increment = printer.increment();
                System.out.println(Thread.currentThread().getName() + "->" + increment);
            }
        }
    }
}