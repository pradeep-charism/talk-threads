package me.edu.intermediate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 3:55 PM
 */
public class ThreadPoolsExample {
    public static void main(String[] args) {

        List<Callable<String>> tasks = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            int finalI = i;
            Callable<String> task = () -> {
                String taskOutput = finalI + "" + Thread.currentThread().getName() + "-" + System.currentTimeMillis();
                System.out.println(taskOutput);
                return taskOutput;
            };
            tasks.add(task);
        }

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

        try {
            singleThreadExecutor.invokeAll(tasks);
            fixedThreadPool.invokeAll(tasks);
            cachedThreadPool.invokeAll(tasks);
            scheduledThreadPool.schedule(tasks.get(0), 50, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            singleThreadExecutor.shutdown();
            fixedThreadPool.shutdown();
            cachedThreadPool.shutdown();
            scheduledThreadPool.shutdown();
        }

    }
}
