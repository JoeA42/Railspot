package com.btp.dataStructures.nodes;

import com.btp.serverData.clientObjects.Ticket;

/**
 * This class represents a node that forms part of the TicketTree
 */
public class TicketNode {
    private Ticket element;
    private TicketNode left;
    private TicketNode right;
    private int height;

    /**
     * The constructor of the class called externally. It calls the private constructor
     * @param element Ticket the ticket object contained in the node
     */
    public TicketNode(Ticket element){
        this(element, null, null);
    }

    /**
     * The actual constructor for the class
     * @param element Ticket the Ticket object contained in the node
     * @param left TicketNode the left child of the node
     * @param right TicketNode the right child of the node
     */
    private TicketNode(Ticket element, TicketNode left, TicketNode right){
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 1;
    }

    /**
     * Getter for the element attribute
     * @return Ticket the ticket the node is storing
     */
    public Ticket getElement() {
        return element;
    }

    /**
     * Setter for the element attribute
     * @param element Ticket the new ticket to be stored
     */
    public void setElement(Ticket element) {
        this.element = element;
    }

    /**
     * Getter for the left attribute
     * @return TicketNode pointed by the left pointer
     */
    public TicketNode getLeft() {
        return left;
    }

    /**
     * Setter for the left attribute
     * @param left TicketNode to be pointed by the left pointer
     */
    public void setLeft(TicketNode left) {
        this.left = left;
    }

    /**
     * Getter for the right attribute
     * @return TicketNode pointed by the right pointer
     */
    public TicketNode getRight() {
        return right;
    }

    /**
     * Setter for the right attribute
     * @param right TicketNode to be pointed by the right pointer
     */
    public void setRight(TicketNode right) {
        this.right = right;
    }

    /**
     * Getter for the height attribute
     * @return int the height of the node in the tree
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for the height attribute
     * @param height int the height of the node in the tree
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
