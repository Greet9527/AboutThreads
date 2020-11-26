package lockImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    static final ReentrantLock fairLock=new ReentrantLock(true);
    static final ReentrantLock noFairLock=new ReentrantLock();

    static class FairLockThread extends Thread{
        final ReentrantLock fairLock;

        FairLockThread(ReentrantLock fairLock) {
            this.fairLock = fairLock;
        }

        @Override
        public void run() {
            System.out.println("线程："+getName()+" Lock before");
            while (true) {
                fairLock.lock();
                try {
                    //TODO
                        System.out.println("线程：" + getName() + " isLocked: " + (fairLock.isLocked() ? "true" : "false"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    fairLock.unlock();
                }
            }
        }
    }

    static class NoFairLockThread extends Thread{
        final ReentrantLock noFairLock;
        NoFairLockThread(ReentrantLock noFairLock) {
            this.noFairLock = noFairLock;
        }

        @Override
        public void run() {
            System.out.println("线程："+getName()+" noFairLock before");

            while (true) {
                noFairLock.lock();
                try {
                    //TODO
                        System.out.println("线程：" + getName() + " isLocked: " + (noFairLock.isLocked() ? "true " : "false "));

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    noFairLock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
//        List<NoFairLockThread> noFairLocks=new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            NoFairLockThread noFairLockThread = new NoFairLockThread(noFairLock);
//            noFairLockThread.start();
//        }

        List<FairLockThread> fairLocks=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            FairLockThread fairLockThread = new FairLockThread(fairLock);
            fairLockThread.start();
        }
    }
}
