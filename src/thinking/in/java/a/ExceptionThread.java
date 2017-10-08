package thinking.in.java.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionThread implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        } catch (RuntimeException e) {
            //这里不会被调用
            System.out.println("handle exception");
        }

    }
}

/*
 本例中的代码是throw一个RuntimeException，如果是Exception类型的异常则必须用try-catch处理，不能使用throws在方法上声明。
 不能捕获从线程中逃逸的异常。一旦异常逃出任务的run方法，它就会向外传播的控制台。
 如本例所示，虽然在main方法中加了try-catch语句，但也无法捕获到从线程中throw的异常，所以catch子句无法执行
 */