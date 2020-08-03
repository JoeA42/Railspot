package com.btp.dataStructures.graphs;

import com.btp.dataStructures.lists.SinglyList;

import java.util.List;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        // create graph
        MyGraph<String> graph = new MyGraph<>();

        // add a bunch of edges
        graph.add("SACRAMENTO", "PHOENIX", 150);
        graph.add("PHOENIX", "SACRAMENTO", 135);
        graph.add("PHOENIX", "SLC", 120);
        graph.add("SLC", "SACRAMENTO", 175);
        graph.add("SACRAMENTO", "PHOENIX", 160); // edge already exists!!!
        graph.add("SACRAMENTO", "PORTLAND", 90);
        graph.add("PORTLAND", "SLC", 185);
        graph.add("OAKLAND", "SACRAMENTO", 45);
        graph.add("PORTLAND", "OAKLAND", 100);
        graph.add("SLC", "OAKLAND", 150);
        graph.add("LAX", "OAKLAND", 75);
        graph.add("SLC", "LAS VEGAS", 100);
        graph.add("LAS VEGAS", "CHICAGO", 250);

        System.out.println("Graph is connected: " + graph.DepthFirstSearch());
        System.out.println("Connected from LAX:" + graph.BreadthFirstSearch("LAX"));
        System.out.println();

        System.out.println(graph);
        System.out.println(graph.edgesToString());

        System.out.println("LAX to PORTLAND");
        graph.getPath("LAX", "PORTLAND").print();
    }
}