package com.btp.dataStructures.nodes;

import com.btp.serverData.clientObjects.Recipe;

/**
 * This class represents a node that forms part of the AVL tree
 *
 */
public class RecipeNode {
    private Recipe element = null;
    private RecipeNode left;
    private RecipeNode right;
    private int height;

    /**
     * The constructor of the class called externally. It calls the private constructor.
     */
    public RecipeNode(){
        this(null, null);
    }

    /**
     * The actual constructor for this class
     * @param left The left child of the node
     * @param right The right child of the node
     */
    private RecipeNode(RecipeNode left, RecipeNode right){
        this.left = left;
        this.right = right;
        this.height = 1;
    }

    /**
     * Getter for the element attribute
     * @return the Recipe the node is storing
     */
    public Recipe getElement() {
        return this.element;
    }

    /**
     * Setter for the element attribute
     * @param element the new Recipe for element
     */
    public void setElement(Recipe element) {
        this.element = element;
    }

    /**
     * Getter for the left child
     * @return the RecipeNode pointed by the left pointer
     */
    public RecipeNode getLeft() {
        return left;
    }

    /**
     * Setter for the left child
     * @param left the RecipeNode left child
     */
    public void setLeft(RecipeNode left) {
        this.left = left;
    }

    /**
     * Getter for the right child
     * @return the RecipeNode pointed by the right pointer
     */
    public RecipeNode getRight() {
        return right;
    }

    /**
     * Setter for the right child
     * @param right the RecipeNode right child
     */
    public void setRight(RecipeNode right) {
        this.right = right;
    }

    /**
     * Getter for the height
     * @return an integer of the height of the node
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for the height
     * @param height the height of the node
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
