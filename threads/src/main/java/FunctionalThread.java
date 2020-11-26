public class FunctionalThread {
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("hello thread");
        }).start();
        Runnable hello = () -> {
            System.out.println("hello");
        };
        new Thread(hello).start();
    }
}
