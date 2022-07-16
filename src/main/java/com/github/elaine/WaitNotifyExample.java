package main.java.com.github.elaine;

/**
 * 
 * @author Elaine
 * @Date 2022.07.16
 * 
 *       每个对象都有wait notify方法
 * 
 *       测试wait notify, 所有线程等着比赛信号开始
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
						wait(); // 等待比赛开始的信号

					}
				}
				System.out.println("start run: " + Thread.currentThread().getName());

			} catch (Exception e) {

			}

		}

		public synchronized void fire() {
			this.fire = true; // 发射比赛开始的信号
			notifyAll();
//			notify();
		}

	}

}
