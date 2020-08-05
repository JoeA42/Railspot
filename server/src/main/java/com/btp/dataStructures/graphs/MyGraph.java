package com.btp.dataStructures.graphs;

import com.btp.dataStructures.lists.SinglyList;

import java.util.PriorityQueue;

/**
 * Class representation of a Weighted Directed Graph.
 * inherits from comparable to allow generic type implementation.
 * @author /u/Philboyd_Studge for r/javaexamples, retrieved original version from https://gist.github.com/snarkbait/9ff6fffe423b220c8890
 * AleQuesada2012 (for modified version, using own-implemented linked lists, no internal classes and private attribute access methods).
 */
public class MyGraph<T extends Comparable<T>> {
    private SinglyList<Vertex<T>> vertices;
    private SinglyList<Edge<T>> edges;

    /**
     * default graph constructor
     * instantiates the vertex and edge containing lists.
     */
    public MyGraph() {
        vertices = new SinglyList<>();
        edges = new SinglyList<>();
    }

    /**
     * get method for Edges list
     * @return SinglyList (linked list) of all graph edges.
     */
    public SinglyList<Edge<T>> getEdges() {
        return this.edges;
    }

    /**
     * get method for Vertices list
     * @return SinglyList (linked list) of all graph vertices.
     */
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
     * find a vertex with a given value
     * @param value T type value expected to be held in a vertex
     * @return a Vertex object if found, null if no vertex held the value in the parameter.
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

    /**
     * find an edge with the source and destination vertices
     * @param v1 the Vertex representing the source of the edge
     * @param v2 the Vertex representing the destination of the edge
     * @return an Edge type object if found, null if no edge matched the specified route
     */
    Edge<T> findEdge(Vertex<T> v1, Vertex<T> v2) {
        for (int i = 0; i < edges.getLength(); i++) {
            if (edges.get(i).getData().getFrom().equals(v1) && edges.get(i).getData().getTo().equals(v2)) {
                return edges.get(i).getData();
            }
        }
        return null;
    }

    /**
     * find an edge with the source and destination values
     * @param from the value to be found in a vertex which should represent the source.
     * @param to the value to be found in a vertex which should represent the destination.
     * @return an Edge type object if found, null if no edge matched the specified parameters.
     */
    Edge<T> findEdge(T from, T to) {
        for (int i = 0; i < edges.getLength(); i++) {
            if (edges.get(i).getData().getFrom().getValue().equals(from) && edges.get(i).getData().getTo().getValue().equals(to)) {
                return edges.get(i).getData();
            }
        }
        return null;
    }

    /**
     * reset all the vertex states to Unvisited using the State enum.
     */
    private void clearStates() {
        for (int i = 0; i < vertices.getLength(); i++) {
            vertices.get(i).getData().setState(State.UNVISITED);
        }
    }

    /**
     * checks if the graph is connected by accessing each vertex's state
     * @return true if the graph is connected, false otherwise.
     */
    public boolean isConnected() {
        for (int i = 0; i < vertices.getLength(); i++) {
            if (vertices.get(i).getData().getState() != State.COMPLETE) {
                return false;
            }
        }
        return true;
    }

    /**
     * performs a recursive depth-first search on the
     * 'root' node (the first vertex created)
     * @return true if connected, false if empty or not connected.
     */
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

    /**
     * auxiliary method for depth first search
     * @param vertex the starting vertex for the search
     */
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
        //TODO: make own priority queue that works with Vertex class
        PriorityQueue<Vertex<T>> pq = new PriorityQueue<>(new VertexComparator<T>());
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
     * obtain the shortest path to a specified vertex
     * @param target Vertex representing the desired destination
     * @return a linked list of strings containing the path.
     */
    private SinglyList<String> getShortestPath(Vertex<T> target) {
        SinglyList<String> path = new SinglyList<>();

        // check for no path found
        if (target.getMinDistance() == Integer.MAX_VALUE) {
            path.add("No path found");
            return path;
        }

        // loop through the vertices from end target
        for (Vertex<T> v = target; v != null; v = v.getPrevious()) {
            path.add(v.getValue() + " : cost: " + v.getMinDistance());
        }

        // invert the list and return it
        return path.flip();
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

    /**
     * main method for regular usage of Dijkstras algorithm and private functions
     * calls the Dijkstra method to build the path tree for the given
     * starting vertex, then calls getShortestPath method to return
     * a list that contains all the steps in the shortest path to the destination vertex.
     * @param from value of type T for source vertex.
     * @param to value of type T for destination vertex.
     * @return string linked list of the steps in the shortest path
     */
    public SinglyList<String> getPath(T from, T to) {
        boolean test = Dijkstra(from);
        if (!test) {
            return null;
        }
        return getShortestPath(findVertex(to));
    }

    /**
     *
     * @return string with all the vertices
     */
    @Override
    public String toString() {
        String retval = "";
        for (int i = 0; i < vertices.getLength(); i++) {
            retval += vertices.get(i).getData().toString() + "\n";
        }
        return retval;
    }

    /**
     *
     * @return string with all the edges
     */
    public String edgesToString() {
        String retval = "";
        for (int i = 0; i < edges.getLength(); i++) {
            retval += edges.get(i).getData().toString() + "\n";
        }
        return retval;
    }
}
