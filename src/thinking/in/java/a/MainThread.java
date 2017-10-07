package thinking.in.java.a;

public class MainThread {
    public static void main(String[] args) {
        for(int i = 0 ; i < 5 ; i++) { //连续创建5个线程，每个线程的任务都是LiftOff
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for lift off");
    }
}
