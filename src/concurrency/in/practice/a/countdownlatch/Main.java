package concurrency.in.practice.a.countdownlatch;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        //因为下面有3个PreTask任务要先执行完，所以CountDownLatch计数器被初始化为3.
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService exec = Executors.newCachedThreadPool();
        //多个线程等待一组事件发生
        for(int j = 0 ; j < 5 ; j++) {
            exec.execute(new LastTask(latch));
        }

        //启动三个PreTask任务，这三个任务的工作时间不同。
        for(int i = 1 ; i <= 3 ; i++) {
            exec.execute(new PreTask(latch,i));
        }
        exec.shutdown();
    }
}

/*
 CountDownLatch用于使一个或多个线程等待一组事件发生。
 CountDownLatch包含一个计数器，该计数器被初始化为一个正整数，表示需要等待的事件数量。
 countDown()方法递减这个计数器，表示有一个事件已经发生了。
 await方法等待计数器达到0，这表示所有需要等待的事件都已经发生。
 await方法会一直阻塞直到计数器为0，或者线程被interrupt，或者等待超时
 */