package com.btp.serverData.repos;

import com.btp.dataStructures.trees.GenericTrees.BinarySearchTree;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.sql.SQLOutput;

public class tester {

    public static void main(String[] args) {
        BinarySearchTree<Integer> testTree = new BinarySearchTree<Integer>();

        testTree.insert(10);
        testTree.insert(7);
        testTree.insert(15);
        testTree.insert(4);
        testTree.insert(9);


        System.out.println("preorder");
        testTree.preorder();

        System.out.println("inorder");
        testTree.inorder();

        System.out.println("postorder");
        testTree.postorder();



    }
}
