package thinking.in.java.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimplePriorities implements Runnable {

    private int countDown = 5;
    private volatile double d;
    private int priority;

    public SimplePriorities(int priority) {
        this.priority = priority;
    }

    public String toString() {
        //通过调用Thread.currentThread()来获得对驱动该任务的Thread对象的引用
        return Thread.currentThread() + ": " + countDown;
    }

    @Override
    public void run() {
        //设置线程的优先级
        Thread.currentThread().setPriority(priority);
        while(true) {
            for(int i = 1 ; i < 100000 ; i++) { //占用cpu，没有任何意义
                d+= (Math.PI + Math.E) / (double)i;
                if(i%1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if(--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < 5 ; i++) {
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }
        exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        exec.shutdown();
    }
}
