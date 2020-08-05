package com.btp.dataStructures.graphs;

public class Test {
    public static void main(String[] args) {

        // create graph
        MyGraph<String> graph = new MyGraph<>();

        // add a bunch of edges
        graph.add("Moravia", "SanPeter", 150);
        graph.add("SanPeter", "Sabana", 135);
        graph.add("Sabana", "Caja", 120);
        graph.add("Caja", "Curridabat", 175);
        graph.add("Curridabat", "Tres Rios", 160); // edge already exists!!!
        graph.add("Curridabat", "Zapote", 90);
        graph.add("Zapote", "Los Yoses", 185);

        System.out.println("Graph is connected: " + graph.DepthFirstSearch());
        System.out.println("Connected from Zapote:" + graph.BreadthFirstSearch("Zapote"));
        System.out.println();

        System.out.println(graph);
        System.out.println(graph.edgesToString());

        System.out.println("Moravia to Zapote");
        graph.getPath("Moravia", "Zapote").print();
    }
}