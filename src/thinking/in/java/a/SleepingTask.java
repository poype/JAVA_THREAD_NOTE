package thinking.in.java.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff {

    @Override
    public void run() {
        try {
            while(countDown-- > 0) {
                System.out.println(status());
                //Thread.sleep(100)这种调用方式已经过时了，要用下面这种方式让线程睡眠
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < 5 ; i++) {
            exec.execute(new SleepingTask());
        }
        exec.shutdown();
    }
}

/*
 sleep()的调用可以抛出InterruptedException异常
 注意任务中的异常必须在任务中try-catch，因为异常不能跨线程传播。
 */
