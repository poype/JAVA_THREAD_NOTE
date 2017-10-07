package thinking.in.java.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < 5 ; i++)
            exec.execute(new LiftOff());
        exec.shutdown();
    }
}
/*
 *  CachedThreadPool将为每个任务创建一个线程
 *  shutdown方法的调用防止新的任务再被提交给ExecutorService。但是shutdown调用之前提交的任务仍会继续运行
 *
 *  在任何线程池中，只要存在现成可用的线程，都会被自动复用
 *  CachedThreadPool在程序执行过程中，会创建与所需数量相同的线程。在回收旧线程的时候会停止创建新的线程
 */