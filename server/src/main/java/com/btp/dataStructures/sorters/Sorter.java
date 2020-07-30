package com.btp.dataStructures.sorters;

import com.btp.dataStructures.lists.SinglyList;
import com.btp.dataStructures.nodes.Node;
import com.btp.serverData.clientObjects.Recipe;
import java.util.Arrays;

/**
 * Class with static methods to sort the recipes
 */
public class Sorter {

    /**
     * Bubble sort method, calls the respective method according to its class
     * @param list a SinglyList with any type (Only sorts Integers and Recipes)
     */
    public static void bubbleSort(SinglyList list) {
        if (list.getHead().getData().getClass() == Integer.class) {
            bubbleSortInteger(list);
        } else if (list.getHead().getData().getClass() == Recipe.class){
            bubbleSortRecipe(list);
        } else{
            System.out.println("Can't sort this data type");
        }
    }

    /**
     * Bubble sort method, takes an integer list and orders it using the bubble sort algorithm
     * @param numberList an Integer list
     */
    private static void bubbleSortInteger(SinglyList<Integer> numberList) {
        Node<Integer> tmp;
        Node<Integer> next;
        while (!checkSortedInteger(numberList)) {
            for (int i = 0; i < numberList.getLength() - 1; i++) {
                tmp = numberList.get(i);
                next = numberList.get(i + 1);
                if(tmp.getData() > next.getData()){
                    numberList.swap(tmp,next);
                }
            }
        }
    }

    /**
     * Bubble sort method, takes a recipe list and orders it using the bubble sort algorithm
     * using as a parameter the postTime of the Recipe
     * @param recipeList a Recipe list
     */
    private static void bubbleSortRecipe(SinglyList<Recipe> recipeList){
        Node<Recipe> tmp;
        Node<Recipe> next;
        while (!checkSortedRecipe(recipeList)) {
            for (int i = 0; i < recipeList.getLength() - 1; i++) {
                tmp = recipeList.get(i);
                next = recipeList.get(i + 1);
                if(tmp.getData().getPostTime() < next.getData().getPostTime()){
                    recipeList.swap(tmp,next);
                }
            }
        }
    }

    /**
     * Insertion sort method, calls the respective method according to its class
     * @param list a SinglyList with any type (Only sorts Integers and Recipes)
     */
    public static void insertionSort(SinglyList list){
        if (list.getHead().getData().getClass() == Integer.class) {
            insertionSortInteger(list);
        } else if (list.getHead().getData().getClass() == Recipe.class){
            insertionSortRecipe(list);
        } else{
            System.out.println("Can't sort this data type");
        }
    }

    /**
     * insertion sort method, takes an integer list and orders it using the insertion sort algorithm
     * @param numberList an Integer list
     */
    private static void insertionSortInteger(SinglyList<Integer> numberList){
        int tmp;
        for (int i = 1; i < numberList.getLength(); i++) {
            tmp = numberList.get(i).getData();
            int j = i - 1;

            while(j >= 0 && numberList.get(j).getData() > tmp){
                numberList.swap(numberList.get(j), numberList.get(j+1));
                j--;
            }
        }
    }

    /**
     * insertion sort method, takes a recipe list and orders it using the insertion sort algorithm
     * using as a parameter the recipe name
     * @param recipeList an Integer list
     */
    private static void insertionSortRecipe(SinglyList<Recipe> recipeList){
        long tmp;
        for (int i = 1; i < recipeList.getLength(); i++){
            tmp = recipeList.get(i).getData().getPostTime();
            int j = i - 1;

            while(j >= 0 && recipeList.get(j).getData().getPostTime() > tmp){
                recipeList.swap(recipeList.get(j), recipeList.get(j+1));
                j--;
            }
        }
    }

    /**
     * Quicksort method, calls the respective method according to its class
     * @param list a SinglyList with any type (Only sorts Integers and Recipes)
     */
    public static void quickSort(SinglyList list){
        int length = list.getLength();
        if (list.getHead().getData().getClass() == Integer.class) {
            partitionInteger(list, 0, length - 1);
        } else if (list.getHead().getData().getClass() == Recipe.class){
            partitionRecipe(list, 0, length - 1);
        } else{
            System.out.println("Can't sort this data type");
        }
    }

    public static void radixSort(SinglyList list){
        if (list.getHead().getData().getClass() == Integer.class) {
            radixSortInteger(list);
        } else if (list.getHead().getData().getClass() == Recipe.class){
            radixSortRecipe(list);
        } else{
            System.out.println("Can't sort this data type");
        }
    }

