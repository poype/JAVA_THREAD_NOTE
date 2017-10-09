package concurrency.in.practice.a.callable;


import concurrency.in.practice.exception.TestException;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<String> future = exec.submit(new Task());
        try {
            String value = future.get();
            System.out.println(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if(cause instanceof TestException) {
                System.out.println("从线程任务中获取到抛出的TestException异常");
            }
        }
    }
}

class Task implements Callable<String> {

    @Override
    public String call() throws TestException {
        System.out.println("task开始运行");
        throw new TestException();
    }
}

/*
 Callable不仅可以从任务中返回结果，而且还可以抛出异常
 无论任务代码抛出什么异常，都会被封装到一个ExecutionException中，并在Future.get中被重新抛出
 */

