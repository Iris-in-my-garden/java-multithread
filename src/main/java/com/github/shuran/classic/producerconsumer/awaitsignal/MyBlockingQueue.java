package main.java.com.github.shuran.classic.producerconsumer.awaitsignal;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @param <E> 放入的生产产品类型
 * @author shuran
 * @date 2020.07.20
 * <p>
 * 生产者与消费者的示例
 * 使用await和signal来同步
 */
public class MyBlockingQueue<E> {
    private Queue<E> queue;
    private int limit = 0;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public MyBlockingQueue(int limit) {
        this.limit = limit;
        queue = new ArrayDeque<>(limit);
    }

    public void put(E item) throws InterruptedException {
        try {
            lock.lock();

            while (queue.size() == limit) {
                notFull.await();
            }
            queue.offer(item);
            System.out.println("put: " + item);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public E get() throws InterruptedException {
        try {
            lock.lock();
            while (queue.isEmpty()) {
                notEmpty.await();
            }

            E item = queue.poll();
            System.out.println("get: " + item);
            notFull.signalAll();
            return item;

        } finally {
            lock.unlock();
        }
    }

}
