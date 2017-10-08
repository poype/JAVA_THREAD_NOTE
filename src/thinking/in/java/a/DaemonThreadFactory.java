package thinking.in.java.a;

import java.util.concurrent.ThreadFactory;

/*
 * 线程工厂
 */
public class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
