package thinking.in.java.d;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test2 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Box2 box = new Box2();
        exec.execute(new Producer2(box,lock,condition));
        exec.execute(new Producer2(box,lock,condition));
        exec.execute(new Producer2(box,lock,condition));
        exec.execute(new Producer2(box,lock,condition));
        exec.execute(new Comsumer2(box,lock,condition));
        exec.execute(new Comsumer2(box,lock,condition));
        exec.execute(new Comsumer2(box,lock,condition));
        exec.execute(new Comsumer2(box,lock,condition));
        exec.execute(new Comsumer2(box,lock,condition));
        exec.execute(new Comsumer2(box,lock,condition));
        exec.shutdown();
    }
}

class Producer2 implements Runnable {
    private Box2 box;
    private Lock lock;
    private Condition condition;

    public Producer2(Box2 box,Lock lock,Condition condition) {
        this.box = box;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while(true) {
            try {
                lock.lock();
                while(box.isHave()) {
                    condition.await(); //此处一定要在获取到lock上的锁之后才能调用
                }
                box.produce();
                condition.signalAll();
            } catch(InterruptedException e) {

            } finally {
                lock.unlock();
            }
        }
    }

}

class Comsumer2 implements Runnable {
    private Box2 box;
    private Lock lock;
    private Condition condition;

    public Comsumer2(Box2 box,Lock lock,Condition condition) {
        this.box = box;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while(true) {
            try {
                lock.lock();
                while(!box.isHave()) {
                    condition.await();
                }
                box.comsume();
                condition.signalAll();
            } catch(InterruptedException e) {

            } finally {
                lock.unlock();
            }
        }
    }
}

class Box2 {
    private boolean have = false;

    public boolean isHave() {
        return have;
    }

    public void produce() {
        try {
            TimeUnit.MILLISECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        have = true;
        System.out.println("生产了商品---2");
    }


    public void comsume() {
        try {
            TimeUnit.MILLISECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        have = false;
        System.out.println("消费商品---2");
    }
}

/*
 调用condition的await、signal、signalAll实现线程的同步
 这里有一个坑，lock.newCondition()每次调用都会返回一个新的condition。
 而多个线程要共享同一个condition才能达到同步的效果(这个是当然的，只是开始时我以为lock.newCondition调用每次都返回同一个condition，才出错的)。

 使用condition的await、signal、signalAll与wait、notify、notifyAll一样，
 都要在获取到mutex锁之后才能调用，否则会抛IllegalMonitorStateException异常
 */
