package runableThread;

import java.util.ArrayList;
import java.util.List;

public class RThread implements Runnable{
    @Override
    public void run() {
        System.out.println("This is a RThread");
    }

    public static void main(String[] args) {
        List<Thread> threads=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread=new Thread(new RThread());
            threads.add(thread);
            System.out.println("This RThread id:"+ thread.getId());
            thread.start();
        }
    }
}
