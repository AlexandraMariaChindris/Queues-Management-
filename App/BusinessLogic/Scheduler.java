package BusinessLogic;

import Model.Server;
import Model.Task;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int noServers;
    private Strategy strategy;


    public Scheduler(int noServers, int maxTasksPerServer){
        this.noServers = noServers;

        servers = new ArrayList<Server>(this.noServers);
        Server.terminat = 0;
        for(int i = 0; i < this.noServers; i++)
        {
            Server x = new Server(i + 1, maxTasksPerServer);
            servers.add(x);
            Thread t = new Thread(x);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQueueStrategy();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new TimeStrategy();
        }
    }

    public Server dispatchTask(Task task){
        return strategy.addTask(servers, task);
    }

    public String toString(){
        String rez = "";
        for(Server s: servers)
            rez += s.toString();
        return rez;
    }

    public int getTaskTotal(){
        int nr = 0;
        for(Server s: servers)
            nr += s.getTasks().length;
        return nr;
    }

    public int getTotalWaitingTime(){
        int time = 0;
        for(Server s: servers)
            time += s.getWaitingPeriod().get();
        return time;
    }

}