package thinking.in.java.a;

/*
 * 后台线程创建的线程自动是后台线程
 * 后台线程连执行finally语句的机会都不给，正常线程的finally语句可以保证执行
 * 当最后一个非后台线程终止时，后台线程会“突然”终止
 * 后台线程不是一种很好的思想
 */

import java.util.concurrent.TimeUnit;

public class Daemon implements Runnable {
    private Thread[] t = new Thread[10];
    @Override
    public void run() {
        for(int i = 0 ; i < t.length ; i++) {
            t[i] = new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("DaemonSpawn " + i + " started");
        }
        for(int i = 0 ; i < t.length ; i++) {
            System.out.println(t[i].isDaemon());
        }
        while(true) {
            Thread.yield();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread d = new Thread(new Daemon());
        d.setDaemon(true);   //设定父线程为daemon线程，则这个线程产生的所有线程都是daemon线程
        d.start();
        System.out.println(d.isDaemon());
        TimeUnit.MILLISECONDS.sleep(2000);
    }
}

//后台线程产生的子线程，虽然没有显示设定它为后台线程，但它自动就是后台线程。后台线程的finally子句不能保证执行
class DaemonSpawn implements Runnable {
    @Override
    public void run() {
        while(true) {
            Thread.yield();
        }
    }
}