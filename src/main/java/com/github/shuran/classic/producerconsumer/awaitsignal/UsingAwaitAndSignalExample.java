package main.java.com.github.shuran.classic.producerconsumer.awaitsignal;


/**
 * 生产者消费者问题
 * 用显式锁的await signal实现
 */
public class UsingAwaitAndSignalExample {
    public static void main(String[] args) {

        MyBlockingQueue<String> queue = new MyBlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        producer.start();
        consumer.start();
    }
}
