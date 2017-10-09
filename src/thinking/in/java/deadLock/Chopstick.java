package thinking.in.java.deadLock;

//筷子
public class Chopstick {

    private int id;
    public Chopstick(int id) {
        this.id = id;
    }

    private boolean state = false; //起初时筷子没有人拿起
    //拿起筷子
    public synchronized void take() {
        try {
            while(state) {//如果筷子已经被别人拿起，那就等待
                wait();
            }
            state = true;//拿起筷子
            System.out.println(id+"筷子被拿起");
        } catch(InterruptedException e) {
            System.out.println(e);
        }

    }

    //放下筷子
    public synchronized void drop() {
        state = false;
        notifyAll();
    }
}
