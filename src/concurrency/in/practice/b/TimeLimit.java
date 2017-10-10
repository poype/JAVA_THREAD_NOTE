package concurrency.in.practice.b;


import java.util.concurrent.*;

public class TimeLimit {

    // 请求一个服务最长等待的时间，如果超过这个时间，则放弃任务请求，使用默认数据
    private static final int TIME_BUDGET = 2000000000;  //单位纳秒，就是2s

    public static void main(String[] args) {
        try {
            String value = request(); //发起请求
            System.out.println("在main中拿到响应：" + value);
        } catch (InterruptedException e) {

        }
    }

    private static String request() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        ExecutorService exec = Executors.newFixedThreadPool(3);
        Future<String> future = exec.submit(new Task());
        exec.shutdown(); //如果没有这句代码，线程池中的线程会一直存在，影响jvm退出
        String value;
        try {
            long timeLeft = endNanos - System.nanoTime();
            value = future.get(timeLeft,TimeUnit.NANOSECONDS); // 指定最大等待时间
            System.out.println(value);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            System.out.println("发生异常："+cause.getMessage());
            value = "default content";
        } catch (TimeoutException e) { //等待超时还没有拿到结果，value使用默认值
            System.out.println("请求超时，使用兜底默认数据");
            value = "default content";
            boolean cancelResult = future.cancel(true); //记得取消那个任务，回收资源
            if(cancelResult) {
                System.out.println("关闭请求任务成功");
            }
        }
        return value;
    }

}

class Task implements Callable<String> {

    @Override
    public String call() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100000); //这个任务明显超时了
        return "response content";
    }
}

/*
 如果某个任务无法在指定时间内完成，那么将不在需要它的结果，此时可以放弃这个任务。
 例如多线程请求上游系统，如果某个任务等待的时间过长，就可以考虑丢弃那个请求，使用默认数据响应客户端。
 这样可以给用户更好的体验。

 Callable有时比Runnable更有用
 */

/*
 还记得昨天发现的，程序执行完之后并没有退出，要等很久才能退出。
 这时因为你没有调用exec.shutdown()，会导致线程池中的线程一直存在。
 所以你自己的代码执行完了，但是还有非daemon的线程存在，所以jvm一直不能退出
 */



