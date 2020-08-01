package com.btp.dataStructures.graphs;

import com.btp.dataStructures.lists.SinglyList;

/**
 * A java class representation of a graph vertex, using generic coding.
 * @param <T> type of element contained in the vertex.
 */
public class Vertex<T> {
    private T element;
    private SinglyList<Edge<T>> edges = new SinglyList<>();

    public Vertex() {
        this.element = null;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public T getElement() {
        return this.element;
    }

    public void addEdge(Edge<T> edge) {
        edges.add(edge);
    }

    public SinglyList<Edge<T>> getAdjList() {
        return this.edges;
    }
}
