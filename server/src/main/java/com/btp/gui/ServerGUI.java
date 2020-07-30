package com.btp.gui;

import com.btp.serverData.clientObjects.DishTime;
import com.btp.serverData.clientObjects.DishType;
import com.btp.serverData.clientObjects.User;
import com.btp.serverData.repos.RecipeRepo;
import com.btp.serverData.repos.UserRepo;
import com.btp.utils.Notifier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * This is a class that represents the server GUI
 */
public class ServerGUI extends JFrame{
    private JPanel mainPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel middlePanel;
    private JTextArea serverConsoleMonitor;
    private JTable chefRequestTable;
    private JButton acceptButton;
    private JButton refuseButton;
    private JTable userData;
    private JTextArea userSearchBoxTextArea;
    private JTable userRecipes;
    private JTextArea messageTextArea;
    private JButton sendButton;
    private JButton searchButton;
    private JButton refreshButton;
    private JRadioButton allUsersRadioButton;
    private JRadioButton thisUserRadioButton;
    private ButtonGroup radioButtonGroup = new ButtonGroup();
    private User tmpUser;
    private final DefaultTableModel chefRequestModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    private final DefaultTableModel userDataModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    private final DefaultTableModel userRecipesModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };


    /**
     * Constructor for the server GUI
     */
    public ServerGUI() {
        setTitle("CookTime Server Manager");
        setSize(1200,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        serverConsoleMonitor.setEditable(false);
        chefRequestTable.setModel(chefRequestModel);
        chefRequestModel.addColumn("Name");
        chefRequestModel.addColumn("Email");
        chefRequestModel.addColumn("Recipes");
        userData.setModel(userDataModel);
        userDataModel.addColumn("Name");
        userDataModel.addColumn("Age");
        userDataModel.addColumn("Recipes");
        userDataModel.addColumn("Chef Status");
        userDataModel.addColumn("Followers");
        userDataModel.addColumn("Following");
        userRecipes.setModel(userRecipesModel);
        userRecipesModel.addColumn("Name");
        userRecipesModel.addColumn("Number id");
        userRecipesModel.addColumn("Type");
        userRecipesModel.addColumn("Time");
        userRecipesModel.addColumn("Rating");
        userRecipesModel.addColumn("# comments");
        radioButtonGroup.add(allUsersRadioButton);
        radioButtonGroup.add(thisUserRadioButton);

        loadActiveChefRequests();
        searchButton.addActionListener(e -> searchUser());
        sendButton.addActionListener(e -> sendNotification());
        refreshButton.addActionListener(e -> loadActiveChefRequests());
        acceptButton.addActionListener(e -> acceptChef());
        refuseButton.addActionListener(e -> rejectChef());
    }

    /**
     * This method prints a line to the gui console
     * @param txt String to be printed
     */
    public void printLn(String txt) {
        serverConsoleMonitor.setEditable(true);
        serverConsoleMonitor.append(txt+"\n");
        serverConsoleMonitor.setEditable(false);
    }

    /**
     * this method searches for a user in the database, and retrieves the data
     */
    public void searchUser(){
        if(userSearchBoxTextArea.getText().isEmpty()){
            printLn("enter an user's email");
            tmpUser=null;
            userDataModel.setRowCount(0);
            userRecipesModel.setRowCount(0);
        }
        else{
            if(UserRepo.getUser(userSearchBoxTextArea.getText())==null) {
                printLn("user ;" + userSearchBoxTextArea.getText() + " not found.");
                userDataModel.setRowCount(0);
                userRecipesModel.setRowCount(0);
                tmpUser = null;
            }
            else{
                tmpUser = UserRepo.getUser(userSearchBoxTextArea.getText());
                populateFields(tmpUser);
            }
        }
    }

    /**
     * This method loads the user data on the GUI
     * @param user user to pull data from
     */
    private void populateFields(User user) {
        userSearchBoxTextArea.setText("");
        printLn("loading user data...");
        loadUserDetails(user);
        loadUserRecipes(user);
        printLn("user:"+user.getEmail()+" loaded");
        thisUserRadioButton.setSelected(true);

    }

    /**
     * This method loads any active chef requests in the server
     */
    private void loadActiveChefRequests(){
        chefRequestModel.setRowCount(0);
        ArrayList<String> chefRequests = UserRepo.getChefRequests();
        for (String chefRequest : chefRequests) {
            String name = UserRepo.getUser(chefRequest).fullName();
            String email = UserRepo.getUser(chefRequest).getEmail();
            int recipes = UserRepo.getUser(chefRequest).userCreatedRecipes().size();
            chefRequestModel.addRow(new Object[]{name, email, recipes});
        }
    }

    /**
     * This method loads the user details on the locations
     * @param user user to load data from
     */
    private void loadUserDetails(User user){
        userDataModel.setRowCount(0);
        String name = user.fullName();
        int age = user.getAge();
        int recipes = user.userCreatedRecipes().size();
        boolean chefStatus = user.isChef();
        int followers = user.getFollowerEmails().size();
        int following = user.getFollowingEmails().size();
        userDataModel.addRow(new Object[]{name, age, recipes,chefStatus,followers,following});
    }

    /**
     * This method loads the userRecipes on the appropriate fields in the GUI
     * @param user user to pull data from
     */
    private void loadUserRecipes(User user){
        userRecipesModel.setRowCount(0);
        ArrayList<Integer> userRecipes = user.getRecipeList();
        for (int userRecipe : userRecipes) {
            String name = RecipeRepo.getRecipe(userRecipe).getName();
            int id = RecipeRepo.getRecipe(userRecipe).getId();
            DishType type = RecipeRepo.getRecipe(userRecipe).getDishType();
            DishTime time = RecipeRepo.getRecipe(userRecipe).getDishTime();
            float rating = RecipeRepo.getRecipe(userRecipe).getScore();
            int comments = RecipeRepo.getRecipe(userRecipe).getComments().size();
            userRecipesModel.addRow(new Object[]{name, id, type,time,rating,comments});
        }
    }

    /**
     * This method sends a notification to the user
     */
    private void sendNotification(){
        if(allUsersRadioButton.isSelected()){
            Notifier.notifyAll(messageTextArea.getText());
            printLn("sent message to all users");
            printLn("message:\n"+messageTextArea.getText());
            printLn("updating dataBase");

        }
        else if(tmpUser!=null){
            printLn("send message to ;"+tmpUser.getEmail()+";");
            tmpUser.sendMessage(messageTextArea.getText());
            printLn("message:\n"+messageTextArea.getText());
            printLn("updating dataBase");
        }
    }

    /**
     * This method is in charge of accepting a a chef, and setting the selected user as a chef
     */
    private void acceptChef() {
        int row = chefRequestTable.getSelectedRow();
        String userID = (String) chefRequestModel.getValueAt(row,1);
        UserRepo.getUser(userID).setChef(true);
        UserRepo.removeChefRequest(userID);
        loadActiveChefRequests();
        printLn(UserRepo.getUser(userID).fullName()+"'s chef request accepted");
        Notifier.notify(userID,"Congratulations, we have approved your chef request");
    }

    /**
     * This method rejects the application
     */
    private void rejectChef() {
        int row = chefRequestTable.getSelectedRow();
        if(row==-1){
            printLn("please select a user");
        }
        else {
            String userID = (String) chefRequestModel.getValueAt(row, 1);
            UserRepo.getUser(userID).setChef(false);
            UserRepo.removeChefRequest(userID);
            loadActiveChefRequests();
            printLn(UserRepo.getUser(userID).fullName() + "'s chef request rejected");
            Notifier.notify(userID, "We have decided at this time not to approve your chef request, please wait 2 weeks, before sending another request");
        }
    }


}

