package me.edu.intermediate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author Pradeep Kumar
 * @create 3/9/2021 2:02 PM
 */
public class CountingNumberPrinter {

    private Map<Long, String> cache = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();


    private static int MAX_COUNT = 11;

    private AtomicInteger counter = new AtomicInteger(0);

    public synchronized void printOddNumbers() throws InterruptedException {
        while (counter.get() < MAX_COUNT) {
            if (counter.get() % 2 == 0) {
                System.out.println("Thread: "+ Thread.currentThread().getName() + "-> "+ counter.incrementAndGet());
                notify();
            }
            wait();
        }
    }

    public synchronized void printEvenNumbers() throws InterruptedException {
        while (counter.get() < MAX_COUNT) {
            if (counter.get() % 2 == 1) {
                System.out.println("Thread: "+ Thread.currentThread().getName() + "-> "+ counter.incrementAndGet());
                notify();
            }
            wait();
        }
    }

    public static void main(String[] args) {
        CountingNumberPrinter printer = new CountingNumberPrinter();

        class EvenProducer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                Thread.currentThread().setName("Even");
                printer.printEvenNumbers();
                return null;
            }
        }

        class OddProducer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                Thread.currentThread().setName("Odd");
                printer.printOddNumbers();
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
