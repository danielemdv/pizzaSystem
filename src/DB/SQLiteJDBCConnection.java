package DB;
//import java.sql.*; //Para cuando necesitamos todo

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author daniel
 */
public class SQLiteJDBCConnection {
    
    
    public static void connect() {
        Connection conn = null;
        
        try {
            // db parameters
            String url = "jdbc:sqlite:/home/daniel/NetBeansProjects/pizzaSystem/src/DBFiles/pizzaTestDB.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
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
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }
    
    
}
