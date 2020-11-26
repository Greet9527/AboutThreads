package syncs;

import java.util.ArrayList;
import java.util.List;
/*
* JDK1.6前 synchronized采用的是重度锁实现
* 1.6之后进行优化，会根据所同步的对象具体情况进行优化。无锁-》偏向锁-》轻量锁-》重度锁
* 可重入，注意死锁.公平锁
* */
public class SyncsDemo {
    private static final Object object=new Object();

    static class MThread extends Thread{
        final Object object;

        MThread(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread :"+this.getId()+" get sync before");
            synchronized (object){
                System.out.println("Thread:"+this.getId()+" get sync");
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Thread:"+this.getId()+" get sync after "+i+" s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        List<MThread> mThreads=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            MThread thread=new MThread(object);
            mThreads.add(thread);
            thread.start();

        }
    }
}
