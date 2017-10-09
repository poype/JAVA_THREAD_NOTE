package concurrency.in.practice.c;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
 对于不能抛出InterruptedException异常的阻塞操作，通过关闭底层资源响应interrupt事件
 这个例子复写了Thread类的interrupt方法，会先关闭底层资源，再调用Thread自己的interrupt
 */
public class ReadThread extends Thread {

    private final Socket socket;
    private final InputStream in;

    public ReadThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close(); //关闭底层资源
        } catch (IOException e) {

        } finally {
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[200];
            while(true) {
                int count = in.read(buf); //阻塞操作
                if (count < 0) {
                    break;
                } else if(count > 0) {
                    // 处理数据
                }
            }
        } catch (IOException e) {
            /* 允许线程退出 */
        }
    }
}

/*
 处理不可中断的阻塞
 in.read()这种阻塞操作是不能抛出InterruptedException异常的，
 所以只能关闭底层资源来让任务退出
 */
