/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;
import javax.swing.*;
import DB.DBController;
import Model.Client;
import java.util.ArrayList;

/**
 *
 * @author daniel
 * <p>
 * Class aimed at controlling the flow of the GUI and the
 * GUI's information exchange with the model
 */
public class PController {
    
    //Declaration of DataBase Controller
    DBController dbController;
    
    //ArrayList to hold Frames to be able to batch call their methods.
    ArrayList<JFrame> frames;
    
    //Declaration of all JFrame objects in the GUI
    private LogInFrame LogIn;
    private MainMenuFrame MainMenu;
    private RegisterClientFrame RegisterClient;
    
    
    //Constructor method
    public PController(DBController dbController){
        this.dbController = dbController;
        
        //Initialization of arrayList
        frames = new ArrayList<>();
        
        //Initialization of all the JFrame Objects
        LogIn = new LogInFrame(this);
        MainMenu = new MainMenuFrame(this);
        RegisterClient = new RegisterClientFrame(this);
        
        //Adding the JFrames to the ArrayList
        frames.add(LogIn);
        frames.add(MainMenu);
        frames.add(RegisterClient);
        
        //Setting all JFrames invisible except for the Log In Frame
        centerAndDisappearFrames();

        
        LogIn.setVisible(true);
        
    }
    
    //--------------LOG IN FRAME---------------------------------------------------
    
    //Method called from LogInFrame
    public void logIn(String user, char[] password){
        //Get the user and password given and trim them.
        user = user.trim();
        String pass = String.valueOf(password);
        pass = pass.trim();
        
        //Verify if the user and password combination exists in the database.
        if(dbController.verifyUser(user, pass))
        {
            System.out.println("USER VALID!!");
            //Set login window invisible and clear the text fields
            LogIn.clearFields();
            LogIn.setVisible(false);
            
            //Show main menu frame.
            MainMenu.setVisible(true);
        }
        else
        {
            System.out.println("ERROR: USER LOG IN INCORRECT");
        } 
    }
    
    //Method called from MainMenu
    public void logOut(){
        //Set MainMenu invisible and appear LogIn Frame
        MainMenu.setVisible(false);
        LogIn.setVisible(true);
    }
    
    //-------------------END LOG IN FRAME----------------------------------------
    
    
    //--------------------------MAIN MENU FRAME------------------------------------
    
    //Method called from MainMenu when RegisterClientButton is pressed
    public void mainMenuRegisterClientButton(){
        /*
        set MainMenu invisible and set RegisterClient visible.
        */
        
        MainMenu.setVisible(false);
        RegisterClient.setVisible(true);
        
    }
    
    
    
    //--------------------------END  MAIN MENU FRAME--------------------------------------
    
    
    //--------------------------REGISTER CLIENT FRAME---------------------------------------
    
    //Method called from RegisterClientFrame when backButton is pressed.
    public void registerClientBackButton(){
        /*
        Clear the frame's fields, set in invisible and set the main menu visible.
        */
        RegisterClient.clearFields();
        RegisterClient.setVisible(false);
        
        MainMenu.setVisible(true);
    }
    
    //Method called from RegisterClientFrame when registerButton is pressed.
    public void registerClientRegisterButton(String name, String address, String phone){
        /*
        We need to tell the dbController to add the new client and make sure if any exception is thrown that
        the user is correctly notified.
        */
        
        //Check if the data is consistent with the model
        Object[] dat = Client.isValidClient(name, address, phone);
        name = (String)(dat[1]);
        address = (String)(dat[2]);
        phone = (String)(dat[3]);
        
        //If data is not valid, then inform the user.
        if(!((boolean)(dat[0])))
        {
            JOptionPane.showMessageDialog(RegisterClient, "Los campos no pueden estar vacíos y el teléfono debe tener mínimo 8 dígitos sin contar espacios.", "CUIDADO" , JOptionPane.INFORMATION_MESSAGE);
        }
        //Data is valid, try to addClient to the DB
        else if(dbController.addClient(name, address, phone))
        {
            System.out.println("Client registered succesfully");
            JOptionPane.showMessageDialog(RegisterClient, "Cliente dado de alta exitosamente", "EXITO" , JOptionPane.INFORMATION_MESSAGE);
            
            //Disappear Frame and go back to main menu
            RegisterClient.clearFields();
            RegisterClient.setVisible(false);
            MainMenu.setVisible(true);
        }
        //Client not succesfully added to the DB
        else
        {
            System.out.println("ERROR: Client registered unsuccesfully!!!");
            JOptionPane.showMessageDialog(RegisterClient, "El cliente no pudo ser dado de alta\nRecuerde que dos clientes no pueden tener el mismo teléfono", "ERROR" , JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //--------------------------END REGISTER CLIENT FRAME--------------------------------------
    
    
    //Method to clear fields and set all frames invisible.
    private void centerAndDisappearFrames(){
        for(int i = 0; i < frames.size(); i++){
            frames.get(i).setLocationRelativeTo(null);
            frames.get(i).setVisible(false);
        }
    }
    
    
}
