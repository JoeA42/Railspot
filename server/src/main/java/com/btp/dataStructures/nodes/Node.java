package com.btp.dataStructures.nodes;

/**
 * Superclass of all Nodes. Stores data on lists.
 * @param <T> Data type of the info stored on each Node
 */
public abstract class Node<T> {

    protected T data;

    /**
     * Constructor that sets the data to the node
     * @param data data of generic type saved on new node
     */
    public Node(T data) {
        this.data = data;
    }

    /**
     * Gets the data store on the node
     * @return data stored on node
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data stored on node
     * @param data data stored on node
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets the previous node on doubly nodes
     * @return previous node (only on doubly nodes)
     */
    public abstract Node<T> getPrevious();

    /**
     * Sets the previous reference on doubly nodes
     * @param previous previous reference
     */
    public abstract void setPrevious(Node<T> previous);

    /**
     * Gets the next node on both singly and doubly node
     * @return next node
     */
    public abstract Node<T> getNext();

    /**
     * Sets the next reference on both singly and doubly node
     * @param next next reference
     */
    public abstract void setNext(Node<T> next);
}
