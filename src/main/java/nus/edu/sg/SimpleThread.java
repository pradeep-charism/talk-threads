package nus.edu.sg;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 3:19 PM
 */
public class SimpleThread extends Thread{

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Invoked start");
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Invoked run");
        throw new RuntimeException("throwing mandatory runtime exception");
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("Invoked destroy");
    }

    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        super.setUncaughtExceptionHandler(eh);
    }
}
