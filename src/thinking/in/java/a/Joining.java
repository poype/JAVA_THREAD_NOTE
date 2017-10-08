package thinking.in.java.a;

import java.util.concurrent.TimeUnit;

public class Joining {
    public static void main(String[] args) {
        Sleeper s = new Sleeper();
        Joiner j = new Joiner(s);
        s.start();
        j.start();
    }
}

class Joiner extends Thread {
    Sleeper s;
    Joiner(Sleeper s) {
        this.s = s;
    }

    public void run() {
        try {
            s.join(); //本线程会被挂起，直到s线程运行结束才能恢复执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I am Joiner");
    }
}

class Sleeper extends Thread {
    public void run() {
        try {
            for (int i = 0 ; i < 5 ; i++) {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("Sleeper "+i);
            }
        } catch(InterruptedException e) {
            System.out.println(e);
        }
    }
}
