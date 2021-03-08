package nus.edu.sg.basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 3:38 PM
 */
public class SimpleCallableExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        SimpleCallable task = new SimpleCallable();

        Future<String> future = executorService.submit(task);
        String output = future.get();
        System.out.println("Value from task: " + output);
        String output2 = future.get(100, TimeUnit.MILLISECONDS);
        System.out.println("Value from task: " + output2);

        executorService.shutdown();
    }
}
