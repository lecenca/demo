import java.util.concurrent.atomic.AtomicInteger;

public class SpinLock {
    private AtomicInteger i = new AtomicInteger(0);

    public void lock(){
        while (!i.compareAndSet(0,1));
    }

    public void release(){
        i.set(0);
    }
}
