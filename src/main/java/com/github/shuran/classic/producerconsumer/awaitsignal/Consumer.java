package main.java.com.github.shuran.classic.producerconsumer.awaitsignal;

/**
 * @author shuran
 * @date 2022.07.20
 * <p>
 * 消费者
 */
public class Consumer extends Thread {
    MyBlockingQueue<String> queue;

    public Consumer(MyBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            int num = 0;
            while (num < 50) {
                String item = queue.get();
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
