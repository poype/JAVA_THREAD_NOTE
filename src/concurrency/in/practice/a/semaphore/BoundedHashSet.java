package concurrency.in.practice.a.semaphore;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/*
 一个有界阻塞的容器实现
 semaphore被初始化为容器的最大容量值
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        //注意这里的set还是要使用同步容器，因为semaphore同一时刻可能会放进来多个线程任务
        this.set = Collections.synchronizedSet(new HashSet<T>());
        semaphore = new Semaphore(bound); //bound是容器的最大容量
    }

    public boolean add(T o) throws InterruptedException {
        semaphore.acquire(); //Semaphore计数器减1，如果计数器的值已经为0，则会阻塞
        boolean wasAdded = false;

        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if(!wasAdded) {
                semaphore.release(); //如果添加元素没成功，恢复Semaphore值
            }
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if(wasRemoved) {
            semaphore.release(); //移除元素，Semaphore计数器加1
        }
        return wasRemoved;
    }
}

/*
 Semaphore用来控制同时访问某个特定资源的操作数量，或同时执行某个指定操作的数量。
 Semaphore中管理着一组许可，许可的初始数量可通过构造函数指定。
 任何一个操作都可以申请获得一个许可，使用之后再将许可还回去(这是Semaphore与CountDownLatch的区别)。
 如果许可都没了，那么acquire将阻塞直到有许可。
 release方法将还给Semaphore一个许可
 初始值为1的Semaphore可以用作mutex，并且具备了不可重入的加锁语义。
 */