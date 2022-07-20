package main.java.com.github.shuran.classic.producerconsumer.waitnotity;

/**
 * @author shuran
 * @date 2022.07.20
 * <p>
 * 生产者
 */
public class Producer extends Thread {
    MyBlockingQueue<String> queue;

    public Producer(MyBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            int num = 0;
            while (num < 50) {
                String item = String.valueOf(num);
                queue.put(item);
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
