package thinking.in.java.a;

/*
 * 这个类只是定义了一个任务，还没有涉及到线程
 */
public class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {}

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "("+(countDown > 0 ? countDown : "LiftOff")+"),";
    }

    @Override
    public void run() {
        while(countDown-- > 0) {
            System.out.print(status());
            Thread.yield(); //告诉线程调度器，此时可以将处理器资源分配给其它线程
        }
        System.out.println();
    }
}

/*
 线程与任务：
 Thread类自身不执行任何操作，它只是驱动赋予它的任务。
 Runnable接口代表一个任务，这个接口的名字选的很糟糕，如果叫做Task应该更合理。
 一个线程可能对应多个任务。
 */