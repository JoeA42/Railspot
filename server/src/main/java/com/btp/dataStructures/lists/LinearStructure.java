package com.btp.dataStructures.lists;

/**
 * Superclass made to add length to Lists and Stack
 */
public abstract class LinearStructure {

    protected int length;

    /**
     * Returns the length of the structure. Used on every structure.
     * @return returns an integer with the length attribute of the list
     */
    public int getLength() {
        return length;
    }
}
