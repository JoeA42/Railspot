package com.btp.dataStructures.trees.GenericTrees;

import com.btp.dataStructures.nodes.GenericNodes.SplayNode;

/**
 * the public class for the SplayTree instances. This code is based in the tutorial found in
 * https://www.sanfoundry.com/java-program-implement-splay-tree/, with major syntax modifications
 * for using generic type nodes and getter/setter methods for data-scope reduction.
 * @param <T> generic type of objects to possibly contain in the tree instance
 */
public class SplayTree<T extends Comparable<T>> {
    private SplayNode<T> root;
    int values = 0;

    /**
     * Constructor for the class
     */
    public SplayTree() {
        this.root = null;
    }

    /**
     * Checks if the tree is empty
     * @return true if the tree is empty, false if not
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Calls the recursive method insert
     *
     * @param element the element to be inserted
     */
    public void insert(T element) {
        SplayNode<T> current = root;
        SplayNode<T> parent = null;

        while (current != null){
            parent = current;
            int x = element.compareTo(parent.getElement());
            if (x > 0){
                current = current.getRight();
            }
            else{
                current = current.getLeft();
            }
        }
        current = new SplayNode(0);
        current.setElement(element);
        current.setParent(parent);
        int x = element.compareTo(parent.getElement());
        if (parent == null) {
            root = current;
        }
        else if (x > 0){
            parent.setRight(current);
        }
        else {
            parent.setLeft(current);
        }
        Splay(current);
        values++;
    }

    /**
     * Makes a zig rotation of the SplayTree data structure
     * @param child child to be rotated
     * @param parent parent to be rotated
     */
    public void zigRotation(SplayNode<T> child, SplayNode<T> parent) {
        if ((child == null) || (parent == null) || (parent.getLeft() != child) || (child.getParent() != parent)){
            throw new RuntimeException("wrong rotational operation for this value");
        }

        if (parent.getParent() != null) {
            if (parent == parent.getParent().getLeft()){
                parent.getParent().setLeft(child);
            }
            else{
                parent.getParent().setRight(child);
            }
        }

        if (child.getRight() != null){
            child.getRight().setParent(parent);
        }

        child.setParent(parent.getParent());
        parent.setParent(child);
        parent.setLeft(child.getRight());
        child.setRight(parent);
        //zig zag memory go brrr
        //TODO: actually understand what is going on
    }

    /**
     * Makes a zag rotation of the SplayTree data structure
     * @param child child to be rotated
     * @param parent parent to be rotated
     */
    public void zagRotation(SplayNode<T> child, SplayNode<T> parent) {
        if ((child == null) || (parent == null) || (parent.getRight() != child) || (child.getParent() != parent)){
            throw new RuntimeException("wrong rotational operation for this value");
        }
        if (parent.getParent() != null) {
            if (parent == parent.getParent().getLeft()) {
                parent.getParent().setLeft(child);
            }
            else {
                parent.getParent().setRight(child);
            }
        }
        if (child.getLeft() != null) {
            child.getLeft().setParent(parent);
        }

        child.setParent(parent.getParent());
        parent.setParent(child);
        parent.setRight(child.getLeft());
        child.setLeft(parent);
    }

    /**
     * Sets a node to its corresponding position
     * @param x node to be rotated
     */
    private void Splay(SplayNode<T> x) {
        while (x.getParent() != null) {
            SplayNode<T> parent = x.getParent();
            SplayNode<T> grandParent = parent.getParent();
            if (grandParent == null) {
                if (x == parent.getLeft()) {
                    zigRotation(x, parent);
                } else {
                    zagRotation(x, parent);
                }
            } else {
                if (x == parent.getLeft()) {
                    if (parent == grandParent.getLeft()) {
                        zigRotation(parent, grandParent);
                        zigRotation(x, parent);
                    }
                    else {
                        zigRotation(x, x.getParent());
                        zagRotation(x, x.getParent());
                    }
                }
                else {
                    if (parent == grandParent.getLeft()){
                        zagRotation(x, x.getParent());
                        zigRotation(x, x.getParent());
                    }
                    else {
                        zagRotation(parent, grandParent);
                        zagRotation(x, parent);
                    }
                }
            }
        }
        root = x;
    }

