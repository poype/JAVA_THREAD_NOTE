package thinking.in.java.deadLock;

//哲学家
public class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private int id;

    public Philosopher(Chopstick left,Chopstick right,int id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    @Override
    public void run() {
        while(true) {
            left.take();
            System.out.println(id+" take left chopstick");
            right.take();
            System.out.println(id+" take right chopstick");
            System.out.println("哲学家"+id+"开始吃饭");
            left.drop();
            right.drop();
            System.out.println(id+"开始冥想");
        }
    }
}
