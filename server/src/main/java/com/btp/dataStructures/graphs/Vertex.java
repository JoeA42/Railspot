package com.btp.dataStructures.graphs;
import com.btp.dataStructures.lists.SinglyList;
/**
 * A java class representation of a graph vertex, using generic coding.
 * @param <T> type of element contained in the vertex.
 */
public class Vertex<T extends Comparable<T>> {
    private T value;
    // variables for Dijkstra Tree
    private Vertex<T> previous = null;
    private int minDistance = Integer.MAX_VALUE;

    private SinglyList<Vertex<T>> incoming;
    private SinglyList<Vertex<T>> outgoing;
    private State state;

    /**
     * Creates new Vertex with value T
     */
    public Vertex() {
        this.value = null;
        incoming = new SinglyList<>();
        outgoing = new SinglyList<>();
        state = State.UNVISITED;
    }

    /**
     * Add Vertex to adjacent incoming list
     * @param vert Vertex of incoming adjacent
     */
    public void addIncoming(Vertex<T> vert) {
        incoming.add(vert);
    }

    public void addOutgoing(Vertex<T> vert) {
        outgoing.add(vert);
    }
    public void setValue(T value) {
        this.value = value;
    }
    public T getValue() {
        return this.value;
    }
    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public SinglyList<Vertex<T>> getIncoming() {
        return this.incoming;
    }

    public SinglyList<Vertex<T>> getOutgoing() {
        return this.outgoing;
    }


    /**
     * Get string of Vertex with all it's ingoing and outgoing adjacencies
     * @ return string
     */
    @Override
    public String toString() {
        String retval = "";
        retval += "Vertex: " + value + " : ";
        retval += " In: ";
        for (int i = 0; i < incoming.getLength(); i++) {
            retval += incoming.get(i).getData().getValue() + " ";
        }
        retval += "Out: ";
        for (int i = 0; i < outgoing.getLength(); i++) {
            retval += outgoing.get(i).getData().getValue() + " ";
        }
        return retval;
    }
}
