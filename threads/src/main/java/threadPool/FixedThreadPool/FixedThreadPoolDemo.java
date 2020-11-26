package threadPool.FixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {

    /*
    * 一种固定大小的线程池
    * corePoolSize和maximunPoolSize都为用户设定的线程数量nThreads；
    * keepAliveTime为0，意味着一旦有多余 的空闲线程，就会被立刻停止掉；但这里keepAliveTime无效；
    * 阻塞队列采用LinkedBlockingQueue，它是一个无界队列；
    * 由于阻塞队列是一个无界队列，因此永远不可能拒绝任务；
    * */
    public static void fixedThreadPool(){
        ExecutorService fixedThreadPool= Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int ii=i;
            fixedThreadPool.execute(()->{
                System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    public static void main(String[] args) {
        fixedThreadPool();
    }
}
