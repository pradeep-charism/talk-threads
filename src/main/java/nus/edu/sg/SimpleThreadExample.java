package nus.edu.sg;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 3:22 PM
 */
public class SimpleThreadExample {

    public static void main(String[] args) {
        SimpleThread thread = new SimpleThread();
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("uncaught exception handler invoked. Error message: " + e.getMessage());
            }
        });

        try {
            thread.start();
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }

        System.out.println("After thread is executed");
    }
}
