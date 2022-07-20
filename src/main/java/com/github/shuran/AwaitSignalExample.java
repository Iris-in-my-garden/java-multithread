package main.java.com.github.shuran;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shuran
 * @date 2022.07.16
 * 
 * 测试  await signal
 */
public class AwaitSignalExample {

	public static void testAwaitAndSignal() throws InterruptedException {
		WaitRunnable runnable = new WaitRunnable();
		final int threadNum = 10;
		Thread[] threads = new Thread[threadNum];
		for (int i = 0; i < threadNum; i++) {
			threads[i] = new Thread(runnable);
			threads[i].start();
		}

		Thread.sleep(1000);
		System.out.println("prepared to fire!");
		runnable.fire();
	}

	public static class WaitRunnable implements Runnable {
		private boolean fire = false;
		private Lock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		@Override
		public void run() {
			try {
				lock.lock();
				while (!fire) {
					condition.await();
				}
				System.out.println("start run: " + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void fire() {
			try {
				lock.lock();
				fire = true;
				condition.signalAll();

			} finally {
				lock.unlock();
			}
		}
	}

}
