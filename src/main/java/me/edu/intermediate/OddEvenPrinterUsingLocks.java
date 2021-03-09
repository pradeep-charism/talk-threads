package me.edu.intermediate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Pradeep Kumar
 * @create 3/9/2021 3:30 PM
 */
public class OddEvenPrinterUsingLocks {

    private static final int MAX_COUNT = 11;
    Lock lock = new ReentrantLock();
    Condition evenCondition = lock.newCondition();
    Condition oddCondition = lock.newCondition();

    //    private final AtomicInteger counter = new AtomicInteger(0);
    private int counter = 0;

    public void printOddNumbers() throws InterruptedException {
        try {
            lock.lock();
            while (counter < MAX_COUNT) {
                if (counter % 2 == 0) {
                    counter++;
                    System.out.println("Thread: " + Thread.currentThread().getName() + "-> " + counter);
                    evenCondition.signal();
                }
                oddCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void printEvenNumbers() throws InterruptedException {
        try {
            lock.lock();
            while (counter < MAX_COUNT) {
                if (counter % 2 == 1) {
                    counter++;
                    System.out.println("Thread: " + Thread.currentThread().getName() + "-> " + counter);
                    oddCondition.signal();
                }
                evenCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        OddEvenPrinterUsingLocks printer = new OddEvenPrinterUsingLocks();


        class EvenNumberProducer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                Thread.currentThread().setName("Even");
                printer.printEvenNumbers();
                return null;
            }
        }

        class OddNumberProducer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                Thread.currentThread().setName("Odd");
                printer.printOddNumbers();
                return null;
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            List<Callable<Integer>> tasks = new ArrayList<>();
            tasks.add(new EvenNumberProducer());
            tasks.add(new EvenNumberProducer());
            tasks.add(new OddNumberProducer());
            tasks.add(new OddNumberProducer());
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
