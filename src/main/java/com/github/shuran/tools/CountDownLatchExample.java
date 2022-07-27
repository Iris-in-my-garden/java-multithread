package main.java.com.github.shuran.tools;

import java.util.concurrent.CountDownLatch;

/**
 * @author shuran
 * @date 2022.07.27
 * <p>
 * 倒计时门栓
 * - 门栓关闭，所有线程等待
 * - 门栓打开。所有线程可以通过
 * - 一次性的，打开后就不能关上了
 * <p>
 * 应用场景：
 * 1、同时开始
 * 2、主从协作
 */
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
//        testRacer();
        testMasterWorker();
    }

    public static void testRacer() throws InterruptedException {
        System.out.println("---------------------test racer-------------------");
        Racer racer = new Racer();
        final int threadNum = 10;
        Thread[] threads = new Thread[threadNum];
        for (Thread thread : threads) {
            thread = new Thread(racer);
            thread.start();
        }

        Thread.sleep(1000);
        racer.begin();

    }

    public static void testMasterWorker() throws InterruptedException {
        final int threadNum = 10;
        CountDownLatch latch = new CountDownLatch(threadNum);
        Thread[] threads = new Thread[threadNum];
        for (Thread thread : threads) {
            thread = new Worker(latch);
            thread.start();
        }
        latch.await();
        System.out.println("collect worker result");
    }

    /**
     * 同时开始
     */
    public static class Racer implements Runnable {
        private CountDownLatch latch = new CountDownLatch(1);

        @Override
        public void run() {
            try {
                latch.await();
                System.out.println(Thread.currentThread().getName() + " start run " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void begin() {
            latch.countDown();
        }
    }

    /**
     * 主从协作
     */
    public static class Worker extends Thread {
        private CountDownLatch latch;

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                //do something
                Thread.sleep((int) (Math.random() * 1000));
                System.out.println(Thread.currentThread().getName() + " worker done! " + System.currentTimeMillis());
            } catch (InterruptedException e) {

            } finally {
                latch.countDown();
            }
        }
    }
}
