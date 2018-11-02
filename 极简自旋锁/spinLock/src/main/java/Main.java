public class Main {

    private static void add2000v1(){
        int[] i = new int[1];
        i[0] = 0;
        Thread thread01 = new Thread(()->{
            for(int j = 0;j<20000;++j)
                ++i[0];
        });
        Thread thread02 = new Thread(()->{
            for(int j = 0;j<20000;++j)
                ++i[0];
        });
        thread01.start();
        thread02.start();
        try {
            thread01.join();
            thread02.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i[0]);
    }

    private static void add2000v2(){
        SpinLock spinLock = new SpinLock();
        int[] i = new int[1];
        i[0] = 0;
        Thread thread01 = new Thread(()->{
            for(int j = 0;j<20000;++j){
                spinLock.lock();
                ++i[0];
                spinLock.release();
            }
        });
        Thread thread02 = new Thread(()->{
            for(int j = 0;j<20000;++j){
                spinLock.lock();
                ++i[0];
                spinLock.release();
            }
        });
        thread01.start();
        thread02.start();
        try {
            thread01.join();
            thread02.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i[0]);
    }

    public static void main(String[] args) {
        add2000v1();
        add2000v2();
    }
}
