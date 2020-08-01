package com.btp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
}

