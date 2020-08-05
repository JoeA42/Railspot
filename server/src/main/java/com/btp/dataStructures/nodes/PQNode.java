package com.btp.dataStructures.nodes;

public class PQNode<T> extends Node<T> {
    protected Node<T> next;
    private int priority;

    public PQNode(T data) {
        super(data);
    }

    @Override
    public void setData(T data) {
        super.setData(data);
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public Node<T> getPrevious() {
        return null;
    }

    @Override
    public void setPrevious(Node<T> previous) {
        System.out.println("pqnodes have no previous.");
    }

    @Override
    public Node<T> getNext() {
        return next;
    }

    @Override
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
