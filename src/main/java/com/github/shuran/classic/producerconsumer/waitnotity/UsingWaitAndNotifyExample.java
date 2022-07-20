package main.java.com.github.shuran.classic.producerconsumer.waitnotity;

/**
 * 生产者消费者问题
 * 用Object自带的wait notify实现
 */
public class UsingWaitAndNotifyExample {
    public static void main(String[] args) {
        MyBlockingQueue<String> queue = new MyBlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        producer.start();
        consumer.start();
    }
}
