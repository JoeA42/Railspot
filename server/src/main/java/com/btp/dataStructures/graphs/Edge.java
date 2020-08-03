package com.btp.dataStructures.graphs;

public class Edge<T extends Comparable<T>> {
    private Vertex<T> from;
    private Vertex<T> to;
    private int cost;
    private MyGraph<T> graph;

    /**
     * @param v1 value of type T for 'from' vertex
     * @param v2 value of type T for 'to' vertex
     * @param cost double value for cost/weight of edge
     * @param graph reference to the graph which the edges belong to.
     */
    public Edge(T v1, T v2, int cost, MyGraph<T> graph) {
        from = graph.findVertex(v1);
        if (from == null)
        {
            from = new Vertex<T>();
            from.setValue(v1);
            graph.getVertices().add(from);
        }
        to = graph.findVertex(v2);
        if (to == null)
        {
            to = new Vertex<T>();
            to.setValue(v2);
            graph.getVertices().add(to);
        }
        this.graph = graph;
        this.cost = cost;
        from.addOutgoing(to);
        to.addIncoming(from);
    }

    /**
     * set method for source vertex
     * @param from vertex object as origin of the edge
     */
    public void setFrom(Vertex<T> from) {
        this.from = from;
    }

    /**
     * get method for source vertex
     * @return Vertex object as origin of the edge
     */
    public Vertex<T> getFrom() {
        return this.from;
    }

    /**
     * set method for destination vertex
     * @param to vertex object as endpoint of the edge
     */
    public void setTo(Vertex<T> to) {
        this.to = to;
    }

    /**
     * get method for destination vertex
     * @return vertex object as endpoint of the edge
     */
    public Vertex<T> getTo(){
        return this.to;
    }

    /**
     * set method for edge cost
     * @param newCost integer value for the cost of the edge
     */
    public void setCost(int newCost) {
        this.cost = newCost;
    }

    /**
     * get method for edge cost
     * @return integer value for the cost of the edge
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * get method for the graph
     * @return graph instance to which the edge belongs
     */
    public MyGraph<T> getGraph() {
        return this.graph;
    }

    /**
     * @return Edge as string
     */
    @Override
    public String toString() {
        return "Edge From: " + from.getValue() + " to: " + to.getValue() + " cost: " + cost;
    }
}
