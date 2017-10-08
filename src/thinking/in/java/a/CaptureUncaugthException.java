package thinking.in.java.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/*
 * 捕获线程中的异常
 * Thread.setDefaultUncaughtExceptionHandler(); 可以用这个方法为所有线程设置默认的异常处理器
 * 默认的异常处理器只有在不存在线程专有的异常处理器时才会被调用。
 */
public class CaptureUncaugthException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor(new HandlerThreadFactory()); //指定了一个ThreadFactory为池中的线程设置属性
        exec.execute(new ExceptionThread2());
    }
}

//任务
class ExceptionThread2 implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }
}

//线程异常处理器
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t);
    }
}

//线程工厂
class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        System.out.println("HandlerThreadFactory "+t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler()); //给每个创建的线程设置异常处理器
        return t;
    }
}