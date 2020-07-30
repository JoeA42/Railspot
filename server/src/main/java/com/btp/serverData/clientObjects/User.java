package com.btp.serverData.clientObjects;

import com.btp.serverData.repos.RecipeRepo;
import java.util.ArrayList;

/**
 * This is the Class for the User obj, it holds the user data
 */
public class User implements Comparable<User> {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isChef = false;
    private ArrayList<String> followerEmails = new ArrayList<>();
    private ArrayList<String> followingEmails = new ArrayList<>();
    private ArrayList<Integer> recipeList = new ArrayList<>();
    private int business;
    private String photo;
    private ArrayList<Integer> newsFeed = new ArrayList<>();
    private ArrayList<String> ratedBy = new ArrayList<>();
    private float chefScore = 0;
    private int chefScoreTimes = 0;
    private int age;
    private ArrayList<String> notifications = new ArrayList<>();


    public String getPhoto() {
        return photo;
    }

    /**
     * This method is used to add a photo to the user's photo list
     * @param photo String name of the photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Getter of the id attribute
     * @return int of the id attribute
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter of the id attribute
     * @param firstName int the id to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter of the name attribute
     * @return String of the name attribute
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter of the id attribute
     * @param lastName String the name to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter of the email attribute
     * @return String of the email attribute
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter of the email attribute
     * @param email String the email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter of the password attribute
     * @return String of the password attribute
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of the password attribute
     * @param password String the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of the age attribute
     * @return int of the age attribute
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter of the age attribute
     * @param age int the age to be set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Adds a recipe id to the author personal recipe list
     * @param id the id of the recipe
     */
    public void addRecipe(int id) {
        this.recipeList.add(id);
    }

    /**
     * Getter of the recipeList
     * @return ArrayList<Integer> the recipe list
     */
    public ArrayList<Integer> getRecipeList(){
        return this.recipeList;
    }

    /**
     * this method adds a follower to the users follower list
     * @param email String of the email of the follower
     */
    public void addFollower(String email){
        this.followerEmails.add(email);
    }

    /**
     * this method removes a follower from the users follower list
     * @param email String of the email of the follower to be removed
     */
    public void unFollower(String email) {
        this.followerEmails.remove(email);
    }

    /**
     * this method adds an email reference of someone this user is following
     * @param email String email, of the person to be followed
     */
    public void addFollowing(String email){
        this.followingEmails.add(email);
    }

    /**
     * This method removes an email reference of someone this user is following
     * @param email String email, of the person to stop being followed
     */
    public void unFollowing(String email){
        this.followingEmails.remove(email);
    }

    /**
     * This method is used to retrieve the list of followers
     * @return ArrayList of the followers of this user
     */
    public ArrayList<String> getFollowerEmails(){
        return this.followerEmails;
    }

    /**
     * This method is used to retrieve the list of people this user is following
     * @return ArrayList of the people being followed
     */
    public ArrayList<String> getFollowingEmails(){
        return this.followingEmails;
    }

    /**
     * getter for the chef variable
     * @return boolean value that determines if this user has been marked as a chef
     */
    public boolean isChef() {
        return this.isChef;
    }

    /**
     * setter for the chef variable
     * @param Chef boolean value to be set
     */
    public void setChef(boolean Chef) {
        this.isChef = Chef;
    }

    /**
     * returns the chef score of this user
     * @return
     */
    public float getChefScore() {
        return this.chefScore;
    }

    /**
     * This method is used to get the list of people that have rated this user
     * @return ArrayList of people that rated this user
     */
    public ArrayList<String> getRatedBy() {
        return ratedBy;
    }

    /**
     * This method adds an email reference of the people that have rated this user
     * @param email String email of the user that makes the rating
     */
    public void addRated(String email){
        ratedBy.add(email);
    }

    /**
     * This method is used to add to the chef's score
     * @param score the score to be added to the user
     */
    public void addChefScore(float score){
        float tmp = this.chefScore * this.chefScoreTimes;
        this.chefScoreTimes++;
        if (score < 0.0){
            score = 0;
        } else if (score > 5.0){
            score = 5;
        }
        this.chefScore = (tmp + score)/chefScoreTimes;
    }

    /**
     * returns the amount of times this user has been rated
     * @return
     */
    public int getChefScoreTimes() {
        return this.chefScoreTimes;
    }

    /**
     * This method returns the list of businesses associated with this user
     * @return
     */
    public int getBusiness() {
        return business;
    }

    public void setBusiness(int id){
        this.business=id;
    }

    /**
     * Getter for the News feed of the user
     * @return ArrayList of recipes in the users news feed
     */
    public ArrayList<Integer> getNewsFeed() {
        return newsFeed;
    }

    /**
     * This method is used to add an int id of a recipe to the news feed
     * @param id int id of the recipe
     */
    public void addNewsFeed(int id){
        newsFeed.add(id);
    }

    /**
     * Compares this User to the User in the parameter using their id attribute
     * @param user the User to be compared
     * @return int the result of the comparison
     */
    @Override
    public int compareTo(User user) {
        return this.getEmail().compareToIgnoreCase(user.getEmail());
    }

    /**
     * This method is used to return the full name of the user
     * @return String that corresponds to the full name
     */
    public String fullName() {
        return getFirstName()+" "+getLastName();
    }

    /**
     * This method adds a notification to the users list of notifications
     * @param message String of the notification to be added
     */
    public void sendMessage(String message) {
        notifications.add(message);
    }

    /**
     * This method is used to retrieve the notifications for the seller
     * @return ArrayList of notifications
     */
    public ArrayList<String> getNotifications(){
        return this.notifications;
    }

    /**
     * This method is used to delete a single notification
     * @param message the message to be deleted
     */
    public void deleteMessage(int message){
        notifications.remove(message);
    }

    /**
     * This method is used to return a list of the recipes that were created by this user
     * @return Arraylist of ints corresponding to recipes
     */
    public ArrayList<Integer> userCreatedRecipes(){
        ArrayList<Integer> userRecipes = new ArrayList<>();
        for (Integer integer : recipeList) {
            if (RecipeRepo.getRecipe(integer).getAuthorEmail().equals(this.email)) {
                userRecipes.add(integer);
            }
        }
        return userRecipes;
    }

    /**
     * This method clears all notifications from the user
     */
    public void clearNotifications() {
        notifications.clear();
    }
}

