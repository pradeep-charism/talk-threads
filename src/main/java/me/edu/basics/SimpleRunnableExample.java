package me.edu.basics;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 3:30 PM
 */
public class SimpleRunnableExample {
    public static void main(String[] args) {
        SimpleRunnable runnable = new SimpleRunnable();
        Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler((t, e) -> System.out.println("uncaught exception handler invoked. Error message: " + e.getMessage()));

        thread.start();
        System.out.println("runnable is completed");
    }
}
