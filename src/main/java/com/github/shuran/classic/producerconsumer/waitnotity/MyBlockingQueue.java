package main.java.com.github.shuran.classic.producerconsumer.waitnotity;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @param <E> 放入的生产产品类型
 * @author shuran
 * @date 2020.07.20
 * <p>
 * 生产者与消费者的示例
 * 使用Object对象自带的wait和notity来实现
 */
public class MyBlockingQueue<E> {
    private Queue<E> queue = null; //存放产品的队列
    private int limit = 0;  //队列里最多能存放多少个产品

    public MyBlockingQueue(int limit) {
        this.limit = limit;
        queue = new ArrayDeque<>(limit);
    }

    public synchronized void put(E item) throws InterruptedException {
        while (queue.size() == limit) {
            wait(); //
        }
        queue.offer(item);
        System.out.println("put: " + item);
        notifyAll();
    }

    public synchronized E get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        E item = queue.poll();
        notifyAll();
        System.out.println("get: " + item);
        return item;
    }
}
