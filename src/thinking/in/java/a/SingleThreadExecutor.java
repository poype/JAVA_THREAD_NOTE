package thinking.in.java.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for(int i = 0 ; i < 5 ; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
/*
 * SingleThreadExecutor相当于数量等于1的FixedThreadPool，提交到它的所有的任务都会顺序执行
 */
