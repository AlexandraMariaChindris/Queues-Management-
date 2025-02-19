package Model;

public class Task implements  Comparable<Task>{

    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int id, int a, int s){
        ID = id;
        arrivalTime = a;
        serviceTime = s;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public int compareTo(Task o) {
        return this.arrivalTime - o.arrivalTime;
    }

    public String toString(){
        return "(" + ID + "," + arrivalTime + "," + serviceTime + ")";
    }
}
