package com.btp.dataStructures.nodes;

import com.btp.serverData.clientObjects.Business;

/**
 * Nodes used for the Splay Tree data structure
 */
public class BusinessNode{
    private Business element;
    private BusinessNode left;
    private BusinessNode right;
    //needed argument for referencing the parent of the node
    private BusinessNode parent;

    /**
     * public constructor, to be used more simply
     */
    public BusinessNode(){
        this(null,null,null);
    }

    /**
     * private constructor for splay nodes. Invoked by public constructor
     * to define new data inserted to the splay tree
     * @param left the left 'child' node of the new node
     * @param right the right 'child' of the new node
     * @param parent the reference to the parent node
     */
    private BusinessNode(BusinessNode left, BusinessNode right, BusinessNode parent){
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    /**
     * getter method for the element held in the node
     * @return Business data value contained.
     */
    public Business getElement(){
        return this.element;
    }

    /**
     * setter method for the element in the node that has to be
     * modified or setted in the first place.
     * @param element the T-value that the user wants to put in the node
     */
    public void setElement(Business element) {
        this.element = element;
    }

    /**
     * getter method for the left 'child' node memory direction
     * @return the memory placement of the left child node object
     */
    public BusinessNode getLeft() {
        return this.left;
    }

    /**
     * setter method for the left 'child' node reference
     * @param newLeft the value which the node field will hold when invoked
     */
    public void setLeft(BusinessNode newLeft) {
        this.left = newLeft;
    }

    /**
     * getter method for the right 'child' node memory direction
     * @return the memory placement of the right child node object
     */
    public BusinessNode getRight() {
        return this.right;
    }

    /**
     * setter method for the right 'child' node reference
     * @param newRight the value which the node field will hold when invoked
     */
    public void setRight(BusinessNode newRight) {
        this.right = newRight;
    }

    /**
     * getter method for the 'parent' node memory direction
     * @return the memory placement of the parent node object
     */
    public BusinessNode parent() {
        return this.parent;
    }

    /**
     * setter method for the 'parent' node reference
     * @param parent the new parent to link the node to.
     */
    public void setParent(BusinessNode parent) {
        this.parent = parent;
    }
}
