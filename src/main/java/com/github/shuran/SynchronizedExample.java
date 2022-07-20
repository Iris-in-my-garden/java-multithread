package main.java.com.github.shuran;

/**
 * @author shuran
 * @date 2022.07.20
 * 测试synchronized 对象锁和类锁，以及可重入性质
 */
public class SynchronizedExample {


    public static void testSynchronized(){
        final int threadNum=2;
        Thread[] threads=new Thread[threadNum];

        //test object lock
        DisplayRunnable runnable1=new DisplayRunnable(DisplayRunnable.testObjectLock);

        for(int i=0;i<threadNum;i++){
            threads[i]=new Thread(runnable1);
            threads[i].start();
        }

        //等待结束，再进行下一个
        waitForLastOperationEnd(threads);

        DisplayRunnable runnable2=new DisplayRunnable(DisplayRunnable.testExplicitObjectLock);
        for(int i=0;i<threadNum;i++){
            threads[i]=new Thread(runnable2);
            threads[i].start();
        }

        waitForLastOperationEnd(threads);

        //如果是两个对象，则互不影响
        DisplayRunnable subRunnable1=new DisplayRunnable(DisplayRunnable.testExplicitObjectLock);
        DisplayRunnable subRunnable2=new DisplayRunnable(DisplayRunnable.testExplicitObjectLock);
        threads[0]=new Thread(subRunnable1);
        threads[1]=new Thread(subRunnable2);
        threads[0].start();
        threads[1].start();

        waitForLastOperationEnd(threads);

        //test class lock
        DisplayRunnable runnable3=new DisplayRunnable(DisplayRunnable.testClassLock);
        for(int i=0;i<threadNum;i++){
            threads[i]=new Thread(runnable3);
            threads[i].start();
        }

        waitForLastOperationEnd(threads);

        DisplayRunnable runnable4=new DisplayRunnable(DisplayRunnable.testExplicitClassLock);
        for(int i=0;i<threadNum;i++){
            threads[i]=new Thread(runnable4);
            threads[i].start();
        }


        waitForLastOperationEnd(threads);

        //test reentrant
        DisplayRunnable runnable5=new DisplayRunnable(DisplayRunnable.testReentrantMethod);
        threads[0]=new Thread(runnable5);
        threads[0].start();

        waitForLastOperationEnd(threads);
    }

    /**
     * 等待线程结束
     * @param threads
     */
    private static void waitForLastOperationEnd(Thread[] threads) {
        try {
            for(int i=0;i< threads.length;i++){
                threads[i].join();
            }
            System.out.println("---------waitForLastOperationEnd-------------");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static class DisplayRunnable implements Runnable{
        public static final int testObjectLock=0;
        public static final int testExplicitObjectLock=1;
        public static final int testClassLock=2;
        public static final int testExplicitClassLock=3;
        public static final int testReentrantMethod=4;

        private int testType=0;

        public DisplayRunnable(int testType){
            this.testType=testType;
        }

        @Override
        public void run() {
            if(testType==testObjectLock){
                printUsingObjectLock();
            } else if(testType==testExplicitObjectLock){
                printUsingExplicitObjectLock();
            } else if(testType==testClassLock){
                printUsingClassLock();
            } else if(testType==testExplicitClassLock){
                printUsingExplicitClassLock();
            } else if(testType==testReentrantMethod){
                printReentrantMethod();
            }
        }

        //对象锁，成员方法加上synchronized
        private synchronized void printUsingObjectLock(){
            System.out.println("printUsingObjectLock "+Thread.currentThread().getName()+" start");
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("printUsingObjectLock "+Thread.currentThread().getName()+" end");
        }

        //对象锁，synchronized(对象)
        private void printUsingExplicitObjectLock(){
            synchronized (this){
                System.out.println("printUsingExplicitObjectLock "+Thread.currentThread().getName()+" start");
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("printUsingExplicitObjectLock "+Thread.currentThread().getName()+" end");
            }
        }

        //类锁，static方法加上synchronized
        private static synchronized void printUsingClassLock(){
            System.out.println("printUsingClassLock "+Thread.currentThread().getName()+" start");
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("printUsingClassLock " +Thread.currentThread().getName()+" end");
        }

        //类锁，synchronized(类)
        private static void printUsingExplicitClassLock(){
            synchronized (DisplayRunnable.class){
                System.out.println("printUsingExplicitClassLock "+Thread.currentThread().getName()+" start");
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("printUsingExplicitClassLock " +Thread.currentThread().getName()+" end");
            }
        }

        //可重入特性测试
        private synchronized void printReentrantMethod(){
            System.out.println("printReentrantMethod "+Thread.currentThread().getName()+" start");
            method();
            System.out.println("printReentrantMethod "+Thread.currentThread().getName()+" end");
        }

        private synchronized void method()
        {
            System.out.println("method "+Thread.currentThread().getName()+" start");
        }
    }

}