    /**
     * main method for radixSort implementation. Takes an integer list and sorts it using
     * the Radix Sorting algorithm.
     * note:
     * This method is based on the tutorial recovered from https://www.geeksforgeeks.org/radix-sort/
     * with only minor implementation-related changes.
     * @param numberList the integer-containing instance that the user desires to sort.
     */
    private static void radixSortInteger(SinglyList<Integer> numberList){
        int n = numberList.getLength();

        // Uses getMax method to get the max value in the list
        int m = getMax(numberList, n);

        // starts doing counting sort for every digit.
        // passes an exponent of 10 (10 ^i) with i being the current positional value.
        while (!checkSortedInteger(numberList)){
            for (int exp = 1; m/exp > 0; exp *= 10){
                countSort(numberList, n , exp);
            }
        }
    }

    /**
     * main method for radixSort implementation. Takes a Recipe list and sorts it using
     * the Radix Sorting algorithm.
     * note:
     * This method is based on the tutorial recovered from https://www.geeksforgeeks.org/radix-sort/
     * with only minor implementation-related changes.
     * @param recipeList the recipe-containing instance that the user desires to sort.
     */
    public static void radixSortRecipe(SinglyList<Recipe> recipeList){
        int n = recipeList.getLength();

        // Uses getMax method to get the max value in the list
        int m = getMaxRecipe(recipeList, n);

        // starts doing counting sort for every digit.
        // passes an exponent of 10 (10 ^i) with i being the current positional value.
        while (!checkSortedRadix(recipeList)){
            for (int exp = 1; m/exp > 0; exp *= 10){
                countSortRecipe(recipeList, n , exp);
            }
        }
    }

    /**
     * Method for the counting sort algorithm, working over an unsorted list.
     * note:
     * This method is based on the tutorial recovered from https://www.geeksforgeeks.org/radix-sort/
     * with only minor implementation-related changes.
     * @param numberList list to be sorted according to the digit being watched by Radix.
     * @param n amount of elements in the list (length)
     * @param exponent current digit being sorted by the main algorithm (base 10)
     */
    private static void countSort(SinglyList<Integer> numberList, int n, int exponent){
        int[] output = new int[n]; //output array
        int i;
        int[] count = new int[10];
        Arrays.fill(count,0);

        // Store count of found values in count[]
        for (i = 0; i < n; i++) {
            count[ (numberList.get(i).getData()/exponent) % 10 ]++;
        }

        // Change count[i] so that it contains
        // an actual position of this digit in output array.
        for (i = 1; i < 10; i++){
            count[i] += count[i - 1];
        }

        // Build the output array
        for (i = n - 1; i >= 0; i--){
            output[ count[ (numberList.get(i).getData()/exponent) % 10] - 1 ] = numberList.get(i).getData();
            count [ (numberList.get(i).getData() / exponent) % 10]--;
        }

        // Copy the output array to the list, so that the list now contains
        // sorted numbers according to current digit
        for (i = 0; i < n; i++){
            numberList.get(i).setData(output[i]);
        }
    }

    /**
     * Method for the counting sorth algorithm, working over an unsorted list.
     * note:
     * This method is based on the tutorial recovered from https://www.geeksforgeeks.org/radix-sort/
     * with only minor implementation-related changes.
     * @param numberList list to be sorted according to the digit being watched by Radix.
     * @param n amount of elements in the list (length)
     * @param exponent current digit being sorted by the main algorithm (base 10)
     */
    private static void countSortRecipe(SinglyList<Recipe> numberList, int n, int exponent){
        Recipe[] output = new Recipe[n]; //output array
        int i;
        int[] count = new int[10];
        Arrays.fill(count,0);

        // Store count of found values in count[]
        for (i = 0; i < n; i++) {
            count[ (numberList.get(i).getData().getDifficulty()/exponent) % 10 ]++;
        }

        // Change count[i] so that it contains
        // an actual position of this digit in output array.
        for (i = 1; i < 10; i++){
            count[i] += count[i - 1];
        }

        // Build the output array
        for (i = n - 1; i >= 0; i--){
            output[ count[ (numberList.get(i).getData().getDifficulty()/exponent) % 10] - 1 ] = numberList.get(i).getData();
            count [ (numberList.get(i).getData().getDifficulty() / exponent) % 10]--;
        }

        // Copy the output array to the list, so that the list now contains
        // sorted numbers according to current digit
        for (i = 0; i < n; i++){
            numberList.get(i).setData(output[i]);
        }
    }

