package concurrency.in.practice.a.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*
 先导任务
 */
public class PreTask implements Runnable {

    private CountDownLatch latch;
    private int workSecond; //模拟这个任务的工作时间

    public PreTask(CountDownLatch latch, int workSecond) {
        this.latch = latch;
        this.workSecond = workSecond;
    }

    @Override
    public void run() {
        // 下面的sleep模拟这个任务工作的时间
        try {
            System.out.println("PreTask开始执行");
            TimeUnit.MILLISECONDS.sleep(workSecond * 1000);
            System.out.println("PreTask执行结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown(); //递减这个计数器，表示有一个事件已经发生了
        }
    }
}
