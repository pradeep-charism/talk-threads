package nus.edu.sg.intermediate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 11:39 PM
 */
public class ProducerConsumerWithLocks {

    List<Integer> buffer = new ArrayList<>();


    Lock lock = new ReentrantLock();
    Condition isEmpty = lock.newCondition();
    Condition isFull = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        ProducerConsumerWithLocks example = new ProducerConsumerWithLocks();
        List<Producer> producers = example.createProducers();
        List<Consumer> consumers = example.createConsumers();

        List<Callable<String>> producersAndConsumers = new ArrayList<>();
        producersAndConsumers.addAll(producers);
        producersAndConsumers.addAll(consumers);

        ExecutorService executorService = Executors.newFixedThreadPool(8);

        try {
            List<Future<String>> futures = executorService.invokeAll(producersAndConsumers);

            futures.forEach(
                    future -> {
                        try {
                            System.out.println(future.get());
                        } catch (Exception e) {
                            System.out.println("Exception: " + e.getMessage());
                        }

                    });
        } finally {
            executorService.shutdown();
            System.out.println("Executor is shutdown");
        }
    }

    private List<Consumer> createConsumers() {
        List<Consumer> producers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            producers.add(new Consumer());
        }
        return producers;
    }

    private List<Producer> createProducers() {
        List<Producer> producers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            producers.add(new Producer());
        }
        return producers;
    }

    class Consumer implements Callable<String> {
        @Override
        public String call() throws Exception {
            int count = 0;
            while (count++ < 50) {
                try {
                    lock.lock();
                    while (buffer.isEmpty()) {
                        //wait
                        isEmpty.await();
                    }
                    buffer.remove(buffer.size() - 1);
                    isFull.signalAll();
                } finally {
                    lock.unlock();
                }
            }
            return "Consumed " + (count - 1);
        }
    }

    class Producer implements Callable<String> {

        @Override
        public String call() throws Exception {
            int count = 0;
            while (count++ < 50) {
                try {
                    lock.lock();
                    while (buffer.size() == 50) {
                        //wait
                        isFull.await();
                    }
                    buffer.add(1);
                    //signal
                    isEmpty.signalAll();

                } finally {
                    lock.unlock();
                }
            }
            return "Produced " + (count - 1);
        }
    }
}
