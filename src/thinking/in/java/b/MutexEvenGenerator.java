package thinking.in.java.b;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 显示锁，需要显示化的加锁和解锁。与使用synchronized一个效果。
 * 显示的Lock在加锁和释锁方面，相对于内建的synchronized锁来说，还赋予了你更细力度的控制力
 */
public class MutexEvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public int next() {
        lock.lock();  			//显示加锁
        try {
            currentEvenValue++;
            Thread.yield();
            currentEvenValue++;
            System.out.println(currentEvenValue);
            return currentEvenValue;
        } finally {
            lock.unlock();		//显示解锁，在java8上做测试，感觉不用unlock，自动就会解锁
        }
    }
}