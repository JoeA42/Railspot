package com.btp.dataStructures.lists;

import com.btp.dataStructures.nodes.Node;

/**
 * Superclass of linear data structures where each element can be accessed and manipulated freely.
 * @param <T> Data type on Nodes
 */
public abstract class LinkedList<T> extends LinearStructure {

    protected Node<T> head;

    /**
     * Clears contents on list and leaves it empty
     */
    public abstract void clear();

    /**
     * returns first node of list
     * @return first node
     */
    public abstract Node<T> getHead();

    /**
     * Returns last Node of List
     * @return last Node
     */
    public abstract Node<T> getLast();

    /**
     * Returns Node on given position at index
     * @param index where Node is located
     * @return Node indexed
     */
    public abstract Node<T> get(int index);

    /**
     * Adds a Node to the List
     * @param data info to be added on List
     */
    public abstract void add(T data);

    /**
     * Adds a Node to list on given index
     * @param data info to be added on List
     * @param index where the Node will be added
     */
    public abstract void add(T data, int index);

    /**
     * Remove a Node on the List on given index
     * @param index where Node to be removed is located
     */
    public abstract void remove(int index);

    /**
     * Traverses the list and prints the node value
     */
    public abstract void print();
}
