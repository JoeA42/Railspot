package com.btp.dataStructures.nodes;

public class PQNode<T> extends Node<T> {
    protected Node<T> next;
    protected Node<T> previous;
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
        return previous;
    }

    @Override
    public void setPrevious(Node<T> previous) {
        System.out.println(this.previous = previous);
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
