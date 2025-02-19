package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private int ID;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    public static int terminat = 0;

    public Server(int id, int maxTasksPerServer){
        ID = id;
        tasks = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public void run(){
        while(terminat == 0){

            while(tasks.peek() != null)
            {
                Task t = tasks.peek();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                waitingPeriod.decrementAndGet();
                t.setServiceTime(t.getServiceTime() - 1);
                if(t.getServiceTime() == 0)
                {
                    try {
                        tasks.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public Task[] getTasks(){

        int index = 0;
        Task[] rez = new Task[tasks.size()];
        for(int i = 0; i < tasks.size(); i++)
        {
            rez[index++] = tasks.peek();
        }
        return rez;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public String toString() {
        String rez = "\nQueue " + this.ID + ": ";
        if(waitingPeriod.get() > 0 && tasks.peek() != null)
            rez += tasks.toString();
        else
            rez += "closed";

        return rez;
    }
}
