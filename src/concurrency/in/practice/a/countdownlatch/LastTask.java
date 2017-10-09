package concurrency.in.practice.a.countdownlatch;


import java.util.concurrent.CountDownLatch;

/*
 等所有PreTask任务执行完后才能执行的task
 */
public class LastTask implements Runnable {

    private CountDownLatch latch;

    public LastTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await(); //await方法等待计数器达到0，这表示所有需要等待的事件都已经发生。
            System.out.println("lastTask 开始执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
