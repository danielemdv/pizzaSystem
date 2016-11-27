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
    private DBController dbController;
    
    //ArrayList to hold Frames to be able to batch call their methods.
    private ArrayList<JFrame> frames;
    
    //Declaration of int to store value of the ID of the current client that is being edited.
    private int currentClientIDToEdit;
    
    //Declaration of int to store the ID of the current client we are creating an order for.
    private int currentClientOrderID;
    
    //Declaration of all JFrame objects in the GUI
    private LogInFrame LogIn;
    private MainMenuFrame MainMenu;
    private RegisterClientFrame RegisterClient;
    private ClientManagerFrame ClientManager;
    private EditClientFrame EditClient;
    private SearchClientPhoneFrame SearchClientPhone;
    
    
    //Constructor method
    public PController(DBController dbController){
        this.dbController = dbController;
        
        //Initialization of control variable
        currentClientOrderID = -1;
        
        //Initialization of arrayList
        frames = new ArrayList<>();
        
        //Initialization of all the JFrame Objects
        LogIn = new LogInFrame(this);
        MainMenu = new MainMenuFrame(this);
        RegisterClient = new RegisterClientFrame(this);
        ClientManager = new ClientManagerFrame(this);
        EditClient = new EditClientFrame(this);
        SearchClientPhone = new SearchClientPhoneFrame(this);
        
        //Adding the JFrames to the ArrayList
        frames.add(LogIn);
        frames.add(MainMenu);
        frames.add(RegisterClient);
        frames.add(ClientManager);
        frames.add(EditClient);
        frames.add(SearchClientPhone);
        
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
    
    public void mainMenuNewOrderButton(){
        MainMenu.setVisible(false);
        SearchClientPhone.resetFields();
        SearchClientPhone.setVisible(true);
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
            this.currentClientIDToEdit = client.getUid(); //Store the reference to the Client we want to edit in this global variable.
            
            //Disappear ClientManager
            ClientManager.setVisible(false);
            //Appear EditClient
            EditClient.setVisible(true);
        }
    }
    
    //Called when the ClientManager's deleteButton is pressed.
    public void clientManagerDeleteButton(){
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
            
            int optionRes = JOptionPane.showConfirmDialog(ClientManager, "Está seguro que desea eliminar al cliente:\n<html><b>" + client.getName() + "</b></html>", "Eliminar", JOptionPane.YES_NO_OPTION);
            
            //Check the option
            if(optionRes == JOptionPane.YES_OPTION) //delete client.
            {
                boolean opFlag = dbController.deleteClientByID(clientID);
                
                if(opFlag)
                {
                    //Reset the table
                    clientManagerResetAndClear();
                    JOptionPane.showMessageDialog(ClientManager, "El cliente ha sido eliminado exitosamente", "Éxito!" , JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(ClientManager, "El cliente no pudo ser eliminado, hubo un error.", "Error!" , JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
            
        }
    }
    
    
    
    
    //Method to reset the table and clear fields (should be called when appearing the frame)
    public void clientManagerResetAndClear(){
        clientManagerResetTable();
        ClientManager.clearFields();
    }
    
    
    
    //--------------------------END CLIENT MANAGER FRAME----------------------------------------
    
    
    
    
    
     //--------------------------EDIT CLIENT FRAME----------------------------------------
    
    //Method to fill the EditClient frame's fields with the info of the selected client to edit.
    public void fillEditClientFrame(Client client){
        EditClient.fillFields(client.getName(), client.getAddress(), client.getPhone());
    }
    
    
    //Method called when EditClient's backButton is pressed.
    public void editClientBackButton(){
        /*
            Dissapear EditClient and clear its fields, refresh ClientManager's table and clear its fields
        */
        EditClient.setVisible(false);
        EditClient.clearFields();
        
        clientManagerResetAndClear();
        ClientManager.setVisible(true);
    }
    
    
    public void editClientEditButton(String name, String address, String phone){
        /*
        Check that the information is valid for a client. Then execute the update query.
        */
        
        //Validate the information with the model.
        Object[] resClient = Client.isValidClient(name,address,phone);
        
        if(!(boolean)(resClient[0])) //if client is not valid, inform the user.
        {
             JOptionPane.showMessageDialog(EditClient, "Los campos no pueden estar vacíos y el teléfono debe tener mínimo 8 dígitos sin contar espacios.", "CUIDADO" , JOptionPane.INFORMATION_MESSAGE);
        }
        else //The information is valid and we can proceed to update the user.
        {
            //Run the database update
            boolean updateFlag = dbController.updateClient(new Client(currentClientIDToEdit, (String)(resClient[1]), (String)(resClient[2]), (String)(resClient[3])));
            
            if(updateFlag) //Client was updated succesfully.
            {
                JOptionPane.showMessageDialog(EditClient, "El cliente fue modificado exitosamente.", "EXITO" , JOptionPane.INFORMATION_MESSAGE);
                
                //Take user back to ClientManager
                EditClient.clearFields();
                EditClient.setVisible(false);
                
                clientManagerResetAndClear();
                ClientManager.setVisible(true);
            }
            else //some error. Client was not updated.
            {
                JOptionPane.showMessageDialog(EditClient, "El cliente no fue modificado.", "ERROR" , JOptionPane.ERROR_MESSAGE);
            }  
        }
        
    }
    
    
    
     //--------------------------END EDIT CLIENT FRAME----------------------------------------
    
    
    //----------------------------SEARCH CLIENT PHONE FRAME--------------------------------------
    
    public void searchClientPhoneCancelButton(){
        SearchClientPhone.setVisible(false);
        SearchClientPhone.resetFields();
        MainMenu.setVisible(true);
    }
    
    public void searchClientPhoneSearchButton(){
        Client resClient = dbController.selectClientByPhone(SearchClientPhone.getPhone());
        
        //If we do have a result.
        if(resClient != null)
        {
            SearchClientPhone.setClientLabels(resClient.getName(), resClient.getAddress(), resClient.getPhone());
            currentClientOrderID = resClient.getUid();
        }
        else //If no client matches
        {
            JOptionPane.showMessageDialog(SearchClientPhone, "No hay cliente con ese teléfono registrado", "CUIDADO" , JOptionPane.INFORMATION_MESSAGE);
            SearchClientPhone.resetFields();
            currentClientOrderID = -1; //Curren client for order is invalid.
        }
        
        
    }
    
    public void searchClientPhoneNextButon(){
        //If there is no valid client selected for the new order
        if(currentClientOrderID == -1)
        {
            JOptionPane.showMessageDialog(SearchClientPhone, "No hay cliente válido seleccionado", "CUIDADO" , JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            //Appear BuildOrderFrame
            
        }
    }
    
    
    
    
    
    
    
    //----------------------------END SEARCH CLIENT PHONE FRAME--------------------------------------
    
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
