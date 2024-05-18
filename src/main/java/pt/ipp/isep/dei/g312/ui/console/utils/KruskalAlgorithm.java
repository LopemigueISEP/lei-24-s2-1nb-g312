package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.CSVLine;

import java.util.ArrayList;
import java.util.List;

public class KruskalAlgorithm {

    // bubble sort edges by cost
    public static void sortEdges(List<CSVLine> edges) {
        for (int i = 0; i < edges.size() - 1; i++) {
            for (int j = 0; j < edges.size() - i - 1; j++) {
                if (edges.get(j).getCost() > edges.get(j + 1).getCost()) {
                    CSVLine temporaryEdge = edges.get(j);
                    edges.set(j, edges.get(j + 1));
                    edges.set(j + 1, temporaryEdge);
                }
            }
        }
    }

    public static List<CSVLine> runKruskalAlgorithm(ArrayList<String> vertices, List<CSVLine> edges) {
        sortEdges(edges); //sort the edges by cost
        int[] group = new int[vertices.size()]; // Array to store which group each vertex belongs to
        for (int i = 0; i < vertices.size(); i++) { // Initialize group array
            group[i] = i;  // Initially, each vertex is in its own group
        }

        List<CSVLine> mst = new ArrayList<>(); //initialize the list of edges in the minimum spanning tree
        for (CSVLine edge : edges) { // Iterate through all edges in ascending order of cost
            int group1 = group[findVertexIndex(edge.getX(), vertices)]; //finds group of starting vertices
            int group2 = group[findVertexIndex(edge.getY(), vertices)]; //finds group of ending vertices

            if (group1 != group2) { //check if the two vertices are in the same group, to avoid cycles, and add edge to the minimum spanning tree
                mst.add(edge); //add edge to the minimum spanning tree
                for (int k = 0; k < group.length; k++) { //update the group of all vertices in the same group as the ending vertex
                    if (group[k] == group2) {
                        group[k] = group1;
                    }
                }
            }
        }
        return mst;
    }

    //returns the index position of a vertex in the list.
    private static int findVertexIndex(String vertex, ArrayList<String> vertices) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(vertex)) {
                return i;
            }
        }
        return -1;
    }
}
