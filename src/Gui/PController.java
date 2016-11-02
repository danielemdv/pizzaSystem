/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DB.DBController;
import Model.Client;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.*;

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
    private ClientManagerFrame ClientManager;
    private EditClientFrame EditClient;
    
    
    //Constructor method
    public PController(DBController dbController){
        this.dbController = dbController;
        
        //Initialization of arrayList
        frames = new ArrayList<>();
        
        //Initialization of all the JFrame Objects
        LogIn = new LogInFrame(this);
        MainMenu = new MainMenuFrame(this);
        RegisterClient = new RegisterClientFrame(this);
        ClientManager = new ClientManagerFrame(this);
        EditClient = new EditClientFrame(this);
        
        //Adding the JFrames to the ArrayList
        frames.add(LogIn);
        frames.add(MainMenu);
        frames.add(RegisterClient);
        frames.add(ClientManager);
        frames.add(EditClient);
        
        //Setting all JFrames invisible except for the Log In Frame
        centerAndDisappearFrames();

        //Appear LogInFrame
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
    
    //Method called from MainMenu when ClientManagerButton is pressed
    public void mainMenuClientManagerButton(){
        /*
        Set MainMenu invisible, populate ClientManager's table with all clients. Set ClientManager visible.
        */
        
        MainMenu.setVisible(false);
        clientManagerResetTable();
        ClientManager.setVisible(true);
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
    
    
    
    
    //--------------------------CLIENT MANAGER FRAME----------------------------------------
    
    
    //Method to populate ClientManager's JTable with all the clients in the DB
    public void clientManagerResetTable(){
        String[] columnNames = {"ID", "Nombre", "Direccion", "Telefono"}; //should make a final variable (constant) to call it from
        
        ClientManager.setTableModel(buildTableModelFromObjectArray(dbController.selectAllClientsEASY(), columnNames));
        
        //Table testing code
        //JOptionPane.showMessageDialog(null, new JScrollPane(buildTableFromObjectArray(dbController.selectAllClientsEASY(), columnNames)));
    }
    
    //Method called when ClientManager's backButton is pressed
    public void clientManagerBackButton(){
        //disappear ClientManager, clear its fields, appear MainMenu
        ClientManager.setVisible(false);
        ClientManager.clearFields();
        MainMenu.setVisible(true);
    }
    
    //Method called when ClientManager's editButton is pressed
    public void clientManagerEditButton(){
        //Get the table's selected row
        int row = ClientManager.clientTable.getSelectedRow();
        
        //If there is no selected row, inform the user.
        if(row == -1)
        {
            JOptionPane.showMessageDialog(RegisterClient, "Debe seleccionar un cliente primero.", "Cuidado!" , JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            //There is a row selected, we get the Client's ID from the model.
            int clientID = (Integer)(ClientManager.clientTable.getModel().getValueAt(row, 0));
            Client client = dbController.selectClientByID(clientID); //Query the database for the given client.
            fillEditClientFrame(client); //set EditClient's fields the same as the client's instance attributes.
            
            //Disappear ClientManager
            ClientManager.setVisible(false);
            //Appear EditClient
            EditClient.setVisible(true);
        }
    }
    
    
    
    //--------------------------END CLIENT MANAGER FRAME----------------------------------------
    
    
    
    
    
     //--------------------------EDIT CLIENT FRAME----------------------------------------
    
    public void fillEditClientFrame(Client client){
        EditClient.fillFields(client.getName(), client.getAddress(), client.getPhone());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     //--------------------------END EDIT CLIENT FRAME----------------------------------------
    
    
    
    
    
    
    
    
    
    
    //------------------------------HELPER METHODS----------------------------
    
    //Method to clear fields and set all frames invisible.
    private void centerAndDisappearFrames(){
        for(int i = 0; i < frames.size(); i++){
            frames.get(i).setLocationRelativeTo(null);
            frames.get(i).setVisible(false);
        }
    }
    
    
    /*Helper method that creates a DefaultTableModel given the result of a query in objectArrays.
      Also makes all the cells in the model non editable.
    */
    //DefaultTableModel(Object[][] rowData, Object[] columnNames)
    public DefaultTableModel buildTableModelFromObjectArray(Object[][] arr, Object[] columnNames){
        return new DefaultTableModel(arr, columnNames){
            
            @Override //Make the cells non editable.
            public boolean isCellEditable(int row, int column) {
                 return false;
            }
        };
    }
    
    
}
