package com.btp.dataStructures.trees;

import com.btp.dataStructures.nodes.BusinessNode;
import com.btp.serverData.clientObjects.Business;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * the public class for the SplayTree instances. This code is based in the tutorial found in
 * https://www.sanfoundry.com/java-program-implement-splay-tree/, with major syntax modifications
 * for using generic type nodes and getter/setter methods for data-scope reduction.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessTree {
    private BusinessNode root;
    int values = 0;
    private ArrayList<String> businessList = new ArrayList<>();

    /**
     * Getter for the root element
     * @return root element
     */
    public BusinessNode getRoot() {
        return root;
    }

    /**
     * setter for the root element
     * @param root root element
     */
    public void setRoot(BusinessNode root) {
        this.root = root;
    }

    /**
     * Constructor for the class
     */
    public BusinessTree() {
        this.root = null;
    }

    /**
     * Checks if the tree is empty
     *
     * @return true if the tree is empty, false if not
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Checks if an element is inside the tree. Calls the private constructor method
     *
     * @param id The Business id to be checked
     * @return boolean true if the element is contained, false if not
     */
    public boolean checkById(int id) {
        return this.checkById(id, this.root);
    }

    /**
     * Checks if an element is inside the tree. Calls itself recursively
     *
     * @param id   The id to be checked
     * @param node The current node is checking
     * @return boolean true if the element is contained, false if not
     */
    private boolean checkById(int id, BusinessNode node) {
        if (node == null) {
            return false;
        } else {
            int compareValue = id - node.getElement().getId();

            if (compareValue < 0) {
                return checkById(id, node.getLeft());
            } else if (compareValue > 0) {
                return checkById(id, node.getRight());
            } else {
                return true;
            }
        }
    }

    /**
     * Gets a Business of the tree using its id. Calls the private getElementById method
     *
     * @param id the id of the Business to be searched
     * @return the Business of that respective id
     */
    public Business getElementById(int id) {
        return getElementById(id, this.root);
    }

    /**
     * Gets a Recipe of the tree using its id. Calls itself recursively
     *
     * @param id   the id of the user to be searched
     * @param node the current node is searching
     * @return the user of that respective id
     */
    private Business getElementById(int id, BusinessNode node) {
        if (node == null) {
            return null;
        }
        int compareValue = id - node.getElement().getId();

        if (compareValue < 0) {
            return getElementById(id, node.getLeft());
        } else if (compareValue > 0) {
            return getElementById(id, node.getRight());
        } else {
            return node.getElement();
        }
    }

    /**
     * Finds the minimum element in the tree.
     * Calls the recursive method findMin
     *
     * @return the smallest element found
     */
    public Business findMin() {
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
    private BusinessNode findMin(BusinessNode node) {
        if (node == null) {
            return null;
        } else if (node.getLeft() == null) {
            return node;
        } else {
            return findMin(node.getLeft());
        }
    }

    /**
     * Finds the maximum element in the tree.
     * Calls the private method findMax
     *
     * @return the biggest element found
     */
    public Business findMax() {
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
    private BusinessNode findMax(BusinessNode node) {
        if (node != null) {
            while (node.getRight() != null) {
                node = node.getRight();
            }
        }
        return node;
    }

    /**
     * Calls the recursive method insert
     *
     * @param element the element to be inserted
     */
    public void insert(Business element) {
        BusinessNode current = root;
        BusinessNode parent = null;

        while (current != null) {
            parent = current;
            int x = element.getId() - parent.getElement().getId();
            if (x > 0) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }
        current = new BusinessNode();
        current.setElement(element);
        current.setParent(parent);
        int x;
        if(parent!=null) {
             x = element.getId() - parent.getElement().getId();
        }
        else {
            x = element.getId();
        }
        if (parent == null) {
            root = current;
        } else if (x > 0) {
            parent.setRight(current);
        } else {
            parent.setLeft(current);
        }
        Splay(current);
        values++;
    }

    /**
     * Makes a zig rotation of the SplayTree data structure
     *
     * @param child  child to be rotated
     * @param parent parent to be rotated
     */
    public void zigRotation(BusinessNode child, BusinessNode parent) {
        if ((child == null) || (parent == null) || (parent.getLeft() != child) || (child.parent() != parent)) {
            throw new RuntimeException("wrong rotational operation for this value");
        }

        if (parent.parent() != null) {
            if (parent == parent.parent().getLeft()) {
                parent.parent().setLeft(child);
            } else {
                parent.parent().setRight(child);
            }
        }

        if (child.getRight() != null) {
            child.getRight().setParent(parent);
        }

        child.setParent(parent.parent());
        parent.setParent(child);
        parent.setLeft(child.getRight());
        child.setRight(parent);
    }

    /**
     * Makes a zag rotation of the SplayTree data structure
     *
     * @param child  child to be rotated
     * @param parent parent to be rotated
     */
    public void zagRotation(BusinessNode child, BusinessNode parent) {
        if ((child == null) || (parent == null) || (parent.getRight() != child) || (child.parent() != parent)) {
            throw new RuntimeException("wrong rotational operation for this value");
        }
        if (parent.parent() != null) {
            if (parent == parent.parent().getLeft()) {
                parent.parent().setLeft(child);
            } else {
                parent.parent().setRight(child);
            }
        }
        if (child.getLeft() != null) {
            child.getLeft().setParent(parent);
        }

        child.setParent(parent.parent());
        parent.setParent(child);
        parent.setRight(child.getLeft());
        child.setLeft(parent);
    }

    /**
     * Sets a node to its corresponding position
     *
     * @param x node to be rotated
     */
    private void Splay(BusinessNode x) {
        while (x.parent() != null) {
            BusinessNode parent = x.parent();
            BusinessNode grandParent = parent.parent();
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
                    } else {
                        zigRotation(x, x.parent());
                        zagRotation(x, x.parent());
                    }
                } else {
                    if (parent == grandParent.getLeft()) {
                        zagRotation(x, x.parent());
                        zigRotation(x, x.parent());
                    } else {
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
     * @param id the id of the element to be removed
     */
    public void remove(int id) {
        BusinessNode temp = getNodeById(id, this.root);
        remove(temp);
    }

    /**
     * Gets the specific node using an id
     * @param id int id to be searched
     * @param node BusinessNode current node is searching
     * @return BusinessNode node of that id
     */
    private BusinessNode getNodeById(int id, BusinessNode node){
        if (node == null) {
            return null;
        }
        int compareValue = id - node.getElement().getId();

        if (compareValue < 0) {
            return getNodeById(id, node.getLeft());
        } else if (compareValue > 0) {
            return getNodeById(id, node.getRight());
        } else {
            return node;
        }
    }

    /**
     * Removes a node
     *
     * @param toRemove node to be removed
     */
    private void remove(BusinessNode toRemove) {
        if (toRemove == null) {
            return;
        }
        Splay(toRemove);

        if ((toRemove.getLeft() != null) && (toRemove.getRight() != null)) {
            BusinessNode min = toRemove.getLeft();
            while (min.getRight() != null) {
                min = min.getRight();
            }

            min.setRight(toRemove.getRight());
            toRemove.getRight().setParent(min);
            toRemove.getLeft().setParent(null);
            root = toRemove.getLeft();
        } else if (toRemove.getRight() != null) {
            toRemove.getRight().setParent(null);
            root = toRemove.getRight();
        } else if (toRemove.getLeft() != null) {
            toRemove.getLeft().setParent(null);
            root = toRemove.getLeft();
        } else {
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
     *
     * @return integer value for amount of nodes.
     */
    public int countNodes() {
        return values;
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
     *
     * @param root root object of the SplayTree instance
     */
    private void preorder(BusinessNode root) {
        if (root != null) {
            System.out.println(root.getElement().getId() + " ");
            preorder(root.getLeft());
            preorder(root.getRight());
        } else {
            System.out.println("root is empty");
        }
    }

    /**
     * Searches using the name attribute. Calls the private method searchByName
     * @param data String data to be searched
     * @return ArrayList<String> All the businesses that match the search
     */
    public ArrayList<String> search(String data) {
        this.businessList.clear();
        return searchByName(data.toLowerCase(), this.root);
    }

    /**
     * Searches using the name attribute. Calls itself recursively
     * @param data String data to be searched
     * @param root BusinessNode current node is searching
     * @return ArrayList<String> All the businesses that match the search
     */
    private ArrayList<String> searchByName(String data, BusinessNode root){
        if (root != null && this.businessList.size() < 5) {
            Business business = root.getElement();
            if (business.getName().toLowerCase().contains(data)){
                this.businessList.add(business.getId()+";"+business.getName()+";business");
            }
            searchByName(data, root.getLeft());
            searchByName(data, root.getRight());
        }
        return this.businessList;
    }

    /**
     * Recommends business to a specific user. Calls the private method recommend
     * @param data String email of the user
     * @return ArrayList<String> All the businesses that the user hasn't rated
     */
    public ArrayList<String> recommend(String data){
        this.businessList.clear();
        return recommend(data, this.root);
    }

    /**
     * Recommends business to a specific user. Calls itself recursively
     * @param data String email of the user
     * @param root BusinessNode current node is searching
     * @return ArrayList<String> All the businesses that the user hasn't rated
     */
    private ArrayList<String> recommend(String data, BusinessNode root){
        if (root != null){
            Business business = root.getElement();
            boolean rated = false;
            for (String email: business.getFollowers()){
                if(email.contains(data)){
                    rated = true;
                    break;
                }
            }
            for (String email: business.getEmployeeList()){
                if (email.contains(data)){
                    rated = true;
                    break;
                }
            }
            if (!rated){
                this.businessList.add(business.getId()+";"+business.getName()+";business");
            }
            recommend(data, root.getLeft());
            recommend(data, root.getRight());
        }
        return businessList;
    }

    /**
     * Gets the five highest rated businesses. Calls the private method rating
     * @return ArrayList<String> of the five highest rated businesses
     */
    public ArrayList<String> rating(){
        this.businessList.clear();
        return rating(this.root);
    }

    /**
     * Gets the five highest rated businesses. Calls itself recursively
     * @param root BusinessNode current node is searching
     * @return ArrayList<String> of the five highest rated businesses
     */
    private ArrayList<String> rating(BusinessNode root){
        if (root != null){
            Business business = root.getElement();
            if (business.getRaters().size() != 0){
                businessList.add(business.getId()+";"+business.getName()+";business;"+business.getRating());
                for (String businessData:businessList){
                    String[] businessString = businessData.split(";");
                    if (Float.valueOf(businessString[3]) < business.getRating()){
                        int i = businessList.indexOf(businessData);
                        String tmp = businessList.get(i);
                        int j = businessList.indexOf(business.getId()+";"+business.getName()+";business;"+business.getRating());
                        businessList.set(i, business.getId()+";"+business.getName()+";business;"+business.getRating());
                        businessList.set(j, tmp);
                    }
                }
                if (businessList.size() > 5){
                    businessList.remove(5);
                }
            }
            rating(root.getLeft());
            rating(root.getRight());
        }
        return businessList;
    }
}
