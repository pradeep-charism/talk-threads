package me.edu.intermediate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author Pradeep Kumar
 * @create 3/9/2021 2:02 PM
 */
public class CacheWithReadWriteLockExample {

    private Map<Long, String> cache = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public String put(Long key, String value) {
        writeLock.lock();
        try {
            System.out.println("Thread: "+ Thread.currentThread().getName() + " is adding value for Key: "+ key + " Value: "+ value);
            return cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public String get(Long key) {
        try {
            readLock.lock();
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        CacheWithReadWriteLockExample cacheExample = new CacheWithReadWriteLockExample();

        class Producer implements Callable<String> {

            private Random random = new Random();

            @Override
            public String call() throws Exception {
                while (true) {
                    long key = random.nextLong();
                    cacheExample.put(key, Long.toString(key));
                    if (cacheExample.get(key) == null) {
                        System.out.println("Key " + key + " is not in map");
                    }
                    return null;
                }
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            for (int i = 0; i < 4; i++) {
                executorService.submit(new Producer());
            }
        } finally {
            executorService.shutdown();
        }
    }
}
