package com.btp.dataStructures.nodes.GenericNodes;

/**
 * Nodes used for the Splay Tree data structure
 * @param <T> Data type of the info stored on each Node
 */
public class SplayNode<T extends Comparable<T>> {
    private T element;
    private SplayNode<T> left;
    private SplayNode<T> right;
    //needed argument for referencing the parent of the node
    private SplayNode<T> parent;

    /**
     * public constructor, to be used more simply
     * @param inData
     */
    public SplayNode(T inData){
        this(inData,null,null,null);
    }

    /**
     * private constructor for splay nodes. Invoked by public constructor
     * to define new data inserted to the splay tree
     * @param inData the data held by the splay node
     * @param left the left 'child' node of the new node
     * @param right the right 'child' of the new node
     * @param parent the reference to the parent node
     */
    private SplayNode(T inData, SplayNode<T> left, SplayNode<T> right, SplayNode<T> parent){
        this.element = inData;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    /**
     * getter method for the element held in the node
     * @return T type data value contained.
     */
    public T getElement(){
        return this.element;
    }

    /**
     * setter method for the element in the node that has to be
     * modified or setted in the first place.
     * @param element the T-value that the user wants to put in the node
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * getter method for the left 'child' node memory direction
     * @return the memory placement of the left child node object
     */
    public SplayNode<T> getLeft() {
        return this.left;
    }

    /**
     * setter method for the left 'child' node reference
     * @param newLeft the value which the node field will hold when invoked
     */
    public void setLeft(SplayNode<T> newLeft) {
        this.left = newLeft;
    }

    /**
     * getter method for the right 'child' node memory direction
     * @return the memory placement of the right child node object
     */
    public SplayNode<T> getRight() {
        return this.right;
    }

    /**
     * setter method for the right 'child' node reference
     * @param newRight the value which the node field will hold when invoked
     */
    public void setRight(SplayNode<T> newRight) {
        this.right = newRight;
    }

    /**
     * getter method for the 'parent' node memory direction
     * @return the memory placement of the parent node object
     */
    public SplayNode<T> getParent() {
        return this.parent;
    }

    /**
     * setter method for the 'parent' node reference
     * @param parent the new parent to link the node to.
     */
    public void setParent(SplayNode<T> parent) {
        this.parent = parent;
    }
}
