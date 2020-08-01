package com.btp.dataStructures.graphs;

import com.btp.dataStructures.lists.SinglyList;

public class MyGraph<T> {
    private SinglyList<Vertex<T>> vertices;

    public MyGraph() {
        vertices = null;
    }

    public void addEdge(Vertex<T> source, Vertex<T> destination, double weight) {
        Edge<T> edge = new Edge<>(source, destination, weight);
        vertices.getIndexByData(source);
    }

    public SinglyList<Edge<T>> getAdjList (Vertex<T> vertex) {
        return vertices.getIndexByData(vertex).getAdj;
    }
}
