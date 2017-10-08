package thinking.in.java.d;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test1 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Box box = new Box();
        exec.execute(new Producer(box));
        exec.execute(new Producer(box));
        exec.execute(new Producer(box));
        exec.execute(new Producer(box));  //启动多个生产者和多个消费者，但是只有一个box
        exec.execute(new Comsumer(box));
        exec.execute(new Comsumer(box));
        exec.execute(new Comsumer(box));
        exec.execute(new Comsumer(box));
        exec.execute(new Comsumer(box));
        exec.execute(new Comsumer(box));
        exec.shutdown();
    }
}

class Producer implements Runnable {
    private Box box;

    public Producer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        while(true) {
            synchronized(box) {
                try {
                    while(box.isHave()) { //这里要注意使用while，不能使用if
                        box.wait(); //要调用box的wait，必须先获取box上的锁
                    }
                    box.produce();
                    box.notifyAll();
                } catch(InterruptedException e) {

                }
            }
        }
    }

}

class Comsumer implements Runnable {
    private Box box;

    public Comsumer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        while(true) {
            synchronized(box) {
                try {
                    while(!box.isHave()) {
                        box.wait();
                    }
                    box.comsume();
                    box.notifyAll();
                } catch(InterruptedException e) {

                }
            }
        }
    }
}

class Box {
    private boolean have = false;

    public boolean isHave() {
        return have;
    }

    public void produce() {
        try {
            TimeUnit.MILLISECONDS.sleep(600); //生产一个box消耗的时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        have = true;
        System.out.println("生产了商品");
    }


    public void comsume() {
        try {
            TimeUnit.MILLISECONDS.sleep(600); //消费一个box消耗的时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        have = false;
        System.out.println("消费商品");
    }
}



/*
 wait方法会将本任务挂起，只有在notify和notifyAll发生时，这个任务才会被唤醒并去检查所发生的变化。

 当一个任务在方法里遇到了对wait的调用时，执行任务的线程被挂起，对象上的synchronized锁被释放。
 因为wait释放锁，其它任务才有机会获得锁，该对象上的其他synchronized方法才可以在wait期间被调用。
 如果在wait期间不释放对象的锁，那么被阻塞的线程还占用着对象的synchronized锁，导致该线程在被wait挂起时其它线程
 仍无法执行synchronized方法，进而没有任何一个线程会执行notify方法，这就会导致死锁。

 只能在synchronized方法或synchronized块中调用wait、notify、notifyAll方法。
 如果在非同步代码中调用这些方法，程序能通过编译，但是在运行时会抛出IllegalMonitorStateException异常。
 在wait的时候要释放对象的锁，如果不事先用synchronized占有锁，那么在调用wait的时候释放什么呢？

 当收到notify事件，从wait中唤醒时，该任务必须首先重新获得synchronized锁，才能继续原来的代码执行。

 注意要用while循环包围wait()，不能使用if。因为即使线程被唤醒了，也应该检查一下条件是否满足执行的条件，如果不满足，应该继续wait。

 notify和notifyAll的区别：
 使用notify时，在锁上wait的众多任务中只有一个会被唤醒。因此如果使用notify，就必须保证被唤醒的是恰当的任务。
 notifyAll会唤醒所有在锁上wait的任务。被唤醒的任务需要再检查一下条件是否已经满足自己继续运行，如果不满足则应该重新wait。
 在本例中，不能使用notify，只能使用notifyAll。因为本例中有多个provider和consumer都在同一个锁上wait，如果使用notify，很容易唤醒不恰当的任务。
 例如一个provider在生产完一个box之后唤醒了另一个provider。
 */

















