package thinking.in.java.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for(int i = 0 ; i < 5 ; i++)
            exec.execute(new LiftOff());
        exec.shutdown();
    }
}
/*
 * FixedThreadPool创建固定数量的线程，此处为2
 */
