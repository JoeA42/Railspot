package com.btp;

import com.btp.gui.ServerGUI;
import com.btp.serverData.clientObjects.Business;
import com.btp.serverData.repos.BusinessRepo;
import com.btp.serverData.repos.RecipeRepo;
import com.btp.serverData.repos.UserRepo;
import javax.servlet.http.HttpServlet;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates a servlet that runs automatically when the server start, using the HttpServlet class
 */
public class Initializer extends HttpServlet {

    public static ServerGUI serverGUI;
    public static boolean guiStatus = false;

    /**
     * getter for the serverGUI
     * @return boolean value depending on the status of the serverGUI
     */
    public static boolean isGUIOnline() {
        return guiStatus;
    }

    /**
     * Getter for the serverGUI
     * @return the serverGUI object
     */
    public static ServerGUI getServerGUI() {
        return serverGUI;
    }

    /**
     * This method runs when this servlet beings, it loads the different databases
     */
    public void init() {
        System.out.println("running initialization...");
        System.out.println("loading resources...");
        try {
            UserRepo.loadTree();
            RecipeRepo.loadTree();
            BusinessRepo.loadTree();
            //testResources();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("opening GUI...");
        try {
            createWindow();

        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is used to load resources easily
     */
    public static void testResources() {
        Business b1 = new Business();
        ArrayList<String> b1Employees = new ArrayList<>();
        b1Employees.add("m@gmail.com");
        b1.setEmployeeList(b1Employees);
        b1.setName("Las tortas del magias");
        b1.setBusinessHours("M-F;5:11:00am-11:00pm");
        b1.setContact("m@gmail.com");
        b1.setLocation("Moravia");
        b1.setId(500);
        BusinessRepo.addBusiness(b1);

    }

    /**
     * This method loads the server´s GUI
     * @throws ClassNotFoundException in case it doesnt find a class
     * @throws UnsupportedLookAndFeelException in case the look and feel is not supported
     * @throws InstantiationException in case there is an error during instantiation
     * @throws IllegalAccessException in case there is an undesired access
     */
    private void createWindow() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        serverGUI = new ServerGUI();
        serverGUI.setVisible(true);
        guiStatus = true;
    }

}
