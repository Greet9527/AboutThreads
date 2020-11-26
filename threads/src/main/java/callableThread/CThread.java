package callableThread;

import javax.management.monitor.Monitor;
import java.util.concurrent.*;
public class CThread implements Callable<String>{
    @Override
    public java.lang.String call() throws Exception {
        System.out.println("子线程正在执行");
        Thread.sleep(10000);
        return "CallableReturn";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask=new FutureTask<>(new CThread());
        new Thread(futureTask).start();
        System.out.println("hello end");
    }
}
