package com.btp.dataStructures.graphs;

import com.btp.dataStructures.lists.SinglyList;

public class MyGraph<T extends Comparable<T>> {
    private SinglyList<Vertex<T>> vertices;
    private SinglyList<Edge<T>> edges;

    public MyGraph() {
        vertices = new SinglyList<>();
        edges = new SinglyList<>();
    }

    public SinglyList<Edge<T>> getEdges() {
        return this.edges;
    }

    public SinglyList<Vertex<T>> getVertices() {
        return this.vertices;
    }

    /**
     * creates edge from two T values directed from source to dest
     * @param source the value to put in vertex source
     * @param destination value to put in vertex destination
     * @param cost to set the weight/cost of the edge
     */
    public void add(T source, T destination, double cost) {
        Edge<T> temp = findEdge(source, destination);
        if (temp != null) {
            // Don't allow multiple edges, update cost.
            System.out.println("Edge " + source + ',' + destination + " already exists. Changing cost to given value");
            temp.setCost(cost);
        }
        else {
            // this will also create the vertices
            Edge<T> edge = new Edge<>(source, destination, cost,this);
            edges.add(edge);
        }
    }

    /**
     * find vertex with a value
     * TODO document all modifications in methods, access scopes and logic, as well as original reference.
     */
      Vertex<T> findVertex(T value) {
        for (int i = 0; i < vertices.getLength(); i++) {
            boolean compared = vertices.get(i).getData().getValue().compareTo(value) == 0;
            if (compared){
                return vertices.get(i).getData();
            }
        }
        return null;
    }

    Edge<T> findEdge(Vertex<T> v1, Vertex<T> v2) {
        for (int i = 0; i < edges.getLength(); i++) {
            if (edges.get(i).getData().getFrom().equals(v1) && edges.get(i).getData().getTo().equals(v2)) {
                return edges.get(i).getData();
            }
        }
        return null;
    }

    Edge<T> findEdge(T from, T to) {
        for (int i = 0; i < edges.getLength(); i++) {
            if (edges.get(i).getData().getFrom().getValue().equals(from) && edges.get(i).getData().getTo().getValue().equals(to)) {
                return edges.get(i).getData();
            }
        }
        return null;
    }

    private void clearStates() {
        for (int i = 0; i < vertices.getLength(); i++) {
            vertices.get(i).getData().setState(State.UNVISITED);
        }
    }

    public boolean isConnected() {
        for (int i = 0; i < vertices.getLength(); i++) {
            if (vertices.get(i).getData().getState() != State.COMPLETE) {
                return false;
            }
        }
        return true;
    }

    public boolean DepthFirstSearch() {
        if (vertices.getHead() == null) {
            return false;
        }
        clearStates();
        // get first node
        Vertex<T> root = vertices.get(0).getData();
        if (root == null) {
            return false;
        }
        // call recursive function
        DepthFirstSearch(root);
        return isConnected();
    }

    private void DepthFirstSearch(Vertex<T> vertex) {
        vertex.setState(State.VISITED);

        // loop through neighbours
        for (int i = 0; i < vertex.getOutgoing().getLength(); i++) {
            if (vertex.getOutgoing().get(i).getData().getState() == State.UNVISITED) {
                DepthFirstSearch(vertex.getOutgoing().get(i).getData());
            }
        }
        vertex.setState(State.COMPLETE);
    }

    //TODO add BreadthFirstSearch, and Dijkstra main and helper methods
}
