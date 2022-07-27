package main.java.com.github.shuran.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author shuran
 * @date 2022.07.27
 * <p>
 * 读写锁
 * 只有”读-读“锁可以并行
 */
public class ReentrantReadWriteLockExample {

    public static void main(String[] args) {
        testReentrantReadWriteLock();
    }

    public static void testReentrantReadWriteLock() {
        MyCache<String> cache = new MyCache<>();
        final int threadsNum = 10;
        Thread[] threads = new Thread[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            int finalI = i;
            if (i % 2 == 0) {
                threads[i] = new Thread(() -> {
                    cache.put(String.valueOf(finalI), Thread.currentThread().getName());
                });
            } else {
                threads[i] = new Thread(() -> {
                    String result = cache.get(String.valueOf(finalI - 1));
                    System.out.println("get: " + result);
                });
            }
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }


    public static class MyCache<T> {
        private Map<String, T> map = new HashMap<>();
        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = readWriteLock.readLock();
        private Lock writeLock = readWriteLock.writeLock();

        public T get(String key) {
            try {
                readLock.lock();
                return map.get(key);
            } finally {
                readLock.unlock();
            }
        }

        public void put(String key, T value) {
            try {
                writeLock.lock();
                System.out.println("put: key " + key + " value " + value);
                map.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

        public void clear() {
            try {
                writeLock.lock();
                map.clear();
            } finally {
                writeLock.unlock();
            }
        }
    }
}
