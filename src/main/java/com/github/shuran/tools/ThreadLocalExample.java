package main.java.com.github.shuran.tools;


/**
 * @author shuran
 * @date 2022.07.27
 * <p>
 * 线程本地变量
 */
public class ThreadLocalExample {
    private static ThreadLocal<Integer> local = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        local.set(100);
        System.out.println(" main thread local: " + local.get());
        Thread thread = new Thread(() -> {
            System.out.println(" child thread initial local: " + local.get());
            local.set(2000);
            System.out.println(" child thread local: " + local.get());
        });
        local.set(400);
        thread.start();
        thread.join();
        System.out.println(" main thread local: " + local.get());
    }
}
