package src;

import java.util.concurrent.Semaphore;

public class createThreads {
    private int cantHilos;
    private Runnable proceso;
    private String nameProceso;
    public createThreads(int cantHilos, Runnable proceso, String nameProceso){
        this.cantHilos = cantHilos;
        this.proceso = proceso;
        this.nameProceso = nameProceso;
        run();
    }
    private void run(){     //privada para que no se pueda acceder desde otro lado,
                            //solo se ejecuta cuando se instancia un objeto createThreads
        for (int i = 0; i<cantHilos; i++){
            String nameThread = "Thread - "+ i+" - " + nameProceso;
            Thread thread = new Thread(proceso, nameThread);
            thread.start();
        }
    }
}
