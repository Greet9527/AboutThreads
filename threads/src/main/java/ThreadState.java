import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;

/*
* 描述线程状态
*
* */
public class ThreadState {

    static class NewState extends Thread {
        NewState(){
            super();
            System.out.println("线程 ID:"+this.getId()+"目前状态为:"+this.getState());
        }
    }

    static class RunnableState extends Thread{
        @Override
        public synchronized void start() {
            super.start();
            System.out.println("线程目前状态为  RUNNABLE 可执行状态");
        }
    }
    /*RUNNABLE - TIMED_WAITING
    *  1、 Thread.sleep(long millis)
       2、 获得 synchronized 隐式锁的线程调用 Object.wait(long timeout)
       3、 Thread.join(long millis)
       4、 LockSupport.parkNanos(Object blocker, long deadline)
       5、 LockSupport.parkUntil(long deadline)
    * */
    static class TimeWaitingState implements Runnable{
        @Override
        public void run() {
            while (true) {
                synchronized (TimeWaitingState.class) {
                    try {
                        System.out.println("开始timewait，超时五秒唤醒");
                        TimeWaitingState.class.wait(5000);
                        System.out.println("已被唤醒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /*
    1、获得synchronized隐式锁的线程，调用Object.wait();
    2、Thread.join();
    3、LockSupport.park();
    * */
    static class WaitingState implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (WaitingState.class){
                    try {
                        WaitingState.class.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
    /*
     * 被IO流之类中断，或者等待synchronized 锁
     * */
    static class BlockState implements Runnable{
        @Override
        public void run() {
            Scanner scanner=new Scanner(System.in);
            System.out.println("使用输入流将线程Block，输入任意数字后线程继续执行");
            int in=scanner.nextInt();
            System.out.println("输入："+in+"，线程继续执行");
        }
    }

    /*1、run()执行完后自动转为 TERMINATED
      2、stop()(@Deprecated 直接结束线程，如果线程持有ReentrantLock锁并不会释放)
      3、interrupt()
            异常通知
            主动监测
    * */
    static class TerminateState implements Runnable{

        void throwException() throws Exception {
            throw new  Exception("has an exception");
        }
        @Override
        public void run() {
            try {
                throwException();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
       // new NewState();
       // new RunnableState().start();
        //        {
        //            Thread thread = new Thread(new WaitingState());
        //            thread.start();
        //            Thread.sleep(1000);
        //            System.out.println(thread.getState());
        //        }
//                {
//            Thread thread = new Thread(new TimeWaitingState());
//            thread.start();
//            Thread.sleep(1000);
//            System.out.println(thread.getState());
//        }
        Thread thread = new Thread(new BlockState());
        thread.start();
    }
}
