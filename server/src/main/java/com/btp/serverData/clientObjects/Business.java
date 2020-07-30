package com.btp.serverData.clientObjects;

import com.btp.serverData.repos.RecipeRepo;
import com.btp.serverData.repos.UserRepo;

import java.util.ArrayList;

/**
 * Created business class, this holds data for all employees and the recipes
 *
 */
public class Business{
    private int id;
    private String name;
    private float rating = 0.0f;
    private int scoreTimes;
    private ArrayList<String> raters = new ArrayList<>();
    private ArrayList<Integer> privateList = new ArrayList<>();
    private ArrayList<Integer> publicList = new ArrayList<>();
    private ArrayList<String> followers = new ArrayList<>();
    private String location;
    private ArrayList<String> employeeList;
    private String contact;
    private String photo;
    private String businessHours;

    /**
     * Getter of the id attribute
     * @return int the id of the business
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of the id attribute
     * @param id int the id of the business
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter of the name attribute
     * @return String the name of the business
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     * @param name String the name of the business
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a new rating and stores the new average
     * @param score int the rating of the user
     */
    public void addRating(int score){
        float tmp = this.rating*this.scoreTimes;
        this.scoreTimes ++;
        if (score < 0.0){
            score = 0;
        } else if (score > 5.0){
            score = 5;
        }
        this.rating = (tmp + score)/this.scoreTimes;
    }

    /**
     * Getter of the employeeList attribute
     * @return ArrayList<String> list with the employees emails
     */
    public ArrayList<String> getEmployeeList() {
        return employeeList;
    }

    /**
     * Adds an employee to the employeeList
     * @param email String email of the user to add
     */
    public void addEmployee(String email){
        User user = UserRepo.getUser(email);
        employeeList.add(email);
    }

    /**
     * Setter of employeeList attribute
     * @param employeeList ArrayList<String> list with the employees emails
     */
    public void setEmployeeList(ArrayList<String> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     * Adds an user to the raters attribute
     * @param email String email of the user to be added
     */
    public void addRater(String email){
        this.raters.add(email);
    }

    /**
     * Setter of the rating attribute
     * @param rating float rating of the business
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Getter of the scoreTimes attribute
     * @return int the times the business has been rated
     */
    public int getScoreTimes() {
        return scoreTimes;
    }

    /**
     * Setter of the scoreTimes attribute
     * @param scoreTimes int the times the business has been rated
     */
    public void setScoreTimes(int scoreTimes) {
        this.scoreTimes = scoreTimes;
    }

    /**
     * Setter of the raters attribute
     * @param raters ArrayList<String> with the emails of the users that rated the business
     */
    public void setRaters(ArrayList<String> raters) {
        this.raters = raters;
    }

    /**
     * Setter of the privateList attribute
     * @param privateList ArrayList<Integer> with the ids of the private Recipes created by the business
     */
    public void setPrivateList(ArrayList<Integer> privateList) {
        this.privateList = privateList;
    }

    /**
     * Setter of the privateList attribute
     * @param publicList ArrayList<Integer> with the ids of the public Recipes created by the business
     */
    public void setPublicList(ArrayList<Integer> publicList) {
        this.publicList = publicList;
    }

    /**
     * Getter of the location attribute
     * @return String the location of the business
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter of the location attribute
     * @param location String the location of the business
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter of the contact attribute
     * @return String the contact info of the business
     */
    public String getContact() {
        return contact;
    }

    /**
     * Setter of the contact attribute
     * @param contact String the contact info of the business
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Getter of the photo attribute
     * @return String the photo filename
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Setter of the photo attribute
     * @param photo String the photo filename
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Getter of the businessHours attribute
     * @return String with the opening/closing schedule of the business
     */
    public String getBusinessHours() {
        return businessHours;
    }

    /**
     * Setter of the businessHours attribute
     * @param businessHours String with the opening/closing schedule of the business
     */
    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    /**
     * Getter of the rating attribute
     * @return float the rating of the business
     */
    public float getRating() {
        return rating;
    }

    /**
     * Getter of the raters attribute
     * @return ArrayList<String> with the emails of the users that rated the profile
     */
    public ArrayList<String> getRaters() {
        return raters;
    }

    /**
     * Getter of the privateList attribute
     * @return ArrayList<Integer> with ids of the private recipes created by the business
     */
    public ArrayList<Integer> getPrivateList() {
        return privateList;
    }

    /**
     * Getter of the publicList attribute
     * @return ArrayList<Integer> with ids of the public recipes created by the business
     */
    public ArrayList<Integer> getPublicList() {
        return publicList;
    }

    /**
     * this method is used to remove a recipe from the recipes list
     * @param id int id for the recipe
     */
    public void removeRecipe(int id) {
        if(privateList.contains(id)){
            privateList.remove(Integer.valueOf(id));
        }
        else if(publicList.contains(id)){
            publicList.remove(Integer.valueOf(id));
        }
    }

    /**
     * Setter of the followers attribute
     * @param followers ArrayList<String> with the users that follow the business
     */
    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    /**
     * Getter of the followers attribute
     * @return ArrayList<String> with the users follow the business
     */
    public ArrayList<String> getFollowers() {
        return followers;
    }

    /**
     * Adds a recipe id to the privateList
     * @param id int the id of the recipe to be added
     */
    public void addRecipe(int id, String type){
        if(type.equals("1")){
            this.getPrivateList().add(id);
        }
        else {
            this.getPublicList().add(id);
        }
    }

    /**
     * Swaps a recipe between private and public list
     * @param id int the id id of the recipe to be swapped
     * @return String "1" if moved from private to public, "0" from public to private
     */
    public String moveRecipe(int id){
        String response;
        if (this.getPrivateList().contains(id)){ ;
            this.getPublicList().add(id);
            this.getPrivateList().remove(Integer.valueOf(id));
            response = "1";
        } else {
            this.getPrivateList().add(id);
            this.getPublicList().remove(Integer.valueOf(id));
            response = "0";
        }
        return response;
    }

    /**
     * Deletes an user from the follower list
     * @param email String the email of the user to be removed
     */
    public void unFollower(String email) {
        this.getFollowers().remove(email);
    }

    /**
     * Adds an user to the follower list
     * @param email String the email of the user to be added
     */
    public void addFollower(String email) {
        this.getFollowers().add(email);
    }
}
