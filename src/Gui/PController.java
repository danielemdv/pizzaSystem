/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;
import javax.swing.*;
import DB.DBController;
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
    
    //Method called from MainMenu when RegisterClientButton is pressed
    public void mainMenuRegisterClientButton(){
        /*
        set MainMenu invisible and set RegisterClient visible.
        */
        
        MainMenu.setVisible(false);
        RegisterClient.setVisible(true);
        
    }
    
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
        
        //Trim the strings
        name = name.trim();
        address = address.trim();
        phone = phone.trim();
        
        //Check that none of the strings are empty strings and that the phone length is at least 8 digits (including spaces)
        if(name.equals("") || address.equals("") || (phone.length() < 8))
        {
            JOptionPane.showMessageDialog(RegisterClient, "Los campos no pueden estar vacíos y el teléfono debe tener mínimo 8 dígitos", "CUIDADO" , JOptionPane.INFORMATION_MESSAGE);
        }
        else if(dbController.addClient(name, address, phone))
        {
            System.out.println("Client registered succesfully");
            JOptionPane.showMessageDialog(RegisterClient, "Cliente dado de alta exitosamente", "EXITO" , JOptionPane.INFORMATION_MESSAGE);
            RegisterClient.clearFields();
            RegisterClient.setVisible(false);
            MainMenu.setVisible(true);
        }
        else
        {
            System.out.println("ERROR: Client registered unsuccesfully!!!");
            JOptionPane.showMessageDialog(RegisterClient, "El cliente no pudo ser dado de alta", "ERROR" , JOptionPane.ERROR_MESSAGE);
            
        }
        
    }
    
    
    //Method to clear fields and set all frames invisible.
    private void centerAndDisappearFrames(){
        for(int i = 0; i < frames.size(); i++){
            frames.get(i).setLocationRelativeTo(null);
            frames.get(i).setVisible(false);
        }
    }
    
    
}
