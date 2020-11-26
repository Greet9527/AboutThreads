package semaphore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/*
* */
public class SemaphoreDemo {
    /*semaphore 实现互斥*/
    static class MutexSemaphore{
        private static int count;

        /*初始化信号量 permits 为1 表示只允许一个线程进入临界区*/
        private static final Semaphore s=new Semaphore(1);

        /*用信号量保证互斥*/
        static void addOne() throws InterruptedException {
            s.acquire();
            try {
                count += 1;
                //TODO some exception operate
            }finally {
                s.release();
            }
        }
    }

    /*
    * 实现限流器
    * */
    static class ObjPool<T,R>{
        final List<T> pool;

        //用信号量实现限流器
        final Semaphore semaphore;
        
        ObjPool(int size,T t){
            pool=new Vector<T>(){};
            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            semaphore=new Semaphore(size);
        }

        R exec(Function<T,R> func) throws InterruptedException {
            T t=null;
            semaphore.acquire();
            try {
                t=pool.remove(0);
                return func.apply(t);
            }finally {
                pool.add(t);
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ObjPool<Object,String> pool=new ObjPool<>(10,"Worker");
        pool.exec(t->{
            System.out.println(t);
            return t.toString();
        });
    }
}
