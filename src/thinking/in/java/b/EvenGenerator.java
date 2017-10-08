package thinking.in.java.b;

/*
 * 作为共享资源，本意是每次都会生成一个偶数。
 * 可由于next方法没有同步保护，会在两个自增操作之间发生线程切换，这样就会产生基数
 */
public class EvenGenerator extends IntGenerator {

    private int currentEvenValue = 0;
    @Override
    public int next() {
        currentEvenValue++;  //其实递增操作本身也不是原子的
        Thread.yield();
        currentEvenValue++;
        return currentEvenValue;
    }

}