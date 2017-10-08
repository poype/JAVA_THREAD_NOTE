package thinking.in.java.a;


/*
 * 当进程中不存在任何非daemon线程时，进程会终结
 * 也就是说，daemon线程即使存在，也不影响进程的生命周期
 */

import java.util.concurrent.TimeUnit;

public class SimpleDaemons implements Runnable {

    @Override
    public void run() {
        try {
            // 在任务中使用无限循环，确保线程自己不会终结。
            // 但由于会将该线程设置为daemon线程，所以只要程序中所有非daemon线程死亡，那么程序也会退出。
            while(true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch(InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0 ; i < 10 ; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            daemon.setDaemon(true);  //设置为daemon线程
            daemon.start();
        }
        System.out.println("all daemons started");
        TimeUnit.MILLISECONDS.sleep(1000);
    }
}
