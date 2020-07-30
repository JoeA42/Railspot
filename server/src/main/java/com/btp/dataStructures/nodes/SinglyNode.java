package com.btp.dataStructures.nodes;

/**
 * Nodes with a reference of the Node that follows it
 * @param <T> Data type of the info stored on each Node
 */
public  class SinglyNode<T> extends Node<T> {

    protected Node<T> next;

    /**
     * Constructor that sets the data to the node
     * @param data data of generic type saved on new node
     */
    public SinglyNode(T data) {
        super(data);
    }

    /**
     * As a singly node, it doesn't have a previous reference. This was made so casting could be implemented when it
     * was uncertain which type of node would be set
     * @return null
     */
    @Override
    public Node<T> getPrevious() {
        System.out.println("SinglyNode doesn't have previous reference");
        return null;
    }

    /**
     * As a singly node, it doesn't have a previous reference. This was made so casting could be implemented when it
     * was uncertain which type of node would be set
     * @param previous doesn't set anything
     */
    @Override
    public void setPrevious(Node<T> previous) {
        System.out.println("SinglyNode doesn't have previous reference");
    }

    /**
     * Gets the node that it references as its next. Used on doubly node.
     * @return next node
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Sets a node and puts it after it, according to the list order. Used on doubly node.
     * @param next next reference
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

}
