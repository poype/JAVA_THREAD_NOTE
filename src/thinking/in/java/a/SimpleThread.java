package thinking.in.java.a;

/*
 * 编码的变体，直接继承Thread类代替实现Runnable接口，因为Thread类自己就实现了Runnable接口
 */
public class SimpleThread extends Thread {
    private int countDown = 5;
    private static int threadCount = 0;

    public SimpleThread() {
        super(Integer.toString(++threadCount)); //给线程一个名字，可以用getName()方法取出这个名字
        start(); //启动线程
    }

    public String toString() {
        return "#" + getName() + " ("+countDown+")";
    }

    public void run() {
        while(true) {
            System.out.println(this);
            if(--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0 ; i < 5 ; i++) {
            new SimpleThread();
        }
    }
}

/*
 这个例子是在构造器中启动线程。记住不要在构造器中启动一个线程。
 在构造器中启动线程可能会出现问题，因为另一个任务可能会在构造器结束之前开始执行。这意味着该任务可能访问处于不稳定状态的对象。
 */