    /**
     * Removes a node with an specified element
     *
     * @param element the element to be removed
     */
    public void remove(T element) {
        SplayNode<T> temp = findNode(element);
        remove(temp);
    }

    /**
     * Removes a node
     * @param toRemove node to be removed
     */
    private void remove(SplayNode<T> toRemove) {
        if (toRemove == null) {
            return;
        }
        Splay(toRemove);

        if ((toRemove.getLeft() != null) && (toRemove.getRight() != null)) {
            SplayNode<T> min = toRemove.getLeft();
            while (min.getRight() != null) {
                min = min.getRight();
            }

            min.setRight(toRemove.getRight());
            toRemove.getRight().setParent(min);
            toRemove.getLeft().setParent(null);
            root = toRemove.getLeft();
        }
        else if (toRemove.getRight() != null) {
            toRemove.getRight().setParent(null);
            root = toRemove.getRight();
        }
        else if (toRemove.getLeft() != null) {
            toRemove.getLeft().setParent(null);
            root = toRemove.getLeft();
        }
        else {
            root = null;
        }

        toRemove.setParent(null);
        toRemove.setLeft(null);
        toRemove.setRight(null);
        toRemove.setElement(null);
        values--;
    }

    /**
     * method to get the amount of nodes in the tree
     * @return integer value for amount of nodes.
     */
    public int countNodes() {
        return values;
    }

    /**
     * method to search for an element in the tree
     * @param element the value that is to be found
     * @return boolean value, true if the value is in tree, false otherwise.
     */
    public boolean contains(T element){
        return findNode(element) != null;
    }

    /**
     * private method to find a node with the element it contains
     * @param element the value contained by the node to be found, when the search method invokes this method
     * @return a node, if the value exists in the tree, null if it was not found
     */
    private SplayNode<T> findNode(T element) {
        SplayNode<T> previous = null;
        SplayNode<T> current = root;
        while (current != null) {
            previous = current;
            int x = element.compareTo(current.getElement());
            if (x > 0) {
                current = current.getRight();
            }
            else if (x < 0) {
                current = current.getLeft();
            }
            else {
                Splay(current);
                return current;
            }
        }
        if (previous != null) {
            Splay(previous);
            return null;
        }
        return null;
    }

    /**
     * in order traversal printing method
     * this method is used to print the tree in the following order: left subtree, root and right subtree.
     */
    public void inOrder() {
        inorder(this.root);
    }

    /**
     * private inorder traversal method
     * @param root the root object of the SplayTree instance
     */
    private void inorder(SplayNode<T> root) {
        if (root != null) {
            inorder(root.getLeft());
            System.out.println(root.getElement() + " ");
            inorder(root.getRight());
        }
        else {
            System.out.println("root is empty");
        }
    }

    /**
     * public preorder traversal method
     * this method is used to print the tree in the following order: the root, the left subtree, the right subtree
     */
    public void preorder() {
        preorder(this.root);
    }

    /**
     * private preorder traversal method
     * @param root root object of the SplayTree instance
     */
    private void preorder(SplayNode<T> root) {
        if (root != null) {
            System.out.println(root.getElement() + " ");
            preorder(root.getLeft());
            preorder(root.getRight());
        }
        else {
            System.out.println("root is empty");
        }
    }

    /**
     * public postorder traversal method
     * this method is used to print the tree in the following order: left subtree, right subtree, root
     */
    public void postorder() {
        postorder(this.root);
    }

    /**
     * private preorder traversal method
     * @param root the root of the SplayTree Instance
     */
    private void postorder(SplayNode<T> root) {
        if (root != null) {
            postorder(root.getLeft());
            postorder(root.getRight());
            System.out.println(root.getElement() + " ");
        }
        else {
            System.out.println("root is empty");
        }
    }
}
