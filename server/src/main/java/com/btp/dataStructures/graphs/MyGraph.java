package com.btp.dataStructures.graphs;

import com.btp.dataStructures.lists.SinglyList;

import java.util.PriorityQueue;

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
    public void add(T source, T destination, int cost) {
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

    /**
     * Performs a breadth-first search
     * starting at 'root' node (First created vertex)
     * @return true is connected, false is not connected or empty
     */
    public boolean BreadthFirstSearch() {
          if (vertices.getHead() == null) {
              return false;
          }
          clearStates();

          Vertex<T> root = vertices.get(0).getData();
          if (root == null) {
              return false;
          }

          // TODO: make sure this created list works as a queue. Must be implemented as queue.
          SinglyList<Vertex<T>> queue = new SinglyList<>();
          queue.add(root);
          root.setState(State.COMPLETE);

          while (!(queue.getHead() == null)) {
              root = queue.getHead().getData(); // used in place of queue.Peek(), as this way we get the head of the queue without deleting it, just as Peak works.
              for (int i = 0; i < root.getOutgoing().getLength(); i++) {
                  Vertex<T> each = root.getOutgoing().get(i).getData();
                  if (each.getState() == State.UNVISITED) {
                      each.setState(State.COMPLETE);
                      queue.add(each);
                  }
              }
              queue.remove(0); // on a regular Queue interface, this would return the element before removing it, but in this implementation the return value is not used, therefore removing it works just fine
          }
          return isConnected();
    }

    /**
     * Performs breadth-first search on a given starting vertex
     * @param value type T value held on the starting vertex
     * @return true if connected, false if not or if empty
     */
    public boolean BreadthFirstSearch(T value) {
        if (vertices.getHead() == null) {
            return false;
        }
        clearStates();

        Vertex<T> root = findVertex(value);
        if (root == null) {
            return false;
        }

        // TODO: just as method above, confirm this list works as a Queue-implementing structure.
        SinglyList<Vertex<T>> queue = new SinglyList<>();
        queue.add(root);
        root.setState(State.COMPLETE);

        while (!(queue.getHead() == null)) {
            root = queue.getHead().getData(); // again used in place of queue.peak()
            for (int i = 0; i < root.getOutgoing().getLength(); i++) {
                Vertex<T> each = root.getOutgoing().get(i).getData();
                if (each.getState() == State.UNVISITED) {
                    each.setState(State.COMPLETE);
                    queue.add(each);
                }
            }
            queue.remove(0);
        }
        return isConnected();
    }

    /**
     * Creates path information on the graph using Dijkstra's algorithm.
     * places the information into the vertices, based on the given starting one.
     * @param value type T value held in the starting vertex.
     * @return true if successful, false if empty or not found
     */
    private boolean Dijkstra(T value) {
        if (vertices.getHead() == null) {
            return false;
        }

        // reset all the distances and the previous values.
        resetDistances();

        // make sure it is valid
        Vertex<T> source = findVertex(value);
        if (source == null) {
            return false;
        }

        // set to 0 and add to heap
        source.setMinDistance(0);
        PriorityQueue<Vertex<T>> pq = new PriorityQueue<>();
        pq.add(source);

        while (!pq.isEmpty()) {
            // pull off top of queue, based on priority
            Vertex<T> u = pq.poll();
            //loop through adjacent vertices
            for (int i = 0; i < u.getOutgoing().getLength(); i++) {
                Vertex<T> temp = u.getOutgoing().get(i).getData();
                //get the edge
                Edge<T> edge = findEdge(u, temp);
                if (edge == null) {
                    return false;
                }
                // add cost to current
                int totalDistance = u.getMinDistance() + edge.getCost();
                if (totalDistance < temp.getMinDistance()) {
                    // new cost is lower, set it and add to queue
                    pq.remove(temp);
                    temp.setMinDistance(totalDistance);
                    // link the vertex
                    temp.setPrevious(u);
                    pq.add(temp);
                }
            }
        }
        return true;
    }


    /**
     * used in Dijkstra's, resets all the path tree fields
     */
    private void resetDistances() {
        for (int i = 0; i < vertices.getLength(); i++) {
            vertices.get(i).getData().setMinDistance(Integer.MAX_VALUE);
            vertices.get(i).getData().setPrevious(null);
        }
    }
    //TODO add BreadthFirstSearch, and Dijkstra main and helper methods
}
