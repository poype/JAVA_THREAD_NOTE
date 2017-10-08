package thinking.in.java.b;

/*
 * 作为共享资源，本意是每次都会生成一个偶数。
 * 可由于next方法没有同步保护，会在两个自增操作之间发生线程切换，这样就会产生基数
 */
public class EvenGenerator extends IntGenerator {

    private int currentEvenValue = 0;
    @Override
    public synchronized int next() {
        currentEvenValue++;  //其实递增操作本身也不是原子的
        Thread.yield();
        currentEvenValue++;
        return currentEvenValue;
    }
}

/*
 通过添加synchronized关键字，避免多个任务同时访问同一个资源。
 每个对象都自动含有单一的锁，也称为监视器锁。
 当调用一个对象中任意的synchronized方法时，此对象都会被加锁。
 这时如果再调用这个对象上的其它方法，就必须等前一个方法调用完毕并释放了锁之后才能被调用。
 一个对象上的所有synchronized方法共享同一个锁。

 锁重入：一个任务可以多次获得对象的锁。只有首先获得了锁的任务才能允许继续获取多个锁。

 针对每个类，也有一个锁(作为类的Class对象的一部分)，所以synchronized static方法可以在类的范围内防止对static数据的并发访问。
 */