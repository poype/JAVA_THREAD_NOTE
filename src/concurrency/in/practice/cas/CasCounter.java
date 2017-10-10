package concurrency.in.practice.cas;

/*
 利用CAS思想实现非阻塞的计算器
 */
public class CasCounter {

    private SimulatedCAS casWrapValue;

    public int getValue() {
        return casWrapValue.get();
    }

    //自增操作
    public int increment() {
        int a,b;
        boolean casSuccess;
        do {
            a = casWrapValue.get(); //获取变量当前的值a
            b = a + 1;              //根据a计算新值得到b
            casSuccess = (a == casWrapValue.compareAndSwap(a,b));//cas原子更新变量值
        } while (!casSuccess);//如果cas更新变量值失败，则重新尝试
        return b;
    }
}

/*
 CAS的典型用法：先读取变量的值A，根据A计算新值B
              在上面计算之后的时刻，变量的值可能会发生变化，变量此刻的值记作V
              通过CAS以原子的形式将变量的值更新为B(如果变量的值没发生变化，即A == V)
 */
