package com.btp.serverData.repos;

import com.btp.Initializer;
import com.btp.dataStructures.trees.RecipeTree;
import com.btp.serverData.clientObjects.Recipe;
import com.btp.utils.DataWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the main repository for recipes
 */
public class RecipeRepo {

    private static RecipeTree recipeTree = new RecipeTree();
    private static final DataWriter<RecipeTree> dataWriter = new DataWriter<>();
    private static final String path =System.getProperty("project.folder")+"/dataBase/recipeDataBase.json";

    /**
     * Adds a recipe to the RecipeRepo
     * @param recipe Recipe to be added
     */
    public static void addRecipe(Recipe recipe){
        System.out.println("adding recipe...");
        recipeTree.insert(recipe);
        System.out.println("Recipe added...");
        if(Initializer.isGUIOnline()){
            Initializer.getServerGUI().printLn("Recipe added");
        }
        dataWriter.writeData(recipeTree, path);
    }

    /**
     * this method is used to remove a recipe from the recipeRepo
     * @param id int id of the recipe that wants to be removed
     */
    public static void deleteRecipe(int id){
        recipeTree.delete(id);
    }

    /**
     * Gets a recipe from the RecipeRepo using its id
     * @param id int id of the Recipe to get
     * @return the Recipe of that specific id
     */
    public static Recipe getRecipe(int id){
        return recipeTree.getElementById(id);
    }

    /**
     * This method is used to search the recipe in the tree using the name
     * @param data String value of the search
     * @return a recipe if it finds one, or null if the search yields no results
     */
    public static ArrayList<String> searchByName(String data){
        return recipeTree.searchByName(data);
    }

    /**
     * This method is used to load the tree from the json file when the server begins
     * @throws IOException exception
     */
    public static void loadTree() throws IOException {
        System.out.println("loading recipe data base...");
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader file = new FileReader(path);
        recipeTree = objectMapper.readValue(file, recipeTree.getClass());
    }

    /**
     * this method is used to check if an id has already been used
     * @param id int id to check
     * @return boolean value, depending on the id exists or not
     */
    public static boolean checkId(int id){
        return recipeTree.checkById(id);
    }

    /**
     * This method is used to recommend recipes
     * @param data String data to be used in the recommendation
     * @return recommendations based on search
     */
    public static ArrayList<String> recommend(String data){
        return recipeTree.recommend(data);
    }

    /**
     * This method is used to return the 5 recipes with the highest rating
     * @return ArrayList of strings for the 5 recipes with the highest rating
     */
    public static ArrayList<String> rating(){
        return recipeTree.rating();
    }

    /**
     * This method is used to search recipes using the type of recipe
     * @param data data to be used on the search
     * @return ArrayList of strings of the results of the search
     */
    public static ArrayList<String> searchByType(String data){
        return recipeTree.searchByType(data);
    }

    /**
     * This method is used to search recipes using the time of recipe
     * @param data data to be used on the search
     * @return ArrayList of strings of the results of the search
     */
    public static ArrayList<String> searchByTime(String data){
        return recipeTree.searchByTime(data);
    }

    /***
     * This method is used to search recipes using the tags of the recipe
     * @param data data to be used on the search
     * @return ArrayList of strings of the results of the search
     */
    public static ArrayList<String> searchByTag(String data){
        return recipeTree.searchByTag(data);
    }

    /**
     * this method is used to save changes to the json dataBase
     */
    public static void updateTree() {
        dataWriter.writeData(recipeTree, path);
    }
}
