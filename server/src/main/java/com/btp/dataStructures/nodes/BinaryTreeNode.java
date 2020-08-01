package com.btp.dataStructures.nodes;

/**
 * This class represents a node that forms part of a tree
 * @param <T> A generic type
 */
public class BinaryTreeNode<T extends Comparable<T>> {
    private T element = null;
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;

    /**
     * The actual constructor for this class
     * @param left The left child of the node
     * @param right The right child of the node
     */
    public BinaryTreeNode(BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.left = left;
        this.right = right;
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
     * @return the TreeNode pointed by the left pointer
     */
    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    /**
     * Setter for the left child
     * @param left the TreeNode left child
     */
    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    /**
     * Getter for the right child
     * @return the TreeNode pointed by the right pointer
     */
    public BinaryTreeNode<T> getRight() {
        return right;
    }

    /**
     * Setter for the right child
     * @param right the TreeNode right child
     */
    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }
}
