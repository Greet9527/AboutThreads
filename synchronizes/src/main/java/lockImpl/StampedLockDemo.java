package lockImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    static class RWLockTest{
        static ExecutorService service= Executors.newFixedThreadPool(10);
        static StampedLock lock=new StampedLock();
        static long milli=5000;
        static int count=0;

        static long writeLock(){
            long stamp=lock.writeLock();  //获取排他写锁
            count+=1;
            lock.unlockWrite(stamp);      //释放写锁
            System.out.println("数据写入完成");
            return System.currentTimeMillis();
        }

        static void readLock(){
            service.submit(()->{
                int currentCount=0;
                long stamp = lock.readLock();  //获取排他写锁
                try{
                    currentCount=count;         //获取变量值
                    TimeUnit.MILLISECONDS.sleep(milli); //模拟读取需要花费20秒
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlockRead(stamp);     //释放读锁
                }
                System.out.println("readLock=="+currentCount);  //显示最新的变量值
            });

            try {
                TimeUnit.MILLISECONDS.sleep(500);       //等读锁获得
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        private static void optimisticRead() {
            service.submit(() -> {
                long stamp = lock.tryOptimisticRead(); //尝试获取乐观读锁
                int currentCount = count; //获取变量值
                try {
                    TimeUnit.MILLISECONDS.sleep(milli);//模拟读取需要花费20秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!lock.validate(stamp)) { //判断count是否进入写模式
                    stamp = lock.readLock(); //已经进入写模式，没办法只能老老实实的获取读锁
                    try {
                        currentCount = count; //成功获取到读锁，并重新获取最新的变量值
                    } finally {
                        lock.unlockRead(stamp); //释放读锁
                    }
                }
                //走到这里，说明count还没有被写，那么可以不用加读锁，减少了读锁的开销
                System.out.println("optimisticRead==" + currentCount); //显示最新的变量值
            });
            try {
                TimeUnit.MILLISECONDS.sleep(500);//要等一等读锁先获得
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public static void main(String[] args) {
            long readBefore=System.currentTimeMillis();
            readLock();
            long writeAfter=writeLock();
            System.out.println(writeAfter - readBefore);

            long optimisticReadBefore=System.currentTimeMillis();
            optimisticRead();
            long optimisticWriteAfter=writeLock();
            System.out.println(optimisticWriteAfter - optimisticReadBefore);
        }
    }
}
