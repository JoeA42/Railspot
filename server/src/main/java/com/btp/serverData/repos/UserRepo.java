package com.btp.serverData.repos;

import com.btp.Initializer;
import com.btp.dataStructures.trees.UserBST;
import com.btp.utils.DataWriter;
import com.btp.serverData.clientObjects.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the repository where we modify the users on the platform, and saves and loads from the database
 */
public class UserRepo {
    private static UserBST userTree = new UserBST();
    private static ArrayList<String> chefRequests = new ArrayList<>();
    private static final DataWriter<UserBST> dataWriter = new DataWriter<>();
    private static final DataWriter<ArrayList<String>> dataWriter2 = new DataWriter<>();
    private static final String pathUserDataBase =System.getProperty("project.folder")+"/dataBase/userDataBase.json";
    private static final String pathChefRequestDataBase =System.getProperty("project.folder")+"/dataBase/chefRequestsDataBase.json";

    /**
     * This method is used to add a user to the dataBas & user tree
     * @param user User obj,
     */
    public static void addUser(User user) {
        userTree.insert(user);
        System.out.println("user added");
        if(Initializer.isGUIOnline()){
            Initializer.getServerGUI().printLn("user added");
        }
        dataWriter.writeData(userTree, pathUserDataBase);
    }

    /**
     * This method deletes the reference for a recipe that has been deleted from all users
     * @param id int ID of the recipe
     */
    public static void deleteRecipe(int id){
        userTree.deleteRecipe(id);
    }

    /**
     * This method saves changes that are active and saves it in the dataBase (json)
     */
    public static void updateTree(){
        dataWriter.writeData(userTree, pathUserDataBase);
    }

    /**
     * This method is used to retrieve a user from the user tree
     * @param email string email for the user that wants to be retrieved
     * @return user obj
     */
    public static User getUser(String email){
        return userTree.getElementByEmail(email);
    }

    /**
     * This method is used to determine if an email already exists in the userRepo
     * @param email string email for the user that wants to be verified
     * @return boolean value, true if the user exists, false if it doesnt
     */
    public static boolean checkByID(String email) {
        return userTree.checkByEmail(email);
    }

    /**
     * This method is used to search users that mat the parameter
     * @param data the data that will be used to search
     * @return the results of the search
     */
    public static ArrayList<String> searchUsers(String data){
        return userTree.searchPreOrder(data);
    }

    /**
     * This method loads the tree from the json file that was stored
     * @throws IOException exception
     */
    public static void loadTree() throws IOException {
        System.out.println("loading user data base...");
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader file = new FileReader(pathUserDataBase);
        userTree = objectMapper.readValue(file, userTree.getClass());
        System.out.println("loading chef requests...");
        FileReader file2 = new FileReader(pathChefRequestDataBase);
        chefRequests = objectMapper.readValue(file2, chefRequests.getClass());
    }

    /**
     * this method is used to message all users in the platform
     * @param notification String message to send to all users
     */
    public static void notifyAll(String notification) {
        userTree.messageAll(notification);
    }

    /**
     * This method is used to add a chef request, to the pending chef request json
     * @param id String email, the id of the person making the request
     */
    public static void addChefRequest(String id) {
        chefRequests.add(id);
        dataWriter2.writeData(chefRequests, pathChefRequestDataBase);
    }

    /**
     * this method is used to retrieve recommendations to the user
      * @param data String data to be used in the recommendations
     * @return recommendations
     */
    public static ArrayList<String> recommend(String data){
        return userTree.recommend(data);
    }

    /**
     * This method is used to remove a chef request from the active chef requests data base
     * @param id string id of the request that is going to be removed
     */
    public static void removeChefRequest(String id){
        chefRequests.remove(id);
        dataWriter2.writeData(chefRequests, pathChefRequestDataBase);
    }

    /**
     * this method is used to check if there is an existing chef request
     * @param id id of the user making the request
     * @return boolean value, depending of whether or not the email is an active request
     */
    public static boolean isActiveRequest(String id) {
        return chefRequests.contains(id);
    }

    /**
     * This method is used to return all active chef requests
     * @return ArrayList of strings, corresponding to the email of all active requests
     */
    public static ArrayList<String> getChefRequests() {
        return chefRequests;
    }
}
