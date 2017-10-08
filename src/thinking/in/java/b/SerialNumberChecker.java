package thinking.in.java.b;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 验证自增操作不是原子的
 * 这个测试等待时间比较久，等了1分钟左右才出现效果
 */
public class SerialNumberChecker {
    public static void main(String[] args) {
        CircularSet set = new CircularSet();
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < 100 ; i++) {
            exec.execute(new SerialChecker(set));
        }
        exec.shutdown();
    }
}

class SerialChecker implements Runnable {
    private CircularSet set;

    public SerialChecker(CircularSet set) {
        this.set = set;
    }

    @Override
    public void run() {
        while(true) {
            int serialNum = SerialNumberGenerator.nextSerialNumber();
            if(set.contains(serialNum)) {
                System.out.println("重复数字 "+ serialNum);
                System.exit(0);				//发现重复的数字，退出整个进程
            }
            System.out.println(serialNum);
            set.add(serialNum);

        }
    }
}

class SerialNumberGenerator {      //产生连续数字，但是自增运算不是原子的
    private static volatile int serialNumber = 0;
    public static int nextSerialNumber() {
        return serialNumber++;
    }
}

class CircularSet {
    private final int size = 3000;
    private int array[] = new int[size];
    private int index = 0;

    public CircularSet() {
        for(int i = 0 ; i < size ; i++) { //初始化，否则默认为0，会影响判断结果，因为生成的第一个数字就是0
            array[i] = -1;
        }
    }

    public synchronized void add(int i) {  //这个set相当于共享资源，所以所有的方法都要加上synchronized
        array[index] = i;
        index = ++index % size;
    }

    public synchronized boolean contains(int val) {
        for(int i = 0 ; i < size ; i++) {
            if(array[i] == val) {
                return true;
            }
        }
        return false;
    }
}