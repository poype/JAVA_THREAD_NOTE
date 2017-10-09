package concurrency.in.practice.b;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;


/*
 模拟多个任务并行访问各自的上游系统，所有的任务都要求在给定时间内完成
 超时的任务放弃上游结果，使用本地兜底数据
 */
public class ManyTimeLimit {

    // 请求一个服务最长等待的时间，如果超过这个时间，则放弃任务请求，使用默认数据
    private static final int TIME_BUDGET = 2000000000;  //单位纳秒，就是2s

    public static void main(String[] args) {
        try {
            String result = request();
            System.out.println("所有上游系统返回的数据总集合：" + result);
        } catch (InterruptedException e) {

        }
    }

    private static String request() throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);

        List<Task2> tasks = new ArrayList<Task2>();   //创建6个任务，模拟同时请求6个上游系统
        tasks.add(new Task2(1,"one"));
        tasks.add(new Task2(1,"two"));
        tasks.add(new Task2(3,"three")); //three和four这两个任务会超时
        tasks.add(new Task2(4,"four"));
        tasks.add(new Task2(1,"five"));
        tasks.add(new Task2(1,"six"));

        //futures中元素的迭代顺序是按照tasks中对应任务的顺序排序的，这样可以方便的将任务和其对应的结果Future关联在一起。
        List<Future<String>> futures =  exec.invokeAll(tasks, TIME_BUDGET, TimeUnit.NANOSECONDS); //invokeAll方法会抛出InterruptedException

        StringBuilder resultSb = new StringBuilder(""); //用6个上游系统的结果拼装成这个总结果
        Iterator<Task2> iterator = tasks.iterator();
        for(Future<String> future : futures) {
            Task2 task = iterator.next();
            String taskName = task.getName();
            String value;
            try {
                value = future.get(); //获取上游返回的结果
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                System.out.println(taskName+"任务中发生异常："+cause.getMessage());
                value = "任务中发生异常,默认兜底数据";
            } catch (CancellationException e) {
                System.out.println(taskName+"任务超时");
                value = "任务超时,默认兜底数据";
            }
            System.out.println(taskName + "-->" + value);
            resultSb.append(taskName).append("-->").append(value).append("###"); //拼装到总数据中
        }
        return resultSb.toString();
    }
}

class Task2 implements Callable<String> {

    private long workTime; //模拟请求上游系统花费的时间
    private String name; //任务的名字

    public Task2(long workTime, String name) {
        this.workTime = workTime;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.MILLISECONDS.sleep(workTime * 1000);
        return name+"上游系统返回的数据内容";
    }
}

/*
 上个例子只是要求单个任务在给定时间内完成，这次是要求多个任务在给定时间内完成。
 也可以仿照上个例子那样使用限时的get()方法，但是这里更适合使用invokeAll。

 invokeAll方法接受一个任务的集合，并返回一组Future的集合。
 invokeAll按照任务集合的迭代器顺序将对应的Future添加到返回的集合中，从可以方便的将任何和Future关联起来。
 下面三种情况invokeAll方法都会返回：
 1.所有的任务都执行完毕
 2.调用线程被interrupt
 3.超过参数中指定的时间
 当超过指定的时间时，任何还没有完成的任务都会自动取消。客户端代码可以通过get()或isCancelled()来判断究竟发生了什么。
 */
