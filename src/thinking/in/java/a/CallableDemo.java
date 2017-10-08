package thinking.in.java.a;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<Future<String>>();
        for(int i = 0 ; i < 10 ; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }
        for(Future<String> fs:results) {
            System.out.println(fs.get());
        }
    }

}
/*
 * 思想：
 * exec.submit方法返回一个future对象，代表将来会产生一个结果。
 * 调用future对象的get方法会阻塞，直到future对象返回值
 */