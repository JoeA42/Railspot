package com.btp.utils;

import com.btp.serverData.repos.UserRepo;
import java.util.ArrayList;

/**
 * Notifier class, it has three methods to notify a single user, multiple users, and all users in the dataBase
 */
public class Notifier {

    /**
     * This method takes an user id, and a notification and sends the notification to the user
     * @param id string id of the user
     * @param notification string message of the notification
     */
    public static void notify(  String id, String notification){
        UserRepo.getUser(id).sendMessage(notification);
        UserRepo.updateTree();
    }

    /**
     * This method takes a String Array
     * @param idList ArrayList of String id's
     * @param notification String message to be sent to the users
     */
    public static void notifyMult(ArrayList<String> idList, String notification){
        for (String s : idList) {
            UserRepo.getUser(s.split(";")[0]).sendMessage(notification);
            UserRepo.updateTree();
        }
    }

    /**
     * This method notifies all users on the dataBase
     * @param notification String message to be sent to the users
     */
    public static void notifyAll(String notification){
        UserRepo.notifyAll(notification);
        UserRepo.updateTree();
    }
}
