package main.java.com.github.shuran;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author shuran
 * @date 2022.07.16
 * 
 * 测试 ReentrantLock
 */
public class ReentrantlockExample {

	public static void testReentrantLock() {
		LockExample lockExample = new LockExample();
		final int threadNum = 3;
		Thread[] threads = new Thread[threadNum];
		for (int i = 0; i < threadNum; i++) {
			threads[i] = new Thread() {
				public void run() {
					lockExample.func();
				}
			};
			threads[i].start();
		}
	}

	public static class LockExample {
		private Lock lock = new ReentrantLock();
		private int count = 0;

		public void func() {
			lock.lock();
			try {
				for (int i = 0; i < 10; i++) {
					count++;
					System.out.println(count);
				}
			} finally { // finally要释放锁
				lock.unlock();
			}
		}

	}

}
