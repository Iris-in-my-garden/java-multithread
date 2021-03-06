package main.java.com.github.shuran.basic;

/**
 * 
 * @author shuran
 * @date 2022.07.13
 * 1. 测试竞态条件
 * 2. 测试可见性问题
 */
public class ThreadUnsafeExample {
	public static boolean flag = false;

	public static void testRaceCondition() throws InterruptedException {
		final int threadSize = 1000;
		Thread[] threads = new Thread[threadSize];
		for (int i = 0; i < threadSize; i++) {
			threads[i] = new CounterThread();
			threads[i].start();
		}

		for (int i = 0; i < threadSize; i++) {
			threads[i].join(); // 等待
		}

		System.out.println("-----test race condition------");
		System.out.println(CounterThread.counter);
	}

	public static void testMemoryVisible() throws InterruptedException {
		Thread displayThread = new DisplayThread();
		System.out.println("-----test Memory Visible------");
		displayThread.start();
		Thread.sleep(200);
		flag = false;
		System.out.println("exit testMemoryVisible");
	}

	public static class CounterThread extends Thread {
		public static int counter = 0;

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				counter++;
			}
		}

	}

	public static class DisplayThread extends Thread {
		@Override
		public void run() {
			while (!flag) {
				System.out.println("Display Thread flag true");
			}
		}
	}

}
