package main.java.com.github.shuran.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author shuran
 * @date 2022.07.27
 * <p>
 * 循环栅栏
 * 适用于并行迭代计算，每个线程负责计算一部分，等所有线程都完成到达栅栏后，互相交换数据，再进行下一次迭代
 * 可重复
 * 所有线程互相影响，只要有其中一个线程await时被中断了或者超时了，栅栏就会被破坏，所有await线程退出，抛出异常
 */
public class CyclicBarrierExample {

    public static void main(String[] args) {
        final int threadNum = 10;
        Thread[] threads = new Thread[threadNum];
        CyclicBarrier barrier = new CyclicBarrier(threadNum, () -> {
            System.out.println("all arrived " + System.currentTimeMillis() + " executed by: " + Thread.currentThread().getName());

        });
        for (Thread thread : threads) {
            thread = new Tourist(barrier);
            thread.start();
        }
    }

    public static class Tourist extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Tourist(CyclicBarrier barrier) {
            cyclicBarrier = barrier;
        }

        @Override
        public void run() {

            try {
                Thread.sleep((int) (Math.random() * 1000));
                //集合点A
                cyclicBarrier.await();
                System.out.println(getName() + " arrived A: " + System.currentTimeMillis());

                //之后再各自运行
                Thread.sleep((int) (Math.random() * 1000));
                //集合点B
                cyclicBarrier.await();
                System.out.println(getName() + " arrived B: " + System.currentTimeMillis());
            } catch (InterruptedException e) {

            } catch (BrokenBarrierException e) {

            }
        }
    }

}
