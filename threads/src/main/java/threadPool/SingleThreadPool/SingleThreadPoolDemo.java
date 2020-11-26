package threadPool.SingleThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPoolDemo {

    /*
    只会创建一个处理任务的线程；
    任务队列采用LinkedBlockingQueue；容易OOM
    * */
    public static void singleThreadPool(){
            ExecutorService pool = Executors.newSingleThreadExecutor();
            for (int i = 0; i < 10; i++) {
                final int ii = i;
                pool.execute(() ->System.out.println(Thread.currentThread().getName() + "=>" + ii));
            }
    }
    public static void main(String[] args) {
        singleThreadPool();
    }
}
