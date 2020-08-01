package com.btp.dataStructures.graphs;

public class Edge<T extends Comparable<T>> {
    private Vertex<T> from;
    private Vertex<T> to;
    private double cost;

    /**
     * @param v1 value of type T for 'from' vertex
     * @param v2 value of type T for 'to' vertex
     * @param cost double value for cost/weight of edge
     */
    public Edge(T v1, T v2, double cost)
    {
        //TODO highly modify method. As of now, it uses package-private scope variables that were changed to private scope.
        from = findVertex(v1);
        if (from == null)
        {
            from = new Vertex(v1);
            vertices.add(from);
        }
        to = findVertex(v2);
        if (to == null)
        {
            to = new Vertex(v2);
            vertices.add(to);
        }
        this.cost = cost;

        from.addOutgoing(to);
        to.addIncoming(from);
    }
    public void setFrom(Vertex<T> from) {
        this.from = from;
    }

    public Vertex<T> getFrom() {
        return this.from;
    }

    public void setTo(Vertex<T> to) {
        this.to = to;
    }

    public Vertex<T> getTo(){
        return this.to;
    }

    public void setCost(double newCost) {
        this.cost = newCost;
    }

    public double getCost() {
        return this.cost;
    }

    /**
     * @return Edge as string
     */
    @Override
    public String toString() {
        return "Edge From: " + from.getValue() + " to: " + to.getValue() + " cost: " + cost;
    }
}
