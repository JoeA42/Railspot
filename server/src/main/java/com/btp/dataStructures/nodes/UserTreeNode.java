package com.btp.dataStructures.nodes;

import com.btp.serverData.clientObjects.User;

/**
 * This class represents a node that forms part of a tree
 */
public class UserTreeNode {
    private User element = null;
    private UserTreeNode left;
    private UserTreeNode right;

    /**
     * The constructor of the method called externally.
     * It calls the private constructor.
     */
    public UserTreeNode() {
        this(null, null);
    }

    /**
     * The actual constructor for this class
     * @param left The left child of the node
     * @param right The right child of the node
     */
    public UserTreeNode(UserTreeNode left, UserTreeNode right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Getter for the element attribute
     * @return the value the node is storing
     */
    public User getElement() {
        return element;
    }

    /**
     * Setter for the element attribute
     * @param element the new value for element
     */
    public void setElement(User element) {
        this.element = element;
    }

    /**
     * Getter for the left child
     * @return the TreeNode pointed by the left pointer
     */
    public UserTreeNode getLeft() {
        return left;
    }

    /**
     * Setter for the left child
     * @param left the TreeNode left child
     */
    public void setLeft(UserTreeNode left) {
        this.left = left;
    }

    /**
     * Getter for the right child
     * @return the TreeNode pointed by the right pointer
     */
    public UserTreeNode getRight() {
        return right;
    }

    /**
     * Setter for the right child
     * @param right the TreeNode right child
     */
    public void setRight(UserTreeNode right) {
        this.right = right;
    }
}
