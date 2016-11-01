/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzasystem;
import DB.DBController;
import Gui.PController;
import javax.swing.*;

/**
 *
 * @author daniel
 */
public class PizzaSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DBController dbController = new DBController();
        
        //We will check if we can connect to the database in order to proceed to launch everything
        if(dbController.testConnect())
        {
            //Launch everything...
            
            //Create GUI controller
            PController gController = new PController(dbController);
        }
        else
        {
            System.out.println("Could not connect to database, contact the sysAdmin");
            System.exit(0);
        }
        
    }
    
    
    
}
