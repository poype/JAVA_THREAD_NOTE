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
