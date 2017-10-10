package concurrency.in.practice.cas;


/*
 这里的代码是对CAS操作的一个模拟，可以用其理解CAS操作，但要注意CAS操作是CPU直接支持的
 */
public class SimulatedCAS {
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
}

/*
CAS操作包含了3个参数：变量当前的值V、进行比较的值A、拟写入的新值B
                   当且仅当V等于A时，CAS才会通过原子的方式将变量设置成B，否则不会执行任何操作。
                   无论变量的值是否被更新，都会返回变量的旧值V
 */
