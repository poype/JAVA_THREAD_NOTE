package thinking.in.java.deadLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] chops = new Chopstick[5];
        for(int i = 0 ; i < 5 ; i++) {
            chops[i] = new Chopstick(i);
        }
        for(int i = 0 ; i < 5 ; i++) {
            exec.execute(new Philosopher(chops[i],chops[(i+1)%5],i));
        }
        exec.shutdown();
    }
}
/*
 如果要发生死锁，必须同时满足下面4个条件：
 1. 互斥条件。  ps：这条只要是线程问题，就必须满足
 2.  一个任务在持有资源的时候，又再等待另外的资源
 3. 资源不能被其它任务抢占
 4. 循环等待
 上面这些条件中，最容易破坏的就是第4条
 */