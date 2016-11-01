package Model;
import java.util.ArrayList;

/**
 *
 * @author daniel
 * <p>
 * Class aimed to model a client and their data.
 */
public class Client {
    
    private int uid;
    private String name;
    private String address; //In near future this will probably change
    private String phone;
    
    //Might not need this, but makes sense right now
    private ArrayList<Order> orders; //EXTERMINATE!!
    
    //Constructor methods
    Client(int uid, String name, String address, String phone){
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        
    }
    
    //WILL DEPRECATE THIS CONSTRUCTOR DUE TO DB UTILIZATION.
    Client(int uid, String name, String address, String phone, ArrayList<Order> orders){
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.orders = orders;
    }

    //Methods
    
    /*
    Method to check if the information passed is valid for a Client object or not.
    */
    public static boolean isValidClient(String name, String address, String phone){
        boolean res = false;
        
        //trim the strings
        name = name.trim();
        address = address.trim();
        
        //remove all the spaces from phone
        phone = phone.replaceAll("\\s","");
        
        
        //If any of the fields are empty or phone is less than 8 digits long, it's not valid
        if(!(name.equals("") || address.equals("") || (phone.length() < 8)))
        {
            //Valid fields
            res = true;
        }
        return res;
    }
    
    
    
    
    //Returns an int to make sure order was placed properly. A proper order should return 0
    public int placeOrder(Order order){
        int res = 0;
        
        //Controller would place order... see if db accepts it...
        
       return res; 
    }
    
    
    
    public int cancelOrder(int orderUID){
        int res = 0;
        
        return res;
    }
    
    
    
    //-------Getters, Setters & toString
    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    /*
    The only setters for this class will be for address and phone, since these are attributes a client
    should be able to update.
    
    Note: We're not currently checking that the input is valid at this level.
    Note2: If we use a DB, this should update the DB with the new values immediately after accepting them.
    */
    
    public void setAddress(String address) {
        this.address =  address;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString(){
        return "Client:: uid: " + uid + " name: " + name + " address: " + address + " phone: " + phone;
    }
    
    
}
