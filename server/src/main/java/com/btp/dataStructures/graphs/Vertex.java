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

    /**
     * Add vertex to adjacent outgoing list
     * @param vert Vertex of outgoing adjacent
     */
    public void addOutgoing(Vertex<T> vert) {
        outgoing.add(vert);
    }

    /**
     * set method for vertex element
     * @param value T type data to place in the vertex
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * get method for vertex element
     * @return T type element located in the vertex data
     */
    public T getValue() {
        return this.value;
    }

    /**
     * set method for the Vertex state
     * @param state State enum (COMPLETE, VISITED, UNVISITED)
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * get method for the vertex state
     * @return the enum element representing the vertex state
     */
    public State getState() {
        return this.state;
    }

    /**
     * get method for incoming adjacent vertices list
     * @return Vertex linked list containing the incoming vertices reference
     */
    public SinglyList<Vertex<T>> getIncoming() {
        return this.incoming;
    }

    /**
     * get method for outgoing adjacent vertices list
     * @return Vertex linked list containing the incoming vertices reference
     */
    public SinglyList<Vertex<T>> getOutgoing() {
        return this.outgoing;
    }

    /**
     * get method for the reported minimum distance to the vertex
     * @return minimum distance attribute, integer
     */
    public int getMinDistance() {
        return this.minDistance;
    }

    /**
     * get method for previous Vertex
     * @return Vertex object representing the last vertex visited that led to this vertex
     */
    public Vertex<T> getPrevious() {
        return this.previous;
    }

    /**
     * set method for the vertex minimum distance
     * @param minDistance integer value for minimum distance
     */
    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    /**
     * set method for previous vertex reference
     * @param previous Vertex type object to set as the previous
     */
    public void setPrevious(Vertex<T> previous) {
        this.previous = previous;
    }

    /**
     * Get string of Vertex with all it's ingoing and outgoing adjacent values
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
