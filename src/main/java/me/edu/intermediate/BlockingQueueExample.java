package me.edu.intermediate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author Pradeep Kumar
 * @create 3/9/2021 5:56 PM
 */
public class BlockingQueueExample {

    private static final int MAX_COUNT = 10;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);


        class NumberProducer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                for (int i = 0; i < MAX_COUNT; i++) {
                    queue.put(i);
                    Thread.sleep(1000);
                }
                return null;
            }
        }

        class NumberConsumer implements Callable<Integer> {

            @Override
            public Integer call() throws Exception {
                while (true) {
                    Integer take = queue.take();
                    System.out.println(take);
                }
            }
        }

        List<Callable<Integer>> callables = new ArrayList<>();
        callables.add(new NumberProducer());
        callables.add(new NumberConsumer());

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
