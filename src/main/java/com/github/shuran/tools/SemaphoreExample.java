package main.java.com.github.shuran.tools;

import java.util.concurrent.Semaphore;

/**
 * @author shuran
 * @date 2022.07.27
 * <p>
 * 信号量 非重入，任何线程都可以调用release方法
 * 限制对资源的并发访问数
 */
public class SemaphoreExample {

    public static void main(String[] args) {
        AccessControlService service = new AccessControlService();
        final int threadNum = 20;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                service.login(Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                service.logout(Thread.currentThread().getName());
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }


    public static class AccessControlService {
        private static final int MAX_PERMITS = 5;
        private final Semaphore permits = new Semaphore(MAX_PERMITS); //许可数量

        public synchronized void login(String name) {
            if (!permits.tryAcquire()) {
                System.out.println(name + " login failed!");
                return;
            }
            System.out.println(name + " login success!");
        }

        public synchronized void logout(String name) {
            synchronized (this) {
                permits.release();
                System.out.println(name + " logout!");
            }
        }
    }

    public static class ConcurrentLimitException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
