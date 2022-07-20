package main.java.com.github.shuran;

/**
 * 
 * @author shuran
 * @Date 2022.07.16
 * 
 * 测试wait notify
 *
 */
public class WaitNotifyExample {

	public static void testWaitAndNotify() throws InterruptedException {
		WaitRunnable runnable = new WaitRunnable();
		final int threadNum = 10;
		Thread[] threads = new Thread[threadNum];
		for (int i = 0; i < threadNum; i++) {
			threads[i] = new Thread(runnable);
			threads[i].start();
		}
		System.out.println("sleep now!");
		Thread.sleep(1000);
		System.out.println("prepare to fire!");
		runnable.fire();
	}

	public static class WaitRunnable implements Runnable {
		private boolean fire = false;

		@Override
		public void run() {
			try {
				synchronized (this) {
					while (!fire) {
//						System.out.println("wait run: " + Thread.currentThread().getName());
						wait(); //等待比赛信号

					}
				}
				System.out.println("start run: " + Thread.currentThread().getName());

			} catch (Exception e) {

			}

		}

		public synchronized void fire() {
			this.fire = true; // 发出比赛信号
			notifyAll();
//			notify();
		}

	}

}
