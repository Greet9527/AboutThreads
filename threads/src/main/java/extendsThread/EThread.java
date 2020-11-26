package extendsThread;

import java.util.ArrayList;
import java.util.List;

public class EThread extends Thread{
    @Override
    public void run() {
        System.out.println("this thread id:"+ super.getId());
    }

    public static void main(String[] args) {
        List<EThread> eThreads=new ArrayList();
        for (int i = 0; i < 10; i++) {
            EThread eThread = new EThread();
            eThreads.add(eThread);
            eThread.start();
        }
    }
}
