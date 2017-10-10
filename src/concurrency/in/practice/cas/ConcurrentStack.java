package concurrency.in.practice.cas;


import java.util.concurrent.atomic.AtomicReference;

/*
 借助AtomicReference实现的一个可多线程并发访问的一个栈
 这里实现的是一个链表栈，由于栈的特点导致此处唯一的多线程共享资源就是栈顶指针top
 push和pop这两个操作都是在更新栈顶指针的时候，才会有多线程冲突的危险
 所以用AtomicReference包装了top指针，采用cas的方式更新top指针
 */
public class ConcurrentStack<E> {

    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

    public void push(E item) {
        Node<E> newHead = new Node<E>(item); //创建要插入的节点
        Node<E> oldHead;
        do{
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead)); //cas设置头指针的值，如果失败，重新尝试
    }

    public E pop() {
        Node<E> newHead;
        Node<E> oldHead;
        do {
            oldHead = top.get();
            if(oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead,newHead)); //cas设置头指针的值，如果失败，重新尝试
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}

/*
 对于push操作，如果加锁的话，那么newHead.next = oldHead和将top指针指向新node的操作都需要在锁中。
 用cas之后，newHead.next = oldHead操作多个线程是可以并发执行的，只是在top指针修改的地方需要用CAS特殊处理
 而且加锁消耗的资源本身也比CAS高
 所以在竞争强度不是非常大的时候，CAS一定比锁更高效。
 */
