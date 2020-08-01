package com.btp;

import com.btp.gui.ServerGUI;

import javax.servlet.http.HttpServlet;
import javax.swing.*;

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

        testResources();

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
