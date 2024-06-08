package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.comparators.Routes;

import java.util.*;

public class DijkstraAlgorithm {

    // method to calculate the shortest route using Dijkstra's algorithm
    public static List<Routes> dijkstraCalculateShorterRoute(int[][] costMatrix, List<Integer> assemblyPoints, List<String> verticeNames) {
        List<Routes> routes = new ArrayList<>(); // List to store final routes
        int counter = 0; // Counter to keep track of current vertex

        // Iterate through each vertex
        for (String vertex : verticeNames) {
            if (!assemblyPoints.contains(counter)) { // If the vertex is not an assembly point
                int[] routeCost = new int[verticeNames.size()]; // Array to store costs to reach each vertex
                int[] visitedVertices = new int[verticeNames.size()]; // Array to track visited vertices
                int[] parent = new int[verticeNames.size()]; // Array to store the parent of each vertex in the shortest path

                // Run the Dijkstra algorithm for the current vertex
                dijkstraAlgorithmRun(counter, costMatrix, routeCost, visitedVertices, parent);

                // Find the closest assembly point from the current vertex
                int closerAssemblyPoint = findCloserAssemblyPoint(assemblyPoints, routeCost, visitedVertices);

                // If a valid assembly point is found
                if (closerAssemblyPoint != -1) {

                    // Get the route from the current vertex to the closest assembly point
                    List<Routes> route = getRoute(counter, closerAssemblyPoint, parent, verticeNames);

                    // Convert the route into a string
                    String routePoints = calculateRoute(route);

                    // Create a new Routes object and add it to the list of routes
                    Routes finalRoute = new Routes(vertex, routePoints, routeCost[closerAssemblyPoint]);
                    routes.add(finalRoute);
                }
            }
            counter++; // Move to the next vertex
        }
        return routes; // Return the list of routes
    }

    // Method to run Dijkstra's algorithm
    private static void dijkstraAlgorithmRun(int counter, int[][] costMatrix, int[] routeCost, int[] visitedVertices, int[] parent) {

        // Initialize the arrays
        for (int i = 0; i < routeCost.length; i++) {
            routeCost[i] = Integer.MAX_VALUE; // Set all route costs to infinity
            visitedVertices[i] = 0; // Mark all vertices as unvisited
            parent[i] = -1; // Set all parents to -1 (no parent)
        }

        routeCost[counter] = 0; // Set the cost to reach the starting vertex to 0

        // Loop to process each vertex
        for (int i = 0; i < routeCost.length; i++) {
            int closerVertex = -1;
            int closerCost = Integer.MAX_VALUE;

            // Find the closest unvisited vertex
            for (int j = 0; j < routeCost.length; j++) {
                if (visitedVertices[j] == 0 && routeCost[j] < closerCost) {
                    closerVertex = j;
                    closerCost = routeCost[j];
                }
            }

            // If no closest vertex is found, break the loop
            if (closerVertex == -1) {
                break;
            }
            visitedVertices[closerVertex] = 1; // Mark the closest vertex as visited

            // Update the costs and parents for the neighbors of the closest vertex
            for (int j = 0; j < routeCost.length; j++) {
                if (visitedVertices[j] == 0 && costMatrix[closerVertex][j] != 0 && routeCost[closerVertex] != Integer.MAX_VALUE && routeCost[closerVertex] + costMatrix[closerVertex][j] < routeCost[j]) {
                    routeCost[j] = routeCost[closerVertex] + costMatrix[closerVertex][j];
                    parent[j] = closerVertex;
                }
            }
        }
    }

    // Method to find the closest assembly point
    private static int findCloserAssemblyPoint(List<Integer> assemblyPoints, int[] routeCost, int[] visitedVertices) {
        int closerAssemblyPoint = -1;
        int closerCost = Integer.MAX_VALUE;

        // Loop through each vertex
        for (int i = 0; i < routeCost.length; i++) {

            // If the vertex is an assembly point and has been visited, and its cost is the lowest found so far
            if (assemblyPoints.contains(i) && visitedVertices[i] == 1 && routeCost[i] < closerCost) {
                closerAssemblyPoint = i;
                closerCost = routeCost[i];
            }
        }
        return closerAssemblyPoint; // Return the closest assembly point
    }

    // method to construct the route from the parent array
    private static List<Routes> getRoute(int counter, int closerAssemblyPoint, int[] parent, List<String> verticeNames) {
        List<Routes> route = new ArrayList<>();
        int current = closerAssemblyPoint;

        // Loop to construct the route by following the parent array
        while (current != -1) {
            route.add(new Routes(verticeNames.get(current), verticeNames.get(current), 0)); // Assuming cost is not needed here
            current = parent[current]; // Move to the parent vertex
        }
        Collections.reverse(route); // Reverse the route to get the correct order
        return route; // Return the constructed route
    }

    // Method to convert the route to a string
    private static String calculateRoute(List<Routes> route) {
        StringBuilder routePoints = new StringBuilder();

        // Loop through each route and append the route points to the string builder
        for (Routes r : route) {
            routePoints.append(r.getRoute()).append(", ");
        }
        return routePoints.toString().trim(); // Return the route string
    }
}
