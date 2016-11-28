package Model;
import java.util.ArrayList;

/**
 *
 * @author daniel
 * <p>
 * Class aimed to model an Order and the data related to it.
 */
public class Order {
    
    private int id;
    private int clientId;
    private double time;
    private int completed;
    private double cost;
    private ArrayList<Integer> pizzas;
    
    public Order(int clientId){
        this.clientId = clientId;
        this.completed = 0;
        
        pizzas = new ArrayList<>();
    }
    
    
    public Order(int id, int clientId, double time, int completed){
        this.id = id;
        this.clientId = clientId;
        this.time = time;
        this.completed = completed;
        
        pizzas = new ArrayList<>();
    }
    
    public Order(int id, int clientId, double time, int completed, double cost){
        this.id = id;
        this.clientId = clientId;
        this.time = time;
        this.completed = completed;
        this.cost = cost;
        
        pizzas = new ArrayList<>();
    }
    
    public Order(int id, int clientId, double time, int completed, double cost, ArrayList<Integer> pizzas){
        this.id = id;
        this.clientId = clientId;
        this.time = time;
        this.completed = completed;
        this.cost = cost;
        this.pizzas = pizzas;
    }
    
    public void completeOrder(double time, double cost){
        this.time = time;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public double getTime() {
        return time;
    }

    public int getCompleted() {
        return completed;
    }

    public double getCost() {
        return cost;
    }
    
    public ArrayList<Integer> getPizzas(){
        return pizzas;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public void addPizza(int pid){
        pizzas.add(pid);
    }
    
    
}
