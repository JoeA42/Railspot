package com.btp.dataStructures.nodes.GenericNodes;

/**
 * This class represents a node that forms part of the AVL tree
 * @param <T> A generic type
 */
public class AVLNode<T extends Comparable<T>>{
    private T element;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;

    /**
     * The constructor of the class called externally. It calls the private constructor.
     * @param element The element attribute that the node will contain
     */
    public AVLNode(T element){
        this(element, null, null);
    }

    /**
     * The actual constructor for this class
     * @param element The element attribute that the node will contain
     * @param left The left child of the node
     * @param right The right child of the node
     */
    private AVLNode(T element, AVLNode<T> left, AVLNode<T> right){
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 1;
    }

    /**
     * Getter for the element attribute
     * @return the value the node is storing
     */
    public T getElement() {
        return element;
    }

    /**
     * Setter for the element attribute
     * @param element the new value for element
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Getter for the left child
     * @return the AVLNode pointed by the left pointer
     */
    public AVLNode<T> getLeft() {
        return left;
    }

    /**
     * Setter for the left child
     * @param left the AVLNode left child
     */
    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    /**
     * Getter for the right child
     * @return the TreeNode pointed by the right pointer
     */
    public AVLNode<T> getRight() {
        return right;
    }

    /**
     * Setter for the right child
     * @param right the AVLNode right child
     */
    public void setRight(AVLNode<T> right) {
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
