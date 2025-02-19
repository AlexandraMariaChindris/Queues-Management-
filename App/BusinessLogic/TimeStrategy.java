package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{
    @Override
    public Server addTask(List<Server> servers, Task t) {
        int min = 100000, index = -1;
        for(int i = 0; i < servers.size(); i++)
        {
            Server s = servers.get(i);
            if(s.getWaitingPeriod().get() < min){
                min = s.getWaitingPeriod().get();
                index = i;
            }
        }
        servers.get(index).addTask(t);
        return servers.get(index);
    }
}

