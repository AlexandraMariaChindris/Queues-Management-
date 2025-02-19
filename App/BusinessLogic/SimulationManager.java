package BusinessLogic;

import GUI.View;
import Model.Server;
import Model.Task;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static java.lang.Thread.sleep;
import java.awt.event.ActionListener;

public class SimulationManager implements Runnable, ActionListener{
    private int t_max_simulation;
    private int t_min_arrival;
    private int t_max_arrival;
    private int t_min_service;
    private int t_max_service;
    private int numberOfServers;
    private int numberOfClients;
    private static final int MAX_TASKS_PER_SERVER = 1000;
    private SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private static final Logger LOGGER = Logger.getLogger(SimulationManager.class.getName());
    private View view;
    private List<Task> generatedTasks;
    private int peakHour;
    private double averageServiceTime = 0;
    private double averageWaitingTime = 0;
    private int maxTasks;

    public SimulationManager(View v){
        this.view = v;
    }
    private void generateNRandomTasks(){
        generatedTasks = new ArrayList<Task>(numberOfClients);
        for(int i = 0; i < numberOfClients; i++)
        {
            Task t = new Task(i, randomArrivalTime(), randomServiceTime());
            generatedTasks.add(t);
        }
        Collections.sort(generatedTasks);
    }
    private int randomArrivalTime()
    {
        Random rand = new Random();
        return rand.nextInt(t_min_arrival, t_max_arrival);
    }
    private int randomServiceTime(){
        Random rand = new Random();
        return rand.nextInt(t_min_service, t_max_service);
    }
    @Override
    public void run(){

        try{
            FileHandler fileHandler = new FileHandler("log_test.txt");
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            LOGGER.addHandler(fileHandler);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        String rez = "";
        int currentTime = 0, nrTask = 0, emptyServers = 1;;
        maxTasks = 0;

        while(currentTime < t_max_simulation && (!generatedTasks.isEmpty() || scheduler.getTotalWaitingTime() >= 0) && emptyServers == 1){

            if(generatedTasks.isEmpty() && scheduler.getTotalWaitingTime() == 0)
                emptyServers = 0;

            for(int i = 0; i < generatedTasks.size(); i++)
            {
                if(generatedTasks.get(i).getArrivalTime() == currentTime)
                {
                    nrTask++;
                    Server s = scheduler.dispatchTask(generatedTasks.get(i));
                    averageServiceTime += generatedTasks.get(i).getServiceTime();
                    averageWaitingTime += s.getWaitingPeriod().get();
                    Task deleted = generatedTasks.remove(i);
                    i--;
                }
            }

            rez = rez + toString(currentTime);
            view.getResultArea().setText(toString(currentTime));
           // System.out.println(toString(currentTime));
            setPeakHour(currentTime);
            currentTime++;

            try {
                sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        Server.terminat = 1;
        String data = "";
        if(nrTask == 0)
        {
            data += "\nNiciun client preluat.";
            rez += "\nNiciun client preluat.";
        }
        else
        {
            data += "Average Service Time = " + averageServiceTime / nrTask +  "\nAverage Waiting Time = " +
                    (averageWaitingTime - 1) / nrTask + "\nPeak Hour = " + peakHour;
            rez += "\nPeak Hour = " + peakHour;
            rez += "\nAverage Service Time = " + averageServiceTime / nrTask;
            rez += "\nAverage Waiting Time = " + (averageWaitingTime - 1) / nrTask;
            if(numberOfClients != nrTask)
            {
                data += "\nAu ramas " + (numberOfClients - nrTask) + " clienti nepreluati.";
                rez += "\nAu ramas " + (numberOfClients - nrTask) + " clienti nepreluati.";
            }
            if(scheduler.getTaskTotal() != 0)
            {
                data += "\nNu s-au terminat de procesat ultimii clienti.";
                rez += "\nNu s-au terminat de procesat ultimii clienti.";
            }
        }

//        System.out.println("Peak Hour = " + peakHour);
//        System.out.println("Average Service Time = " + averageServiceTime / nrTask);
//        System.out.println("Average Waiting Time = " + (averageWaitingTime - 1) / nrTask);
        view.getResultArea().setText(data);
        LOGGER.info(rez);
    }

    private void setPeakHour(int currentTime) {
        if(scheduler.getTaskTotal() > maxTasks)
        {
            maxTasks = scheduler.getTaskTotal();
            peakHour = currentTime;
        }
    }

    private String toString(int currentTime){
        String r = "\nTime " + currentTime + "\n";
        r += "Waiting clients: ";
        int cnt = 0;
        for (Task t : generatedTasks)
        {
            r += t.toString() + ";";
            cnt++;
            if(cnt == 10)
            {
                r += "\n";
                cnt = 0;
            }
        }
        r += scheduler.toString();
        return r;
    }


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(Objects.equals(command, "COMPUTE")){
            String strategy;
            if(!Objects.equals(view.nTextField().getText(), "") && !Objects.equals(view.qTextField().getText(), "") &&
                    !Objects.equals(view.getMaxSimulationTextField().getText(), "") && !Objects.equals(view.getMinArrivalTextField().getText(), "") &&
                    !Objects.equals(view.getMaxArrivalTextField().getText(), "") && !Objects.equals(view.getMinServiceTextField().getText(), "") &&
                    !Objects.equals(view.getMaxServiceTextField().getText(), ""))
            {
                numberOfClients = Integer.parseInt(view.nTextField().getText());
                numberOfServers = Integer.parseInt(view.qTextField().getText());
                t_max_simulation = Integer.parseInt(view.getMaxSimulationTextField().getText());
                t_min_arrival = Integer.parseInt(view.getMinArrivalTextField().getText());
                t_max_arrival = Integer.parseInt(view.getMaxArrivalTextField().getText());
                t_min_service = Integer.parseInt(view.getMinServiceTextField().getText());
                t_max_service = Integer.parseInt(view.getMaxServiceTextField().getText());
                strategy = String.valueOf(view.getStrategyComboBox().getSelectedItem());


                if(numberOfClients >= 0 && numberOfServers >= 1 && t_max_simulation > 0 && t_min_arrival >= 0 && t_min_arrival < t_max_arrival &&
                        t_max_arrival > 0 && t_min_service > 0 && t_min_service < t_max_service && t_max_service > 0)
                {
                    scheduler = new Scheduler(numberOfServers, MAX_TASKS_PER_SERVER);

                    if(Objects.equals(strategy, "Shortest queue"))
                        selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
                    else
                        if(Objects.equals(strategy, "Shortest time"))
                            selectionPolicy = SelectionPolicy.SHORTEST_TIME;

                    scheduler.changeStrategy(selectionPolicy);

                    generateNRandomTasks();
                    averageServiceTime = 0;
                    averageWaitingTime = 0;

                    Thread t = new Thread(this);
                    t.start();
                }
                else
                {
                    view.getResultArea().setText(String.valueOf("Date incorecte !\n Motive: \n ->Numere negative.\n ->Limitele intervalului sunt incorecte."));
                }
            }
            else
            {
                view.getResultArea().setText(String.valueOf("Date incomplete !"));
            }
        }
    }
}

