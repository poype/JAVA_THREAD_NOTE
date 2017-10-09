package concurrency.in.practice.a.semaphore;


import java.util.concurrent.Semaphore;

public class Test {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        System.out.println("aaa");
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        System.out.println("bbb");
    }
}
/*
 使用semaphore要注意，它可以通过release方法无限增长许可的数量，甚至超过了初始时指定的许可数量
 */
