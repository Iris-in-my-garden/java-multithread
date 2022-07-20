package main.java.com.github.shuran.basic;

import main.java.com.github.shuran.basic.*;

public class MultiThreadTest {
	public static void main(String[] args) {
//		testUnsafeThread();
//		testRunnableAndCallableThread();
//		testReentrantLock();
//		testWaitAndNotify();
//		testAwaitAndSignal();
		testSynchronized();
	}

	public static void testUnsafeThread() {
		try {
			ThreadUnsafeExample.testRaceCondition();
			ThreadUnsafeExample.testMemoryVisible();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void testRunnableAndCallableThread() {
		try {
			FundamentalThreadUsage.testRunnable();
			FundamentalThreadUsage.testCallable();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void testReentrantLock() {
		ReentrantlockExample.testReentrantLock();
	}

	public static void testWaitAndNotify() {
		try {
			WaitNotifyExample.testWaitAndNotify();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testAwaitAndSignal() {
		try {
			AwaitSignalExample.testAwaitAndSignal();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testSynchronized(){
		try {
			SynchronizedExample.testSynchronized();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
