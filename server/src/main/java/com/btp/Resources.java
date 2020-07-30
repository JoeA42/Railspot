package com.btp;

import com.btp.dataStructures.lists.SinglyList;
import com.btp.dataStructures.nodes.SinglyNode;
import com.btp.dataStructures.sorters.Sorter;
import com.btp.serverData.clientObjects.Business;
import com.btp.serverData.clientObjects.DishTag;
import com.btp.serverData.clientObjects.Recipe;
import com.btp.serverData.clientObjects.User;
import com.btp.serverData.repos.BusinessRepo;
import com.btp.serverData.repos.RecipeRepo;
import com.btp.serverData.repos.UserRepo;
import com.btp.utils.Notifier;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Random;

import static com.btp.utils.security.HashPassword.hashPassword;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("resources")
public class Resources {

    private static final Random random = new Random();


    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getResources() {
        return "Resources Main page, \n\nIf you want to check user's JSONs do the following:" +
                "\n\non your browser address bar add the following to the url: /getUser?id= followed by the int value of the id" +
                "\n\nif you want to check the recipe's JSONs do the following:\n\n" +
                "on your browser address bar add the following to the url: /getRecipe?id= followed by the int value of the id"+
                "\n\nto se the cheeseCake, put /getPicture?id=cheesecake-picture0";
    }

    /**
     * Sends a user with a specific email
     * @param email String value of the email of the user
     * @return User
     */
    @Path("getUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@QueryParam("id") String email) {
        return UserRepo.getUser(email);
    }

