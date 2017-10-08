package thinking.in.java.a;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 这个程序的重点是：
 * Executors.newCachedThreadPool方法可以接受一个ThreadFactory类型的对象作为参数
 * 用于详细指定线程池该如何创建新线程
 */
public class DaemonFromFactory implements Runnable {

    @Override
    public void run() {
        try {
            while(true) {
                TimeUnit.MICROSECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 构造线程池的方法都可以接受一个ThreadFactory类型的参数，用于对池中的线程设置属性(后台、优先级、名称)
        ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for(int i = 0 ; i < 10 ; i++) {
            exec.execute(new DaemonFromFactory());
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(1000);
    }
}