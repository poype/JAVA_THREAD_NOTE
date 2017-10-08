package thinking.in.java.a;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/*
 * 定义了一个带有返回值的任务
 */
public class TaskWithResult implements Callable<String> {

    private int id;
    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        if(this.id == 5) { //当id为5时，增加2秒处理的时间。为了测试future对象的get方法是否阻塞
            TimeUnit.MILLISECONDS.sleep(2000);
        }
        return "result of TaskWithResult" + this.id;
    }
}
