package thinking.in.java.c;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 ThreadLocal对象通常当作静态域存储
 可以通过ThreadLocal的get()和set()访问该对象中的内容
 get()方法将返回与其线程相关联的对象的副本
 set()方法会将值存储到该线程自己的存储对象中，并返回存储对象中原有的值。
 ThreadLocal对象是为每一个线程都分配了独有的存储，所以不会出现竞争条件。
 */
public class Accessor implements Runnable {

    private final int id;

    public Accessor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "线程id：" + id + ": " + ThreadLocalVariableHolder.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accessor(i));
        }
        TimeUnit.MICROSECONDS.sleep(100);
        exec.shutdownNow(); //终止线程池中的所有线程
    }
}

/*
 ThreadLocal的一个包装类
 */
class ThreadLocalVariableHolder {

    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {

        @Override
        protected Integer initialValue() { //指定ThreadLocal中的初始化值
            return 0;
        }
    };

    public static void increment() {
        value.set(value.get() + 1);
    }

    public static int get() {
        return value.get();
    }
}


