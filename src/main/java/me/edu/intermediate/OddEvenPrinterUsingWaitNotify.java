package me.edu.intermediate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Pradeep Kumar
 * @create 3/9/2021 2:02 PM
 */
public class OddEvenPrinterUsingWaitNotify {

    private static final int MAX_COUNT = 11;

    private final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        OddEvenPrinterUsingWaitNotify printer = new OddEvenPrinterUsingWaitNotify();

        class EvenProducer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                Thread.currentThread().setName("Even");
                synchronized (printer) {
                    while (printer.counter.get() < MAX_COUNT) {
                        if (printer.counter.get() % 2 == 1) {
                            System.out.println("Thread: " + Thread.currentThread().getName() + "-> " + printer.counter.incrementAndGet());
                            printer.notify();
                        }
                        printer.wait();
                    }
                }
                return null;
            }
        }

        class OddProducer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                Thread.currentThread().setName("Odd");
                synchronized (printer) {
                    while (printer.counter.get() < MAX_COUNT) {
                        if (printer.counter.get() % 2 == 0) {
                            System.out.println("Thread: " + Thread.currentThread().getName() + "-> " + printer.counter.incrementAndGet());
                            printer.notify();
                        }
                        printer.wait();
                    }
                }
                return null;
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            List<Callable<Integer>> tasks = new ArrayList<>();
            tasks.add(new EvenProducer());
            tasks.add(new OddProducer());
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
