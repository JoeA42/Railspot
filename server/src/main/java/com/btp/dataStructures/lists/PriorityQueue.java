package com.btp.dataStructures.lists;

import com.btp.dataStructures.nodes.PQNode;

import java.util.Comparator;

/**
 * Own class representation of a priority queue
 * @param <T> type to implement the queue as the data held in its nodes
 */
public class PriorityQueue<T> {
    private PQNode<T> head;
    private int length;
    private Comparator<T> comparator;

    /**
     * public default constructor for PriorityQueue instances.
     * @param customComparator an object from a class that implements a Comparator, and its method compare.
     */
    public PriorityQueue(Comparator<T> customComparator) {
        this.head = null;
        this.length = 0;
        this.comparator = customComparator;
    }

    /**
     * method used to place new elements in the priority queue
     * @param value T type value to insert in the queue
     */
    public void add(T value) {
        //TODO: insert elements in correct position based on priority
        PQNode<T> tmp = new PQNode<>(value);
        if (this.head == null) {
            this.head = tmp;
        }
        else {
            PQNode<T> current = this.head;
            while(current.getNext() != null) {
                int compare = comparator.compare(value, current.getData());
                if (compare >= 0) {
                    // tmp still has higher priority, value is to be compared with next vertex/node
                    current = (PQNode<T>) current.getNext();
                }
                else{
                    // value has higher priority, must take tmp's place in the queue
                    current.getPrevious().setNext(tmp);
                    tmp.setNext(current);
                    tmp.setPrevious(current.getPrevious());
                    current.setPrevious(tmp);
                }
            }
        }
        length++;
    }

    /**
     * retrieves and removes the element in the head of the queue
     * @return T type value held in the head
     */
    public T poll() {
        T tmp = head.getData();
        this.head = (PQNode<T>) this.head.getNext();
        if (head != null) {
            this.head.setPrevious(null);
        }
        length--;
        return tmp;
    }

    /**
     * retrieves, without removing it, the element in the head of the queue
     * @return T type element contained in head
     */
    public T peek() {
        return head.getData();
    }

    /**
     * removes an element from the queue
     * @param value the value that is supposedly contained in a queue node
     */
    public void remove(T value) {
        PQNode<T> current = this.head;
        if (head == null) {
            return;
        }
        if (value == head.getData()) {
            this.head = (PQNode<T>) current.getNext();
            current.setPrevious(null);
        }
        while (current.getNext() != null) {
            if (value != current.getData()){
                current = (PQNode<T>) current.getNext();
            }
            else {
                current.getNext().setPrevious(current.getPrevious());
                current.getPrevious().setNext(current.getNext());
                current.setPrevious(null);
                current.setNext(null);
                length--;
            }
        }
    }

    /**
     * checks if the queue structure is empty
     * @return true if there is no element, false if at least one element is contained
     */
    public boolean isEmpty() {
        return length == 0 || head == null;
    }

    /**
     * iterate the queue structure to check if the priorities are sorted correctly (this case, uses ascending order)
     * @return true if the queue is ordered, false if some element breaks this order
     */
    public boolean isSorted() {
        PQNode<T> tmp = head;
        while (tmp.getNext() != null) {
            if (comparator.compare(tmp.getData(), tmp.getNext().getData()) < 0) {
                tmp = (PQNode<T>) tmp.getNext();
            }
            else {
                return false;
            }
        }
        return true;
    }


}