package com.btp.dataStructures.lists;

import com.btp.dataStructures.graphs.Vertex;
import com.btp.dataStructures.nodes.PQNode;
import com.btp.dataStructures.nodes.SinglyNode;

import java.util.Comparator;

public class PriorityQueue<T> {
    private PQNode<T> head;
    private int length;
    private Comparator<T> comparator;

    public PriorityQueue(Comparator<T> customComparator) {
        this.head = null;
        this.length = 0;
        this.comparator = customComparator;
    }

    public void add(T value) {
        //TODO: insert elements in correct position based on priority
        PQNode<T> tmp = new PQNode<>(value);
        if (this.head == null) {
            this.head = tmp;
        }
        else {
            tmp = this.head;
            while(tmp.getNext() != null) {
                int compare = comparator.compare(value, tmp.getData());
                if (compare > 0) {
                    // value has higher priority than tmp's value

                }
                else if (compare < 0) {
                    // value has smaller priority tham tmp's value
                }
                tmp = (PQNode<T>) tmp.getNext();
            }
        }
        length++;
    }

    public T poll() {
        PQNode<T> tmp = head;
        head = (PQNode<T>) head.getNext();
        length--;
        return tmp.getData();
    }

    public T peek() {
        return head.getData();
    }

    public boolean isEmpty() {
        return length == 0 || head == null;
    }

    public boolean isSorted() {
        PQNode<T> tmp = head;
        while (tmp.getNext() != null) {
            if (comparator.compare(tmp.getData(), tmp.getNext().getData()) > 0) {
                tmp = (PQNode<T>) tmp.getNext();
            }
            else {
                return false;
            }
        }
        return true;
    }


}