    /**
     * Sends a recipe with a specific id
     * @param id int value of the id of the recipe
     * @return Recipe
     */
    @Path("getRecipe")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@QueryParam("id") int id) {
        return RecipeRepo.getRecipe(id);
    }


    /**
     * Creates an user
     * @param user user to be created
     * @param uniqueID boolean to check if the id is unique
     * @throws NoSuchAlgorithmException
     */
    @POST
    @Path("createUser")
    public void createUser(User user, @QueryParam("uniqueID") boolean uniqueID) throws NoSuchAlgorithmException {
        System.out.println("new user!");
        System.out.println("name: " + user.fullName() + " email: " + user.getEmail() + " password: " + user.getPassword() + " age: " + user.getAge());
        if (Initializer.isGUIOnline()) {
            Initializer.getServerGUI().printLn("new user!");
            Initializer.getServerGUI().printLn("name: " + user.fullName() + "\nemail: " + user.getEmail() + "\npassword: " + user.getPassword() + "\nage: " + user.getAge());
        }
        user.setPassword(hashPassword(user.getPassword()));
        user.sendMessage("Welcome to CookTime!");
        UserRepo.addUser(user);
    }

    /**
     * Creates a recipe
     * @param recipe Recipe to be added
     */
    @POST
    @Path("createRecipe")
    public void createRecipe(Recipe recipe){
        System.out.println("Starting");
        int i = random.nextInt(999) + 1;
        while (RecipeRepo.checkId(i)){
            i = random.nextInt(999) +1;
        }
        recipe.setId(i);
        recipe.setPostTime(System.currentTimeMillis());
        RecipeRepo.addRecipe(recipe);
        User user = UserRepo.getUser(recipe.getAuthorEmail());
        recipe.setAuthorName(user.fullName());
        user.addRecipe(i);
        user.addNewsFeed(i);
        SinglyList<Recipe> recipeList = new SinglyList<>();
        for (int j:user.getRecipeList()){
            Recipe recipeTmp = RecipeRepo.getRecipe(j);
            recipeList.add(recipeTmp);
        }

        Sorter.insertionSort(recipeList);

        recipeList.print();
        SinglyNode tmp = recipeList.getHead();
        user.getRecipeList().clear();
        while (tmp!=null){
            Recipe recipeTmp = (Recipe) tmp.getData();
            user.addRecipe(recipeTmp.getId());
            tmp =(SinglyNode) tmp.getNext();
        }

        for(String data:user.getFollowerEmails()){
            String[] email = data.split(";");
            User follower = UserRepo.getUser(email[0]);
            follower.addNewsFeed(i);
        }

        Notifier.notifyMult(user.getFollowerEmails(),user.fullName()+" has uploaded a new recipe!");
        if (Initializer.isGUIOnline()) {
            Initializer.getServerGUI().printLn("new recipe id: "+recipe.getId());
        }
        UserRepo.updateTree();
        addRecipePicture(i, recipe.getPhoto());
        RecipeRepo.updateTree();
    }

    /**
     * Creates a recipe for a business
     * @param recipe Recipe to be added
     */
    @POST
    @Path("createRecipeB")
    public void createRecipeB(Recipe recipe,@QueryParam("isPrivate") String type){
        System.out.println("Starting");
        int i = random.nextInt(999) + 1;
        while (RecipeRepo.checkId(i)){
            i = random.nextInt(999) +1;
        }
        recipe.setId(i);
        recipe.setPostTime(System.currentTimeMillis());
        RecipeRepo.addRecipe(recipe);
        Business business = BusinessRepo.getBusiness(recipe.getBusinessId());
        business.addRecipe(i,type);

        recipe.setAuthorName(business.getName());

        if (Initializer.isGUIOnline()) {
            Initializer.getServerGUI().printLn("new recipe id: "+recipe.getId());
        }
        BusinessRepo.updateTree();
        addRecipePicture(i, recipe.getPhoto());
        RecipeRepo.updateTree();
    }

    /**
     * Moves a recipe to the other business list
     * @param id int id of the recipe to be moved
     * @param businessId int id of the business that owns the recipe
     * @return String "1" if moved from private to public, "0" from public to private
     */
    @GET
    @Path("moveRecipe")
    public String moveRecipe(@QueryParam("recipeId") int id, @QueryParam("businessId") int businessId){
        Business business = BusinessRepo.getBusiness(businessId);
        String response = business.moveRecipe(id);
        BusinessRepo.updateTree();
        return response;
    }

    /**
     * Shows the publicList of the business
     * @param id int the id of the specific business
     * @param filter String the filter used to sort the recipe
     * @return ArrayList<String> with the recipe information
     */
    @GET
    @Path("businessPublic")
    public ArrayList<String> showPublic(@QueryParam("id") int id, @QueryParam("filter") String filter){
        ArrayList<String> myMenuList = new ArrayList<>();
        Business business = BusinessRepo.getBusiness(id);
        SinglyList<Recipe> sortList = new SinglyList<>();

        for (int i:business.getPublicList()) {
            Recipe recipe = RecipeRepo.getRecipe(i);
            sortList.add(recipe);
        }

        if (sortList.getLength() == 0){
            return myMenuList;
        } else if (sortList.getLength() == 1){
            Recipe recipe = RecipeRepo.getRecipe(business.getPublicList().get(0));
            myMenuList.add(recipe.getId()+";"+recipe.getName()+
                    ";"+recipe.getAuthorName()+";"+recipe.getAuthorEmail());
            return myMenuList;
        }

        switch (filter){
            case "date":
                Sorter.bubbleSort(sortList);
                break;
            case "score":
                Sorter.quickSort(sortList);
                break;
            case "difficulty":
                Sorter.radixSort(sortList);
                break;
        }
        SinglyNode tmp = sortList.getHead();
        while (tmp!=null){
            Recipe recipe = (Recipe) tmp.getData();
            myMenuList.add(recipe.getId()+";"+recipe.getName()+
                    ";"+UserRepo.getUser(recipe.getAuthorEmail()).fullName()+";"+recipe.getAuthorEmail());
            tmp =(SinglyNode) tmp.getNext();
        }
        return myMenuList;
    }

    /**
     * Shows the privateList of the business
     * @param id int the id of the specific business
     * @param filter String the filter used to sort the recipe
     * @return ArrayList<String> with the recipe information
     */
    @GET
    @Path("businessPrivate")
    public ArrayList<String> showPrivate(@QueryParam("id") int id, @QueryParam("filter") String filter){
        ArrayList<String> myMenuList = new ArrayList<>();
        Business business = BusinessRepo.getBusiness(id);
        SinglyList<Recipe> sortList = new SinglyList<>();

        for (int i:business.getPrivateList()) {
            Recipe recipe = RecipeRepo.getRecipe(i);
            sortList.add(recipe);
        }

        if (sortList.getLength() == 0){
            return myMenuList;
        } else if (sortList.getLength() == 1){
            Recipe recipe = RecipeRepo.getRecipe(business.getPrivateList().get(0));
            myMenuList.add(recipe.getId()+";"+recipe.getName()+
                    ";"+recipe.getAuthorName()+";"+recipe.getAuthorEmail());
            return myMenuList;
        }

        switch (filter){
            case "date":
                Sorter.bubbleSort(sortList);
                break;
            case "score":
                Sorter.quickSort(sortList);
                break;
            case "difficulty":
                Sorter.radixSort(sortList);
                break;
        }
        SinglyNode tmp = sortList.getHead();
        while (tmp!=null){
            Recipe recipe = (Recipe) tmp.getData();
            myMenuList.add(recipe.getId()+";"+recipe.getName()+
                    ";"+recipe.getAuthorName()+";"+recipe.getAuthorEmail());
            tmp =(SinglyNode) tmp.getNext();
        }
        return myMenuList;
    }

    /**
     * Rates a business
     * @param id int the id of the business to be rated
     * @param rating int the rating of the user
     * @param email String the email of the user rating the business 
     * @return String "0" if previously rated, "1" if not
     */
    @GET
    @Path("rateBusiness")
    public String rateBusiness(@QueryParam("id") int id, @QueryParam("rating") int rating,
                               @QueryParam("email") String email){
        String response;
        Business business = BusinessRepo.getBusiness(id);
        if (business.getRaters().contains(email)){
            response = "1";
        } else {
            business.addRating(rating);
            business.addRater(email);
            response = "0";
        }
        BusinessRepo.updateTree();
        return response;
    }

    /**
     * Shares a recipe to its myMenu
     * @param id int of the recipe
     * @param email String of the user sharing the recipe
     * @return String "1" if the recipe was shared, "0" if not
     */
    @GET
    @Path("shareRecipe")
    @Produces(MediaType.APPLICATION_JSON)
    public String shareRecipe(@QueryParam("id") int id, @QueryParam("email") String email){
        User user = UserRepo.getUser(email);
        SinglyList<Recipe> recipeList = new SinglyList<>();

        if (!RecipeRepo.checkId(id)){
            return "0";
        }

        for (int data:user.getRecipeList()) {
            if (data == id){
                return "0";
            }
        }

        user.addRecipe(id);

        for (int j:user.getRecipeList()){
            Recipe recipeTmp = RecipeRepo.getRecipe(j);
            recipeList.add(recipeTmp);
        }

        SinglyNode tmp = recipeList.getHead();
        user.getRecipeList().clear();
        while (tmp!=null){
            Recipe recipeTmp = (Recipe) tmp.getData();
            user.addRecipe(recipeTmp.getId());
            tmp =(SinglyNode) tmp.getNext();
        }

        for(String data:user.getFollowerEmails()){
            String[] temp = data.split(";");
            User follower = UserRepo.getUser(temp[0]);
            if (!follower.getNewsFeed().contains(id)){
                follower.addNewsFeed(id);
            }
        }

        UserRepo.updateTree();

        return "1";
    }

    /**
     * Comments a recipe
     * @param id int id of the recipe
     * @param comment String comment
     * @param email String email of the user commenting
     * @return String "1" to notify the client the action was successful
     */
    @GET
    @Path("commentRecipe")
    @Produces(MediaType.APPLICATION_JSON)
    public String commentRecipe(@QueryParam("id") int id, @QueryParam("comment") String comment, @QueryParam("email") String email){
        Recipe recipe = RecipeRepo.getRecipe(id);
        recipe.addComment(comment+";"+UserRepo.getUser(email).fullName());

        User user = UserRepo.getUser(recipe.getAuthorEmail());

        Notifier.notify(recipe.getAuthorEmail(), "Your recipe: " + recipe.getName() + " has a new comment!");
        RecipeRepo.updateTree();

        return "1";
    }

    /**
     * Deletes a recipe from the database or myMenu depending of user ownership
     * @param email String email of the user asking for deletion
     * @param id int id of the recipe
     * @param fromMyMenu String "1" if the request comes from an user profile, "0" if  it comes from a business profile
     * @return String "1" if deletion was successful, "0" if the recipe doesn't exist
     */
    @GET
    @Path("deleteRecipe")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteRecipe(@QueryParam("email") String email, @QueryParam("id") int id,
                               @QueryParam("fromMyMenu") String fromMyMenu){
        boolean response;
        Recipe recipe = RecipeRepo.getRecipe(id);
        User user = UserRepo.getUser(email);

        if (recipe == null){
            return "0";
        }

        if (recipe.getBusinessId() != 0 && fromMyMenu.equals("1")){
            user.getRecipeList().remove(Integer.valueOf(id));
            BusinessRepo.updateTree();
            RecipeRepo.updateTree();
            UserRepo.updateTree();
            return "1";
        } else if (recipe.getBusinessId() != 0 && fromMyMenu.equals("0")){
            BusinessRepo.getBusiness(recipe.getBusinessId()).removeRecipe(id);
            RecipeRepo.deleteRecipe(id);
            UserRepo.deleteRecipe(id);
            BusinessRepo.updateTree();
            RecipeRepo.updateTree();
            UserRepo.updateTree();
            return "1";
        } else {
            if (!recipe.getAuthorEmail().equals(email)) {
                user.getRecipeList().remove(Integer.valueOf(id));
                BusinessRepo.updateTree();
                RecipeRepo.updateTree();
                UserRepo.updateTree();
                return "1";
            } else {
                UserRepo.deleteRecipe(id);
                RecipeRepo.deleteRecipe(id);
                BusinessRepo.updateTree();
                RecipeRepo.updateTree();
                UserRepo.updateTree();
                return "1";
            }
        }
    }


    /**
     * Checks if an email is in the database
     * @param email String email to be checked
     * @return String "1" if its on the database, "0" if not
     */
    @GET
    @Path("isEmailNew")
    @Produces(MediaType.APPLICATION_JSON)
    public String isEmailNew(@QueryParam("email") String email) {
        String value;
        if (UserRepo.checkByID(email)) {
            value = "1";
        } else {
            value = "0";
        }
        return value;
    }

    /**
     * Deletes al notifications of an user
     * @param id String email of the user
     * @return String "1" if it was successful, "0" if not
     */
    @GET
    @Path("deleteAllNotifications")
    public String deleteAllNotifications(@QueryParam("id")String id){
        UserRepo.getUser(id).clearNotifications();
        UserRepo.updateTree();
        if(UserRepo.getUser(id).getNotifications().size()==0){
            return "1";
        }
        else {
            return "0";
        }
    }

    /**
     * Logins an user to its account
     * @param email String email of the user entering the account
     * @param password String password of the user
     * @return String "1" if login was successful, "0" if was unsuccessful, "2" if the email isn't in the database
     * @throws NoSuchAlgorithmException
     */
    @GET
    @Path("auth")
    @Produces(MediaType.APPLICATION_JSON)
    public String authUserAndPassword(@QueryParam("email") String email, @QueryParam("password") String password) throws NoSuchAlgorithmException {
        if (UserRepo.checkByID(email)) {
            User user = UserRepo.getUser(email);
            String userPassword = user.getPassword();
            String hashPassword = hashPassword(password);
            if (hashPassword.equals(userPassword)) {
                return "1";
            } else {
                return "0";
            }
        } else {
            return "2";
        }

    }

    /**
     * Shows the myMenu of a specific user and using a filter to sort the list
     * @param email String email of the user
     * @param filter String filter to be used as a sort
     * @return ArrayList<String> with the sorted recipes of the myMenu of that user
     */
    @GET
    @Path("myMenu")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> myMenu(@QueryParam("email") String email, @QueryParam("filter") String filter){
        ArrayList<String> myMenuList = new ArrayList<>();
        User user = UserRepo.getUser(email);
        SinglyList<Recipe> sortList = new SinglyList<>();

        for (int i:user.getRecipeList()) {
            Recipe recipe = RecipeRepo.getRecipe(i);
            sortList.add(recipe);
        }

        if (sortList.getLength() == 0){
            return myMenuList;
        } else if (sortList.getLength() == 1){
            Recipe recipe = RecipeRepo.getRecipe(user.getRecipeList().get(0));
            myMenuList.add(recipe.getId()+";"+recipe.getName()+
                    ";"+recipe.getAuthorName()+";"+recipe.getAuthorEmail());
            return myMenuList;
        }

        switch (filter){
            case "date":
                Sorter.bubbleSort(sortList);
                break;
            case "score":
                Sorter.quickSort(sortList);
                break;
            case "difficulty":
                Sorter.radixSort(sortList);
                break;
        }
        SinglyNode tmp = sortList.getHead();
        while (tmp!=null){
            Recipe recipe = (Recipe) tmp.getData();
            myMenuList.add(recipe.getId()+";"+recipe.getName()+
                    ";"+recipe.getAuthorName()+";"+recipe.getAuthorEmail());
            tmp =(SinglyNode) tmp.getNext();
        }
        return myMenuList;
    }

    /**
     * Shows the newsfeed of a specific user
     * @param email String email of the user
     * @return ArrayList<String> with the recipes of the newsfeed of that user
     */
    @GET
    @Path("newsfeed")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> newsfeed(@QueryParam("email") String email){
        ArrayList<String> newsfeed = new ArrayList<>();
        User user = UserRepo.getUser(email);
        SinglyList<Recipe> sortList = new SinglyList<>();

        for (int i:user.getNewsFeed()) {
            Recipe recipe = RecipeRepo.getRecipe(i);
            sortList.add(recipe);
        }

        if (sortList.getLength() == 0){
            return newsfeed;
        } else if (sortList.getLength() == 1){
            Recipe recipe = RecipeRepo.getRecipe(user.getNewsFeed().get(0));
            newsfeed.add(recipe.getId()+";"+recipe.getName()+
                    ";"+recipe.getAuthorName()+";"+recipe.getAuthorEmail());
            return newsfeed;
        }

        Sorter.bubbleSort(sortList);

        SinglyNode tmp = sortList.getHead();
        while (tmp!=null){
            Recipe recipe = (Recipe) tmp.getData();
            newsfeed.add(recipe.getId()+";"+recipe.getName()+
                    ";"+recipe.getAuthorName()+";"+recipe.getAuthorEmail());
            tmp =(SinglyNode) tmp.getNext();
        }
        return newsfeed;
    }

    /**
     * Checks if an user is following another user
     * @param ownEmail String email of the user sending the request
     * @param followingEmail String email of the user that needs to be checked
     * @return String "0" if not following, "1" if following
     */
    @GET
    @Path("isFollowing")
    @Produces(MediaType.APPLICATION_JSON)
    public String isFollowing(@QueryParam("ownEmail") String ownEmail, @QueryParam("followingEmail") String followingEmail){
        User ownUser = UserRepo.getUser(ownEmail);
        User followingUser = UserRepo.getUser(followingEmail);

        String response = "0";

        for (String email : ownUser.getFollowingEmails()) {
            if (email.contains(followingEmail+";"+followingUser.fullName())) {
                response = "1";
                break;
            }
        }
        return response;
    }


    /**
     * Checks if an user is following a business
     * @param email String email of the user sending the request
     * @param id int id of the business that needs to be checked
     * @return String "0" if not following, "1" if following
     */
    @GET
    @Path("isFollowingBusiness")
    @Produces(MediaType.APPLICATION_JSON)
    public String isFollowingB(@QueryParam("email") String email, @QueryParam("id") int id){
        User user = UserRepo.getUser(email);
        Business business = BusinessRepo.getBusiness(id);

        String response = "0";

        for (String data : user.getFollowingEmails()) {
            if (data.contains(business.getId()+";"+business.getName())) {
                response = "1";
                break;
            }
        }
        return response;
    }

    /**
     * Checks if an user is a chef
     * @param email String email of the user
     * @return String "0" if the user is a chef, "1" if not
     */
    @GET
    @Path("isChef")
    @Produces(MediaType.APPLICATION_JSON)
    public String isChef(@QueryParam("email") String email){
        User user = UserRepo.getUser(email);
        if (user.isChef()){
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Checks if a chef was rated by a specific user. Calls a private method
     * @param ownEmail String email of the user rating the chef
     * @param chefEmail String email of the chef to be rated
     * @return String "0" if the chef can be rated, "1" if it can't be rated
     */
    @GET
    @Path("isChefRated")
    @Produces(MediaType.APPLICATION_JSON)
    public String isChefRated(@QueryParam("ownEmail") String ownEmail, @QueryParam("chefEmail") String chefEmail){
        return checkChefRating(ownEmail, chefEmail);
    }

    /**
     * Rates a chef
     * @param ownEmail String email of the user rating the chef
     * @param chefEmail String email of the chef
     * @param score int Rating sent by the user
     * @return "0" if the rating was successful, "1" if not
     */
    @GET
    @Path("rateChef")
    @Produces(MediaType.APPLICATION_JSON)
    public String rateChef(@QueryParam("ownEmail") String ownEmail, @QueryParam("chefEmail") String chefEmail, @QueryParam("rating") int score){
        String response = checkChefRating(ownEmail, chefEmail);
        if (response.equals("0")){
            User chef = UserRepo.getUser(chefEmail);
            chef.addRated(ownEmail);
            chef.addChefScore(score);
            chef.sendMessage("new rating by "+ UserRepo.getUser(ownEmail).fullName());
        }
        UserRepo.updateTree();
        return response;
    }

    /**
     * Checks if a chef was rated by a specific user
     * @param ownEmail String email of the user rating the chef
     * @param chefEmail String email of the chef to be rated
     * @return String "0" if the chef can be rated, "1" if it can't be rated
     */
    private String checkChefRating(String ownEmail, String chefEmail) {
        User chef = UserRepo.getUser(chefEmail);
        String response = "0";
        if (ownEmail.equals(chefEmail) || !chef.isChef()){
            response = "1";
        }
        else {
            for(String userEmail:chef.getRatedBy()){
                if (userEmail.equals(ownEmail)){
                    response = "1";
                    break;
                }
            }
        }
        return response;
    }

    /**
     * Checks if recipe was already rated by an user
     * @param id int the id of the recipe
     * @param email String email of the user rating the recipe
     * @return String "1" if it was rated previously, "0" if not
     */
    @GET
    @Path("isRated")
    @Produces(MediaType.APPLICATION_JSON)
    public String isRated(@QueryParam("id") int id, @QueryParam("email") String email){
        return checkRating(id, email);
    }

    /**
     * Rates a recipe
     * @param id int the id of the recipe to be rated
     * @param email String the email of the user sending the rating
     * @param score int the score assigned by the user
     * @return String "1" if the user already rated this recipe, "0" if the recipe was scored successfully
     */
    @GET
    @Path("rateRecipe")
    @Produces(MediaType.APPLICATION_JSON)
    public String rateRecipe(@QueryParam("id") int id, @QueryParam("email") String email, @QueryParam("rating") int score){
        String response = checkRating(id, email);
        if (response.equals("0")){
            Recipe recipe = RecipeRepo.getRecipe(id);
            recipe.addRating(email);
            recipe.addScore(score);
            UserRepo.getUser(recipe.getAuthorEmail()).sendMessage("your recipe: "+recipe.getName()+" has been rated!");
        }
        RecipeRepo.updateTree();
        return response;
    }

    /**
     * Makes a user  follow another user
     * @param ownEmail String email of the user sending the request
     * @param followingEmail String email of the user who is being followed
     * @return String "0" if it already follows the user, "1" if the request was successful
     */
    @GET
    @Path("followUser")
    @Produces(MediaType.APPLICATION_JSON)
    public String followUser(@QueryParam("ownEmail") String ownEmail, @QueryParam("followingEmail") String followingEmail) {
        String response;
        User ownUser = UserRepo.getUser(ownEmail);
        User followingUser = UserRepo.getUser(followingEmail);

        boolean alreadyFollows = false;

        for (String email : ownUser.getFollowingEmails()) {
            if (email.equals(followingEmail+";"+followingUser.fullName())) {
                alreadyFollows = true;
                break;
            }
        }

        if (alreadyFollows) {
            ownUser.unFollowing(followingEmail+";"+followingUser.fullName()+";user");
            followingUser.unFollower(ownEmail+";"+ownUser.fullName()+";user");
            response = "0";
        }else{
            ownUser.addFollowing(followingEmail+";"+followingUser.fullName()+";user");
            followingUser.addFollower(ownEmail+";"+ownUser.fullName()+";user");
            followingUser.sendMessage(ownUser.fullName()+" is now following you");
            response = "1";
        }


        UserRepo.updateTree();
        return response;
    }

    @GET
    @Path("followBusiness")
    @Produces(MediaType.APPLICATION_JSON)
    public String followBusiness(@QueryParam("email") String email, @QueryParam("id") int id){
        String response;
        User user = UserRepo.getUser(email);
        Business business = BusinessRepo.getBusiness(id);

        boolean alreadyFollows = false;

        for (String data : user.getFollowingEmails()) {
            if (data.contains(String.valueOf(business.getId()))) {
                alreadyFollows = true;
                break;
            }
        }

        if (alreadyFollows) {
            user.unFollowing(business.getId()+";"+business.getName()+";business");
            business.unFollower(email+";"+user.fullName()+";user");
            response = "0";
        } else {
            user.addFollowing(business.getId()+";"+business.getName()+";business");
            business.addFollower(email+";"+user.fullName()+";user");
            response = "1";
        }
        UserRepo.updateTree();
        BusinessRepo.updateTree();
        return response;
    }

     /**
     * Changes an user password
     * @param email String email of the user to change the data
     * @param newPassword String the new password
     * @param password String the old password
     * @return String "1" if the password was changed, "0" if not
     * @throws NoSuchAlgorithmException
     */
    @GET
    @Path("editUserPassword")
    @Produces(MediaType.APPLICATION_JSON)
    public String editUser(@QueryParam("email") String email, @QueryParam("newPassword") String newPassword,
                           @QueryParam("password") String password) throws NoSuchAlgorithmException {
        String response;
        User user = UserRepo.getUser(email);
        if (user.getPassword().equals(hashPassword(password))) {
            user.setPassword(hashPassword(newPassword));
            response = "1";
        } else {
            response = "0";
        }
        UserRepo.updateTree();
        return response;
    }

    /**
     * Searches a list of recipes using a filter and a text to be matched
     * @param list String the list in String format of the recipes to be searched
     * @param data String text to be matched
     * @return ArrayList<String> with all the recipes that matched
     */
    @GET
    @Path("filterRecommend")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> filterRecommend(@QueryParam("array") String list, @QueryParam("data") String data){
        String filter = "";
        ArrayList<String> filterList = new ArrayList<>();

        if (list == null){
            return filterList;
        }

        if (list.length() == 0){
            return filterList;
        }

        for (String st:list.split(",")) {
            st = st.substring(1, st.length() - 1);
            filterList.add(st);
        }

        data = data.split(" ")[0];


        ArrayList<String> tagList = new ArrayList<>();

        tagList.add("VEGETARIAN");
        tagList.add("VEGAN");
        tagList.add("KOSHER");
        tagList.add("CELIAC");
        tagList.add("KETO");
        tagList.add("CARNIVORE");

        ArrayList<String> timeList = new ArrayList<>();

        timeList.add("BREAKFAST");
        timeList.add("BRUNCH");
        timeList.add("LUNCH");
        timeList.add("SNACK");
        timeList.add("DINNER");

        ArrayList<String> typeList = new ArrayList<>();

        typeList.add("APPETIZER");
        typeList.add("ENTREE");
        typeList.add("MAIN");
        typeList.add("ALCHOHOL");
        typeList.add("COLD");
        typeList.add("HOT");
        typeList.add("DESSERT");

        if (tagList.contains(data.toUpperCase())){
            filter = "tag";
        } else if (timeList.contains(data.toUpperCase())){
            filter = "time";
        } else if (typeList.contains(data.toUpperCase())){
            filter = "type";
        }

        switch (filter){
            case "time":
                filterList = filterTime(filterList, data);
                break;
            case "tag":
                filterList = filterTag(filterList, data);
                break;
            case "type":
                filterList = filterType(filterList, data);
                break;
        }

        return filterList;
    }

    /**
     * Searches a list of recipes by its dishTime using a text
     * @param list ArrayList<String> the list to be searched
     * @param search ArrayList<String> the text to be matched
     * @return ArrayList<String> the matching recipes
     */
    private ArrayList<String> filterTime(ArrayList<String> list, String search){
        ArrayList<String> filteredList = new ArrayList<>();

        for (String data:list){
            String object = data.split(";")[2];
            if (object.equalsIgnoreCase("recipe")){
                Recipe recipe = RecipeRepo.getRecipe(Integer.parseInt(data.split(";")[0]));
                if (recipe.getDishTime().toString().equalsIgnoreCase(search)){
                    filteredList.add(data);
                }
            }
        }

        return filteredList;
    }

    /**
     * Searches a list of recipes by its dishTags using a text
     * @param list ArrayList<String> the list to be searched
     * @param search ArrayList<String> the text to be matched
     * @return ArrayList<String> the matching recipes
     */
    private ArrayList<String> filterTag(ArrayList<String> list, String search){
        ArrayList<String> filteredList = new ArrayList<>();

        for (String data:list){
            String object = data.split(";")[2];
            if (object.equalsIgnoreCase("recipe")){
                Recipe recipe = RecipeRepo.getRecipe(Integer.parseInt(data.split(";")[0]));
                for (DishTag tag : recipe.getDishTags()){
                    if (tag.toString().equalsIgnoreCase(search)){
                        filteredList.add(data);
                    }
                }
            }
        }

        return filteredList;
    }

    /**
     * Searches a list of recipes by its dishType using a text
     * @param list ArrayList<String> the list to be searched
     * @param search ArrayList<String> the text to be matched
     * @return ArrayList<String> the matching recipes
     */
    private ArrayList<String> filterType(ArrayList<String> list, String search){
        ArrayList<String> filteredList = new ArrayList<>();

        for (String data:list){
            String object = data.split(";")[2];
            if (object.equalsIgnoreCase("recipe")){
                Recipe recipe = RecipeRepo.getRecipe(Integer.parseInt(data.split(";")[0]));
                if (recipe.getDishType().toString().contains(search.toUpperCase())){
                    filteredList.add(data);
                }
            }
        }

        return filteredList;
    }

    /**
     * Sends a list of recommended profiles to an specific user
     * @param email String email of the user searching recommendations
     * @return ArrayList<String> with 15 recommended profiles the user hasn't rated or followed.
     */
    @GET
    @Path("recommend")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> recommended(@QueryParam("email") String email){
        ArrayList<String> userList = UserRepo.recommend(email);
        ArrayList<String> recipeList = RecipeRepo.recommend(email);
        ArrayList<String> businessList = BusinessRepo.recommend(email);

        ArrayList<String> profilesList = createSearchList(userList, recipeList, businessList);

        Collections.shuffle(profilesList);

        ArrayList<String> returnList = new ArrayList<>();

        int i = 0;
        int maxSize = profilesList.size();

        if (maxSize > 15){
            maxSize = 15;
        }

        while (i < maxSize){
            returnList.add(profilesList.get(i));
            i++;
        }
        return returnList;
    }

    /**
     * Sends a list with the five highest rated recipes and businesses
     * @return ArrayList<String> of the highest rated profiles
     */
    @GET
    @Path("ratings")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> ratings(){
        ArrayList<String> ratingList = new ArrayList<>();
        ArrayList<String> recipeList = RecipeRepo.rating();
        ArrayList<String> businessList = BusinessRepo.rating();

        for (String data:recipeList){
            String[] dataL = data.split(";");
            ratingList.add(dataL[0]+";"+dataL[1]+";"+dataL[2]);
        }

        for (String data:businessList){
            String[] dataL = data.split(";");
            ratingList.add(dataL[0]+";"+dataL[1]+";"+dataL[2]);
        }

        return ratingList;
    }

    /**
     * Searches a text using an specific attribute of the recipe class
     * @param search String text to be searched
     * @param filter String attribute to be matched (dishTag, dishType, dishTime)
     * @return ArrayList<String> with all the matching profiles
     */
    @GET
    @Path("searchByFilter")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> filteredSearch(@QueryParam("search") String search, @QueryParam("filter") String filter){
        ArrayList<String> recipeList = new ArrayList<>();

        switch (filter){
            case "tag":
                recipeList = RecipeRepo.searchByTag(search);
                break;
            case "time":
                recipeList = RecipeRepo.searchByTime(search);
                break;
            case "type":
                recipeList = RecipeRepo.searchByType(search);
                break;
        }

        return recipePrio(recipeList);
    }

    /**
     * Makes the first 3 results be of a chef type user if possible
     * @param recipeList ArrayList<String> the list to be sorted
     * @return ArrayList<String> the sorted list
     */
    private ArrayList<String> recipePrio(ArrayList<String> recipeList){
        ArrayList<String> profilesList = new ArrayList<>();

        int i = 0;

        for (String data: recipeList){
            String[] stringList = data.split(";");
            if (stringList[3].equals("chef") && i < 3){
                profilesList.add(i, stringList[0]+";"+stringList[1]+";"+stringList[2]);
            } else {
                profilesList.add(stringList[0]+";"+stringList[1]+";"+stringList[2]);
            }
        }

        return recipeList;
    }

    /**
     * Searches in the database a line of text
     * @param search String name/text to be searched
     * @return ArrayList<String> with all the profiles that matched the search
     */
    @GET
    @Path("searchByName")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> search(@QueryParam("search") String search) {
        ArrayList<String> userList = UserRepo.searchUsers(search);
        ArrayList<String> recipeList = RecipeRepo.searchByName(search);
        ArrayList<String> businessList = BusinessRepo.search(search);
        ArrayList<String> profilesList = createSearchList(userList, recipeList, businessList);

        return profilesList;
    }

    /**
     * Makes a list of profiles have the first 3 Strings be of a chef type user
     * @param userList ArrayList<String> the list with user profiles
     * @param recipeList ArrayList<String> the list with recipe profiles
     * @param businessList ArrayList<String> the list with business profiles
     * @return ArrayList<String> the resulting list
     */
    private ArrayList<String> createSearchList(ArrayList<String> userList, ArrayList<String> recipeList,
                                               ArrayList<String> businessList){
        ArrayList<String> profilesList = new ArrayList<>();

        int i = 0;

        for (String data: userList){
            String[] stringList = data.split(";");
            if (stringList[2].equals("chef") && i < 3){
                profilesList.add(i, data);
                i++;
            } else {
                profilesList.add(data);
            }
        }

        for (String data: recipeList){
            String[] stringList = data.split(";");
            if (stringList[3].equals("chef") && i < 3){
                profilesList.add(i, stringList[0]+";"+stringList[1]+";"+stringList[2]);
            } else {
                profilesList.add(stringList[0]+";"+stringList[1]+";"+stringList[2]);
            }
        }

        for (String data: businessList){
            profilesList.add(data);
        }

        return profilesList;
    }

    /**
     * Shows a picture to the client
     * @param id String the identifier of the user/recipe
     * @return Response the image object
     */
    @GET
    @Path("getPicture")
    @Produces("image/png")
    public File getPicture(@QueryParam("id") String id) {
        //        Response.ResponseBuilder response = Response.ok(file);
//        response.header("Photo", "attachment:filename=DisplayName-" + id + ".png");
        return new File(System.getProperty("project.folder") + "/dataBase/photos/" + id + ".png");
    }


    /**
     * this method takes an id and a String in base64 and takes that image, decodes it into a byte array and sends it to a method to save on disk
     * @param id int id of the recipe
     * @param photo String in base64 of the photo
     */
    @POST
    @Path("addRecipePicture")
    @Consumes({MediaType.APPLICATION_JSON})
    public static void addRecipePicture(@QueryParam("id") int id, String photo) {
        System.out.println(photo);
        String location = System.getProperty("project.folder") + "/dataBase/photos/";
        try {
            byte[] decodedString = Base64.getDecoder().decode(photo.getBytes(StandardCharsets.UTF_8));
            RecipeRepo.getRecipe(id).setPhoto(saveToDisk(decodedString,String.valueOf(id),location));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        RecipeRepo.updateTree();
    }

    /**
     * this method takes an id and a String in base64 and takes that image, decodes it into a byte array and sends it to a method to save on disk
     * @param id String id of the user's picture
     * @param photo String in base64 of the photo
     */
    @POST
    @Path("addUserPicture")
    @Consumes(MediaType.APPLICATION_JSON)
    public static void addUserPicture(@QueryParam("id") String id, String photo) {
        System.out.println(photo);
        String location = System.getProperty("project.folder") + "/dataBase/photos/";
        try {
            //byte[] name = Base64.getEncoder().encode(photo.getBytes());
            byte[] decodedString = Base64.getDecoder().decode(photo.getBytes(StandardCharsets.UTF_8));
            UserRepo.getUser(id).setPhoto(saveToDisk(decodedString,String.valueOf(id),location));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        UserRepo.updateTree();

    }

    /**
     * this method takes an id and a String in base64 and takes that image, decodes it into a byte array and sends it to a method to save on disk
     * @param id int id of the business's picture
     * @param photo String in base64 of the photo
     */
    @POST
    @Path("addBusinessPicture")
    @Consumes(MediaType.APPLICATION_JSON)
    public static void addBusinessPicture(@QueryParam("id") int id, String photo) {
        System.out.println(photo);
        String location = System.getProperty("project.folder") + "/dataBase/photos/";
        try {
            byte[] decodedString = Base64.getDecoder().decode(photo.getBytes(StandardCharsets.UTF_8));
            BusinessRepo.getBusiness(id).setPhoto(saveToDisk(decodedString,String.valueOf(id),location));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BusinessRepo.updateTree();

    }

    /**
     * This method takes a byte array, and converts it to a saved .png image on file
     * @param image byte array of the image that wants to be saved on disk
     * @param id id to be used in the name of the new image file
     * @param location where the image is going to be saved
     * @return a String that represents the name of the file, to be saved as a reference on each object
     * @throws IOException exception
     */
    private static String saveToDisk(byte[] image, String id, String location) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(image);
        BufferedImage bImage = ImageIO.read(bis);
        ImageIO.write(bImage, "png", new File(location+id+"-picture.png") );
        System.out.println("image created");
        return id+"-picture";
    }

    /**
     * Sends a request to the server admins to be labeled as chef
     * @param id String email of the user sending the request
     * @return String "2" if the request was successful, "0" if the request is pending approval, "1" if the user is a chef
     */
    @GET
    @Path("chefRequest")
    @Produces(MediaType.APPLICATION_JSON)
    public String chefRequest(@QueryParam("id") String id){
        if(!UserRepo.getUser(id).isChef()) {
            if (!UserRepo.isActiveRequest(id)) {
                UserRepo.addChefRequest(id);
                UserRepo.getUser(id).sendMessage("We have received your request to become a chef!, please wait from 5 to 7 business days for your request to be processed, thank you!");
                return "2";
            }
            else{
                return "0";
            }
        }
        return "1";
    }

    /**
     * This method takes and int ID and returns a Business Obj
     * @param id int id of the business to request
     * @return Business obj
     */
    @GET
    @Path("getBusiness")
    public Business getBussiness(@QueryParam("id") int id){
        return BusinessRepo.getBusiness(id);
    }

    /**
     * This method is used to create a business obj assigns it an id, and adds it to the database, it also adds the id to the users id
     * @param business Business obj
     */
    @POST
    @Path("createBusiness")
    @Consumes(MediaType.APPLICATION_JSON)
    public static void createBusiness(Business business){
    int i = random.nextInt(999) +1001;
    while (BusinessRepo.checkId(i)){
        i = random.nextInt(999) +1001;
    }
    business.setId(i);
    UserRepo.getUser(business.getEmployeeList().get(0)).setBusiness(business.getId());
    BusinessRepo.addBusiness(business);
    addBusinessPicture(i,business.getPhoto());
    UserRepo.updateTree();
    }

    /**
     * This method adds an user to a business user list, and sets the reference on the users attribute
     * @param email String email of the user that is being added to the business
     * @param id int id of the business
     * @return String response, depending on different scenarios; 3:user doesnt exist, 2:business doesnt exist, 0 if the user already belongs to a business, and 1 if everything worked
     */
    @GET
    @Path("addEmployee")
    public String addEmployee(@QueryParam("email") String email,@QueryParam("id") int id){
        if(UserRepo.getUser(email)==null){
            return "3";
        }
        else if(BusinessRepo.getBusiness(id)==null){
            return "2";
        }
        else if(UserRepo.getUser(email).getBusiness()!=0){
            return "0";
        }
        else {
            BusinessRepo.getBusiness(id).addEmployee(email);
            UserRepo.getUser(email).setBusiness(id);
            BusinessRepo.updateTree();
            UserRepo.updateTree();
            return "1";
        }
    }

    /**
     * Checks if a recipe has been rated by a specific user
     * @param id int The id of the recipe
     * @param email String the email of the user
     * @return String "1" if it was rated, "0" if not.
     */
    private String checkRating(int id, String email){
        Recipe recipe = RecipeRepo.getRecipe(id);
        String response = "0";
        if (email.equals(recipe.getAuthorEmail())){
            response = "1";
        }
        else {
            for(String userEmail:recipe.getRatedBy()){
                if (userEmail.equals(email)){
                    response = "1";
                    break;
                }
            }
        }
        return response;
    }

    /**
     * Gets if an user is an employee of a business
     * @param email String email of the user
     * @param id int id of the business
     * @return String "1" if the user is an employee, "0" if not
     */
    @GET
    @Path("isEmployee")
    public String isEmployee(@QueryParam("email") String email, @QueryParam("id") int id){
        Business business = BusinessRepo.getBusiness(id);

        for (String data: business.getEmployeeList()){
            if (data.contains(email)){
                return "1";
            }
        }
        return "0";
    }
}
