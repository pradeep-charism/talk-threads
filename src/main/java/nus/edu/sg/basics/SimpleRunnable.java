package nus.edu.sg.basics;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 3:30 PM
 */
public class SimpleRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("invoked runnable run()");
        throw new RuntimeException("mandatory runtime exception  - runnable");
    }
}
