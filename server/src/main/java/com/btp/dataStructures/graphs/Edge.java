package com.btp.dataStructures.graphs;

public class Edge<T> {
    private Vertex<T> source;
    private Vertex<T> destination;
    private double weight;

    public Edge(Vertex<T> from, Vertex<T> to, double weight) {
        this.source = from;
        this.destination = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{source= " + source.toString() + ", destination= " + destination.toString() + ", weight= " + weight + '}';
    }
}
