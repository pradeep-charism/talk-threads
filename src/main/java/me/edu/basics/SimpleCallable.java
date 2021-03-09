package me.edu.basics;

import java.util.concurrent.Callable;

/**
 * @Author Pradeep Kumar
 * @create 3/8/2021 3:37 PM
 */
public class SimpleCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("invoked callable");
        Thread.sleep(2000);
        return "This is Callable return value";
    }
}
