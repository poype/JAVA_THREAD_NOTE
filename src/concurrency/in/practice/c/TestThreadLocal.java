package concurrency.in.practice.c;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadLocal {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new Task1());
        exec.execute(new Task2());
        exec.shutdown();
    }
}

class Task1 implements Runnable {
    @Override
    public void run() {
        System.out.println("执行Task1的任务");
        ThreadLocalVariableHolder.set("thing in java");
    }
}

class Task2 implements Runnable {
    @Override
    public void run() {
        System.out.println("执行Task1的任务");
        String value = ThreadLocalVariableHolder.get();
        //这里拿到的value值是在Task1的任务赋值的
        System.out.println("在Task2中取到的value值：" + value);
    }
}

class ThreadLocalVariableHolder {
    private static ThreadLocal<String> value = new ThreadLocal<String>();

    public static void set(String str) {
        value.set(str);
    }

    public static String get() {
        return value.get();
    }
}

/*
 ThreadLocal是为每个线程存储一份变量的副本
 但是一个线程可以驱动多个任务，在这个示例中，用一个线程驱动两个任务Task1、Task2
 在Task1中向ThreadLocal存储一个变量，Task2中获得了这个变量，因为这两个任务是由同一个线程驱动的
 */
