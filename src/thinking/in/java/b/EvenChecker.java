package thinking.in.java.b;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 这个例子要表明的就是多个线程共享的资源要进行同步保护，否则就会出现意外情况
 */
public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator generator,int id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public void run() {
        while(!generator.isCanceled()) {
            int val = generator.next();
            if(val % 2 != 0) { //如果产生的不是偶数，就cancel，这样程序就会终结。
                System.out.println(val + "not even int thread "+id);
                generator.cancel();
            }
        }
    }

	public static void main(String[] args) {
		IntGenerator generator = new EvenGenerator(); // generator被多个线程共享
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0 ; i < 10 ; i++) {
			exec.execute(new EvenChecker(generator,i));
		}
		exec.shutdown();
	}
}
