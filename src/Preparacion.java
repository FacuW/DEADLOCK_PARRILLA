package src;

public class Preparacion implements Runnable{
    public Preparacion(){

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());  //solo para ver que corra
    }
}
