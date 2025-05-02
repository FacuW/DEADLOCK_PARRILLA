package src;

public class CreateThreads {
    private int cantHilos;
    private Runnable proceso;
    private String nameProceso;
    public CreateThreads(int cantHilos, Runnable proceso, String nameProceso){
        this.cantHilos = cantHilos;
        this.proceso = proceso;
        this.nameProceso = nameProceso;
        lanzarHilos();
    }
    private void lanzarHilos(){   //solo se ejecuta cuando se instancia un objeto CreateThreads
        for (int i = 0; i<cantHilos; i++){
            String nameThread = "Thread - "+ i+" - " + nameProceso;
            Thread thread = new Thread(proceso, nameThread);
            thread.start();
        }
    }
}