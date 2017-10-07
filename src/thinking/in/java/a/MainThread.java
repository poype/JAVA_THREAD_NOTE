package thinking.in.java.a;

public class MainThread {
    public static void main(String[] args) {
        for(int i = 0 ; i < 5 ; i++) { //连续创建5个线程，每个线程的任务都是LiftOff
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for lift off");
    }
}
/*
  当main()创建Thread对象时，并没有捕获任何对这些线程对象的引用。
  如果是普通对象，垃圾回收器会对这些对象进行清理。但是对于Thread对象，情况就不同了。
  每个Thread都"注册"了它自己，因此确实有一个队它的引用，
  所以在线程的任务退出其run()并死亡之前，垃圾回收器都无法清除这个线程对象。
 */