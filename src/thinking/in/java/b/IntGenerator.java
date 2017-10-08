package thinking.in.java.b;

/*
 * 相当于共享资源，EvenGenerator会继承这个抽象类
 */
public abstract class IntGenerator {
    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
