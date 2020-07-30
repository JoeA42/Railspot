package com.btp.serverData.clientObjects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is the class of the recipe obj, it holds the recipe information
 */
public class Recipe implements Comparable<Recipe> {
    private int businessId;
    private String name;
    private String authorEmail;
    private DishTime dishTime;
    private String authorName;
    private int portions;
    private int duration; //in minutes
    private DishType dishType;
    private int difficulty;
    private ArrayList<DishTag> dishTags;
    private String photo;
    private ArrayList<String> ingredientsList;
    private ArrayList<String> instructions;
    private float price;
    private int id;
    private long postTime;
    private String postTimeString;
    private float score = 0.0f;
    private int scoreTimes = 0;
    private final ArrayList<String> comments = new ArrayList<>();
    private final ArrayList<String> ratedBy = new ArrayList<>();

    /**
     * Getter for the business id
     * @return
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     * setter for the business id
     * @param businessId
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     * setter for the recipe's photo
     * @param name string id of the photo
     */
    public void setPhoto(String name) {
        this.photo = name;
    }

    /**
     * Getter of the id attribute
     * @return int the unique id associated to a recipe
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of the id attribute
     * @param id int the unique id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter of the name attribute
     * @return String the name of the recipe
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     * @param name String the name of the recipe to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the author attribute
     * @return User the author attribute
     */
    public String getAuthorEmail() {
        return authorEmail;
    }

    /**
     * Setter of the authorEmail attribute
     * @param authorEmail String the email of the author
     */
    public void setAuthorEmail(String authorEmail){
        this.authorEmail = authorEmail;
    }

    /**
     * Getter of the dishTime attribute
     * @return ENUM the dishTime attribute
     */
    public DishTime getDishTime() {
        return dishTime;
    }

    /**
     * Setter of the dishTime attribute
     * @param dishTime ENUM the dishTime to be set
     */
    public void setDishTime(DishTime dishTime) {
        this.dishTime = dishTime;
    }

    /**
     * Getter of the portions attribute
     * @return int the portions attribute
     */
    public int getPortions() {
        return portions;
    }

    /**
     * Setter of the portions attribute
     * @param portions int the portions to be set
     */
    public void setPortions(int portions) {
        this.portions = portions;
    }

    /**
     * Getter of the duration attribute
     * @return int the duration attribute
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter of the duration attribute
     * @param duration int the duration to be set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getter of the dishType attribute
     * @return ENUM of the dishType attribute
     */
    public DishType getDishType() {
        return dishType;
    }

    /**
     * Setter of the dishType attribute
     * @param dishType ENUM of the DishType to be set
     */
    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    /**
     * Getter of the difficulty attribute
     * @return int the difficulty attribute
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Setter of the  difficulty attribute
     * @param difficulty int difficulty to be set
     */
    public void setDifficulty(int difficulty) {
        if(difficulty<1){
            this.difficulty = 1;
        }
        else if(difficulty>5){
            this.difficulty = 5;
        } else {
            this.difficulty = difficulty;
        }
    }

    /**
     * Getter of the dishTags attribute
     * @return ArrayList<DishTag> of the dishTags attribute
     */
    public ArrayList<DishTag> getDishTags() {
        return dishTags;
    }

    /**
     * Setter of the dishTags attribute
     * @param dishTags ArrayList<DishTag> of the dishTags to be set
     */
    public void setDishTags(ArrayList<DishTag> dishTags) {
        this.dishTags = dishTags;
    }

    /**
     * Getter of the ingredientsList attribute
     * @return ArrayList<Ingredient> of the ingredientsList attribute
     */
    public ArrayList<String> getIngredientsList() {
        return ingredientsList;
    }

    /**
     * Setter of the ingredientsList attribute
     * @param ingredientsList ArrayList<Ingredient> of the ingredientList to be set
     */
    public void setIngredientsList(ArrayList<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    /**
     * Getter of the instructions attribute
     * @return ArrayList<String> of the instructions attribute
     */
    public ArrayList<String> getInstructions() {
        return instructions;
    }

    /**
     * Setter of the instructions attribute
     * @param instructions ArrayList<String> of the instructions to be set
     */
    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    /**
     * Getter of the price attribute
     * @return int of the price attribute
     */
    public float getPrice() {
        return price;
    }

    /**
     * Setter of the price attribute
     * @param price int of the price to be set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    /**
     * Getter of the postTime attribute
     * @return LocalDateTime of the postTime attribute
     */
    public long getPostTime() {
        return postTime;
    }

    /**
     * Setter of the postTime attribute
     * @param postTime LocalDateTime of the postTime to be set
     */
    public void setPostTime(long postTime) {
        this.postTime = postTime;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date(postTime);
        this.postTimeString = sdf.format(date);
    }

    /**
     * Getter of the score attribute
     * @return float of the score attribute
     */
    public float getScore() {
        return score;
    }

    /**
     * getter for the string of the time the recipe was posted
     * @return String representing the time the recipe was posted
     */
    public String getPostTimeString() {
        return postTimeString;
    }

    /**
     * Setter of the score attribute
     * @param score score by one of the other users
     */
    public void addScore(float score) {
        float tmp = this.score*this.scoreTimes;
        this.scoreTimes ++;
        if (score < 0.0){
            score = 0;
        } else if (score > 5.0){
            score = 5;
        }
        this.score = (tmp + score)/this.scoreTimes;
    }

    /**
     * Getter of the scoreTimes attribute
     * @return int of the scoreTimes attribute
     */
    public int getScoreTimes() {
        return scoreTimes;
    }

    /**
     * Getter for the emails that have rated this recipe
     * @return ArrayList of emails
     */
    public ArrayList<String> getRatedBy() {
        return ratedBy;
    }

    /**
     * This method adds a rating to the recipe's rating
     * @param email email of the person rating the recipe
     */
    public void addRating(String email){
        this.ratedBy.add(email);
    }

    /**
     * Getter for the comments on the recipe
     * @return ArrayList of comments
     */
    public ArrayList<String> getComments() {
        return comments;
    }

    /**
     * this method add a comment to the recipe
     * @param comment String comment
     */
    public void addComment(String comment){
        this.comments.add(comment);
    }

    /**
     * Getter for the name of the author
     * @return String of the author's name
     */
    public String getAuthorName() {
        return this.authorName;
    }

    /**
     * Setter for the name of the author
     * @param name String of the author's name
     */
    public void setAuthorName(String name){
        this.authorName = name;
    }

    /**
     * Compares this Recipe to the Recipe in the parameter using their id attribute
     * @param recipe the Recipe to be compared
     * @return int the result of the comparison
     */
    @Override
    public int compareTo(Recipe recipe) {
        return this.getId() - recipe.getId();
    }
}
