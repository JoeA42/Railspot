package com.btp.dataStructures.trees;

import com.btp.dataStructures.nodes.AVLNode;

/**
 * the public class for the SplayTree instances. This code is based in the tutorial found in
 * https://www.geeksforgeeks.org/avl-tree-set-1-insertion/, with major syntax modifications
 * for using generic type nodes and getter/setter methods for data-scope reduction.
 * @param <T> generic type of objects to possibly contain in the tree instance
 */
public class AVLTree<T extends Comparable<T>>{
    protected AVLNode<T> root;

    /**
     * Constructor for the class
     */
    public AVLTree(){this.root = null;}

    /**
     * Checks if the tree is empty
     * @return boolean true if is empty, false if not
     */
    public boolean isEmpty(){
        return this.root == null;
    }

    /**
     * Checks if an element is inside the tree. Calls the private constructor method
     * @param element The element to be checked
     * @return boolean true if the element is contained, false if not
     */
    public boolean contains(T element){
        return this.contains(element, this.root);
    }

    /**
     * Checks if an element is inside the tree. Calls itself recursively
     * @param element The element to be checked
     * @param node The current node is checking
     * @return boolean true if the element is contained, false if not
     */
    private boolean contains(T element, AVLNode<T> node){
        if (node == null){
            return false;
        } else {
            int compareValue = element.compareTo(node.getElement());

            if (compareValue < 0){
                return contains(element, node.getLeft());
            } else if (compareValue > 0){
                return contains(element, node.getRight());
            } else {
                return true;
            }
        }
    }

    /**
     * Finds the minimum element in the tree.
     * Calls the recursive method findMin
     *
     * @return the smallest element found
     */
    public T findMin() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.findMin(this.root).getElement();
        }
    }

    /**
     * Recursive method that finds the minor element in the tree
     *
     * @param node The node on which the method starts checking
     * @return The found min element
     */
    private AVLNode<T> findMin(AVLNode<T> node){
        if (node != null){
            while (node.getLeft() != null){
                node = node.getRight();
            }
        }
        return node;
    }

    /**
     * Finds the maximum element in the tree.
     * Calls the private method findMax
     *
     * @return the biggest element found
     */
    public T findMax() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.findMax(this.root).getElement();
        }
    }

    /**
     * Method that finds the mayor element in the tree
     *
     * @param node The node on which the method starts checking
     * @return The found max element
     */
    private AVLNode<T> findMax(AVLNode<T> node) {
        if (node != null) {
            while (node.getRight() != null) {
                node = node.getRight();
            }
        }
        return node;
    }

    /**
     * Method that finds the height of a node
     * @param N Node to get its height
     * @return int height of the Node
     */
    private int height(AVLNode<T> N){
        if (N == null){
            return 0;
        }
        return N.getHeight();
    }

    /**
     * Gets the max value of two integers
     * @param a int to be compared
     * @param b int to be compared
     * @return int with the max value
     */
    private int max(int a, int b){
        return Math.max(a, b);
    }

    /**
     * Makes a right Simple Rotation of the AVLTree data structure
     * @param y node to be rotated around
     * @return new node in that position after rotation
     */
    private AVLNode<T> rightRotate(AVLNode<T> y){
        AVLNode<T> x = y.getLeft();
        AVLNode<T> T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);

        return x;
    }

    /**
     * Makes a left Simple Rotation of the AVLTree data structure
     * @param x node to be rotated around
     * @return new node in that position after rotation
     */
    private AVLNode<T> leftRotate(AVLNode<T> x){
        AVLNode<T> y = x.getRight();
        AVLNode<T> T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);

        return y;
    }

    /**
     * Gets the balance of a node
     * @param Node Node to check the balance of
     * @return the balance of the node
     */
    private int getBalance(AVLNode<T> Node){
        if (Node == null){
            return 0;
        }

        return height(Node.getLeft()) - height(Node.getRight());
    }

    /**
     * Calls the recursive method insert
     *
     * @param element the element to be inserted
     */
    public void insert(T element) {
        this.root = this.insert(element, this.root);
    }

    /**
     * Recursive method that inserts a new node in the tree
     *
     * @param element the element to be inserted
     * @param current the current node being compared
     * @return the new TreeNode to be inserted
     */
    public AVLNode<T> insert(T element, AVLNode<T> current){
        if (current == null){
            return new AVLNode<>(element);
        }

        int compareValue = element.compareTo(current.getElement());

        if (compareValue < 0){
            current.setLeft(this.insert(element, current.getLeft()));
        } else if (compareValue > 0) {
            current.setRight(this.insert(element, current.getRight()));
        }

        current.setHeight(1 + max(height(current.getLeft()), height(current.getRight())));

        int balance = getBalance(current);

        // Left Left Case
        if (balance > 1 && element.compareTo(current.getLeft().getElement()) < 0){
            return leftRotate(current);
        }

        // Right Right Case
        if (balance < -1 && element.compareTo(current.getRight().getElement()) > 0){
            return rightRotate(current);
        }

        // Left Right Case
        if (balance > 1 && element.compareTo(current.getRight().getElement()) > 0) {
            current.setLeft(leftRotate(current.getLeft()));
            return rightRotate(current);
        }

        // Right Left Case
        if (balance < -1 && element.compareTo(current.getLeft().getElement()) < 0){
            current.setRight(rightRotate(current.getRight()));
            return leftRotate(current);
        }

        return current;
    }

    /**
     * Prints the tree using preOrder
     * @param node Current Node to be printed
     */
    public void preOrder(AVLNode<T> node) {
        if (node != null) {
            System.out.print(node.getElement() + " ");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    /**
     * Calls the recursive method remove
     *
     * @param element the element to be removed
     */
    public void delete(T element){
        this.root = this.delete(element, this.root);
    }

    /**
     * Recursive method that removes a certain node
     * @param element the element that will be searched and deleted
     * @param current the current node being compared
     * @return The node that was checked to be processed recursively
     */
    private AVLNode<T> delete(T element, AVLNode<T> current) {

        if (current == null)
            return null;

        int compareValue = element.compareTo(current.getElement());

        if (compareValue < 0){
            current.setLeft(this.delete(element, current.getLeft()));
        } else if (compareValue > 0) {
            current.setRight(this.delete(element, current.getRight()));
        } else {
            if ((current.getLeft() == null) || (current.getRight() == null))
            {
                AVLNode<T> tmp;
                if (null == current.getLeft()) {
                    tmp = current.getRight();
                } else {
                    tmp = current.getLeft();
                }

                current = tmp;
            }
            else
            {
                AVLNode<T> tmp = findMin(current.getRight());

                current.setElement(tmp.getElement());

                current.setRight(delete(tmp.getElement(), current.getRight()));
            }
        }
        if (current == null)
            return null;

        current.setHeight(max(height(current.getLeft()), height(current.getRight())) + 1);

        int balance = getBalance(current);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(current.getLeft()) >= 0)
            return rightRotate(current);

        // Left Right Case
        if (balance > 1 && getBalance(current.getLeft()) < 0)
        {
            current.setLeft(leftRotate(current.getLeft()));
            return rightRotate(current);
        }

        // Right Right Case
        if (balance < -1 && getBalance(current.getRight()) <= 0)
            return leftRotate(current);

        // Right Left Case
        if (balance < -1 && getBalance(current.getRight()) > 0)
        {
            current.setRight( rightRotate(current.getRight()));
            return leftRotate(current);
        }

        return current;
    }
}
