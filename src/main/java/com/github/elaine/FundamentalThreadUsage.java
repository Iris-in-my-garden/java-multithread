package main.java.com.github.elaine;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 基本线程用法: 1. runnable 2. callable
 * 
 * @author Elaine
 * @date 2022.07.14
 */
public class FundamentalThreadUsage {

	public static void testRunnable() {
		Runnable instance = new MyRunnable();
		Thread testRunnableThread = new Thread(instance);
		System.out.println("-----------test runnable---------");
		testRunnableThread.start();
	}

	public static void testCallable() throws ExecutionException, InterruptedException {
		Callable<Integer> myCallable = new MyCallable();
		FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
		Thread thread = new Thread(futureTask);
		thread.start();
		System.out.println("-----------test callable---------");
		System.out.println(futureTask.get());
	}

	public static class MyRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("my runnable");
		}
	}

	public static class MyCallable implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			return 100;
		}
	}

}
