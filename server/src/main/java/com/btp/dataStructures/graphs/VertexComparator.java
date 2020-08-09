package com.btp.dataStructures.graphs;

import java.util.Comparator;

/**
 * Class used as a custom comparator for vertices in a priority queue.
 * @param <T> generic type t
 */
public class VertexComparator<T extends Comparable<T>> implements Comparator<Vertex<T>> {

    /**
     * overridden method that compares two vertices to obtain the highest priority.
     * priority is determined by minimum distance. Lowest minDist means highest priority.
     * @param o1 Vertex object to compare
     * @param o2 another Vertex object to compare
     * @return 1 if o1 has more priority, -1 if o2 has more, or 0 if they are equal when compared.
     */
    @Override
    public int compare(Vertex<T> o1, Vertex<T> o2) {
        if (o1.getMinDistance() > o2.getMinDistance()) {
            return 1;
        }
        else if (o1.getMinDistance() < o2.getMinDistance()) {
            return -1;
        }
        return 0;
    }
}
