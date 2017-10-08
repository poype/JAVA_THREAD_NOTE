package thinking.in.java.d;

import java.util.concurrent.*;

public class Test3 {
    public static void main(String[] args) {
        BlockingQueue<Bread> queue = new ArrayBlockingQueue<Bread>(10); //生产者和消费者共享一个同步队列
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Producer3(queue));
        exec.execute(new Producer3(queue));
        exec.execute(new Producer3(queue));
        exec.execute(new Producer3(queue));
        exec.execute(new Producer3(queue));
        exec.execute(new Producer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.execute(new Consumer3(queue));
        exec.shutdown();
    }
}

//Bread作为被生产出的产品
class Bread {
    private int id;

    public Bread(int id) {
        this.id  = id;
    }

    @Override
    public String toString() {
        return "Bread [id=" + id + "]";
    }
}

class Producer3 implements Runnable {

    private BlockingQueue<Bread> queue;
    private static int id = 0;

    public Producer3(BlockingQueue<Bread> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(700);
                synchronized(Producer3.class) { //这里要加互斥块是因为id是共享资源，如果不加互斥，当有多个生产者时就会有问题
                    id++;
                    Bread bread = new Bread(id);
                    queue.put(bread);  // 加入队列
                }
            } catch(InterruptedException e) {

            }
        }
    }
}


class Consumer3 implements Runnable {

    private BlockingQueue<Bread> queue;

    public Consumer3(BlockingQueue<Bread> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            Bread bread;
            try {
                bread = queue.take(); //从队列中取出
                System.out.println(bread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 BlockingQueue不接受null元素。null被用作哨兵使用。

 BlockingQueue的几个方法比较：
    抛出异常		特殊值		阻塞			超时
插入	add(e)		offer(e)	put(e)		offer(e, time, unit)
移除	remove()	poll()		take()		poll(time, unit)
检查	element()	peek()		不可用		不可用

 BlockingQueue的实现类：
 LinkedBlockingQueue：无界队列
 ArrayBlockingQueue：具有固定的尺寸

 BlockingQueue已经帮助我们做了线程间的互斥、同步等机制，所以不需要我们再显示增加类似synchronize的这种代码
*/
