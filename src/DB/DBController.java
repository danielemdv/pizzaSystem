/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;
import java.sql.*;

/**
 *
 * @author daniel
 * <p>
 * Class to control the interaction with our sqlite database.
 */
public class DBController {
    
    //String pointing to the DB file
    public String url;
    
    //Constructor
    public DBController(){
        //Note, this string is different if we run on windows.
        url = "jdbc:sqlite:/home/daniel/NetBeansProjects/pizzaSystem/src/DBFiles/pizzaTestDB.db";
    }
    
    //overloaded constructor to pass a custom path to the database.
    public DBController(String url){
        this.url = url;
    }
    
    //Method to check if we have connection to the DataBase.
    public boolean testConnect() {
        boolean res = false;
        Connection conn = null;
        
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
            res = true; //We indicate that the connection was succesful
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return res;
    }
    
    //Method to establish and return a Connection object.
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    
    //Method to verify that a user is in the system. return true if the user and password combination exists.
    public boolean verifyUser(String name, String password){
        boolean res = false;
        
        String sql = "SELECT * FROM user where name = '" + name + "' and password = '" + password +"';";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                res = true; //We get thrown a value, therefore the user exists.
                
                System.out.println("Verification of: " + rs.getInt("id") +  "\t" + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
    
    
    public boolean addClient(String name, String address, String phone){
        boolean res = true;

        String sql = "INSERT INTO CLIENT (NAME, ADDRESS, PHONE) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res = false;
        }
        return res;
    }
    
    
    
    
}
