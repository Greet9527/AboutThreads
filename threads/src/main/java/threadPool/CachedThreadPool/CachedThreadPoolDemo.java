package threadPool.CachedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolDemo {

    /* 适合处理简单任务，快速执行快速返回
    * 它是一个可以无限扩大的线程池；它比较适合处理执行时间比较小的任务；
    * corePoolSize为0，maximumPoolSize为无限大，意味着线程数量可以无限大；
    * keepAliveTime为60S，意味着线程空闲时间超过60S就会被杀死；
    * 采用SynchronousQueue装等待的任务，这个阻塞队列没有存储空间，这意味着只要有请求到来，就必须要找到一条工作线程处理他，如果当前没有空闲的线程，那么就会再创建一条新的线程。
    * */
    public static void cacheThreadPool() {
        ExecutorService cachedThreadPool= Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int ii=i;
            try {
                Thread.sleep(ii);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(()->System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii));
        }
    }
    public static void main(String[] args) {
        cacheThreadPool();
    }
}