    /**
     * this method is used to get the highest value in a singly-linked list.
     * can be modified to use a different data structure by declaring a different type in the parameters.
     * @param numberList the specific singly-linked instance that the user desires to check.
     * @param n the number of elements (nodes) in the structure at the moment of calling the method. Also known as length.
     * @return integer value obtained from the list, corresponding to the largest number in it.
     */
    private static int getMax(SinglyList<Integer> numberList, int n){
        int mx = numberList.getHead().getData();
        for (int i = 1; i < n; i++) {
            if (numberList.get(i).getData() > mx){
                mx = numberList.get(i).getData();
            }
        }
        return mx;
    }

    private static int getMaxRecipe(SinglyList<Recipe> recipeList, int n){
        int mx = recipeList.getHead().getData().getDifficulty();
        for (int i = 1; i < n; i++) {
            if (recipeList.get(i).getData().getDifficulty() > mx){
                mx = recipeList.get(i).getData().getDifficulty();
            }
        }
        return mx;
    }

    /**
     * This method takes a list and returns a boolean value, true if the list is sorted, false otherwise.
     * @param list an integer list
     * @return boolean value, true if sorted, false if not
     */
    private static boolean checkSortedInteger(SinglyList<Integer> list){
        boolean sorted = false;
        Node<Integer> tmp;
        Node<Integer> next;
        for (int i = 0; i < list.getLength()-1; i++) {
            tmp = list.get(i);
            next = list.get(i + 1);
            if(tmp.getData() > next.getData()){
                sorted = false;
                i = list.getLength();
            }
            else{
                sorted = true;
            }
        }
        return sorted;
    }

    /**
     * This method takes a list and returns a boolean value, true if the list is sorted, false otherwise.
     * @param list a recipe list
     * @return boolean true if sorted, false if not
     */
    private static boolean checkSortedRecipe(SinglyList<Recipe> list){
        boolean sorted = false;
        Node<Recipe> tmp;
        Node<Recipe> next;
        for (int i = 0; i < list.getLength()-1; i++) {
            tmp = list.get(i);
            next = list.get(i + 1);
            if(tmp.getData().getPostTime() < next.getData().getPostTime()){
                sorted = false;
                i = list.getLength();
            }
            else{
                sorted = true;
            }
        }
        return sorted;
    }

    /**
     * This method takes a list and returns a boolean value, true if the list is sorted, false otherwise.
     * @param list a recipe list
     * @return boolean true if sorted, false if not
     */
    private static boolean checkSortedRadix(SinglyList<Recipe> list){
        boolean sorted = false;
        Node<Recipe> tmp;
        Node<Recipe> next;
        for (int i = 0; i < list.getLength()-1; i++) {
            tmp = list.get(i);
            next = list.get(i + 1);
            if(tmp.getData().getDifficulty() > next.getData().getDifficulty()){
                sorted = false;
                i = list.getLength();
            }
            else{
                sorted = true;
            }
        }
        return sorted;
    }

    /**
     * This method is the recursive part of the quicksort algorithm
     * @param list an integer list
     * @param low  first element of the list
     * @param high last element of the list
     */
    private static void partitionInteger(SinglyList<Integer> list, int low, int high){
        int i = low;
        int j = high;
        int pivot = list.get(low + (high - low) / 2).getData();
        while (i <= j){
            while (list.get(i).getData() < pivot){
                i++;
            }
            while (list.get(j).getData() > pivot){
                j--;
            }
            if (i <= j){
                list.swap(list.get(i), list.get(j));
                i++;
                j--;
            }
        }
        if (low < j){
            partitionInteger(list, low, j);
        }
        if (i < high){
            partitionInteger(list, i, high);
        }
    }

    /**
     * This method is the recursive part of the quicksort algorithm
     * @param list an Recipe list
     * @param low  first element of the list
     * @param high last element of the list
     */
    private static void partitionRecipe(SinglyList<Recipe> list, int low, int high){
        int i = low;
        int j = high;
        float pivot = list.get(low + (high - low) /2).getData().getScore();
        while (i <= j) {
            while (list.get(i).getData().getScore() > pivot){
                i++;
            }
            while (list.get(j).getData().getScore() < pivot){
                j--;
            }
            if (i <= j){
                list.swap(list.get(i), list.get(j));
                i++;
                j--;
            }
        }
        if (low < j){
            partitionRecipe(list, low, j);
        }
        if (i < high){
            partitionRecipe(list, i, high);
        }
    }
}