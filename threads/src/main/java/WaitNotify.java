import java.util.concurrent.Semaphore;

public class WaitNotify {

    public static Object object=new Object();
    static class WaitThread extends Thread{
        public final Object object;

        WaitThread(Object object) {
            this.object = object;
        }
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("WaitThread run...");
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(i*1000);
                        System.out.println("WaitThread wait before "+ i+" s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(i*1000);
                        System.out.println("WaitThread wait after "+ i+" s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    static class NotifyThread extends Thread{
        public final Object object;

        NotifyThread(Object object) {
            this.object = object;
        }
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("NotifyThread run...");
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(i*1000);
                        System.out.println("NotifyThread notify before "+ i+" s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(i*1000);
                        System.out.println("NotifyThread notify after "+ i+" s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new WaitThread(object).start();
        new NotifyThread(object).start();
    }
}
