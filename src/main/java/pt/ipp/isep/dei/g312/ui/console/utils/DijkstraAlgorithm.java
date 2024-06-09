package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.comparators.Routes;

import java.util.*;

public class DijkstraAlgorithm {

    public static List<Routes> dijkstraCalculateShorterRoute(int[][] costMatrix, List<Integer> assemblyPoints, List<String> verticeNames) {
        List<Routes> routes = new ArrayList<>();
        int counter = 0;
        for (String vertex : verticeNames) {
            if (!assemblyPoints.contains(counter)) {
                int[] routeCost = new int[verticeNames.size()];
                int[] visitedVertices = new int[verticeNames.size()];
                int[] parent = new int[verticeNames.size()];
                dijkstraAlgorithmRun(counter, costMatrix, routeCost, visitedVertices, parent);

                int closerAssemblyPoint = findCloserAssemblyPoint(assemblyPoints, routeCost, visitedVertices);

                if (closerAssemblyPoint != -1) {
                    List<Routes> route = getRoute(counter, closerAssemblyPoint, parent, verticeNames);
                    String routePoints = calculateRoute(route);

                    Routes finalRoute = new Routes(vertex, routePoints, routeCost[closerAssemblyPoint]);
                    routes.add(finalRoute);
                }
            }
            counter++;
        }
        return routes;
    }

    private static void dijkstraAlgorithmRun(int counter, int[][] costMatrix, int[] routeCost, int[] visitedVertices, int[] parent) {
        for (int i = 0; i < routeCost.length; i++) {
            routeCost[i] = Integer.MAX_VALUE;
            visitedVertices[i] = 0;
            parent[i] = -1;
        }
        routeCost[counter] = 0;
        for (int i = 0; i < routeCost.length; i++) {
            int closerVertex = -1;
            int closerCost = Integer.MAX_VALUE;
            for (int j = 0; j < routeCost.length; j++) {
                if (visitedVertices[j] == 0 && routeCost[j] < closerCost) {
                    closerVertex = j;
                    closerCost = routeCost[j];
                }
            }
            if (closerVertex == -1) {
                break;
            }
            visitedVertices[closerVertex] = 1;
            for (int j = 0; j < routeCost.length; j++) {
                if (visitedVertices[j] == 0 && costMatrix[closerVertex][j] != 0 && routeCost[closerVertex] != Integer.MAX_VALUE && routeCost[closerVertex] + costMatrix[closerVertex][j] < routeCost[j]) {
                    routeCost[j] = routeCost[closerVertex] + costMatrix[closerVertex][j];
                    parent[j] = closerVertex;
                }
            }
        }
    }

    private static int findCloserAssemblyPoint(List<Integer> assemblyPoints, int[] routeCost, int[] visitedVertices) {
        int closerAssemblyPoint = -1;
        int closerCost = Integer.MAX_VALUE;
        for (int i = 0; i < routeCost.length; i++) {
            if (assemblyPoints.contains(i) && visitedVertices[i] == 1 && routeCost[i] < closerCost) {
                closerAssemblyPoint = i;
                closerCost = routeCost[i];
            }
        }
        return closerAssemblyPoint;
    }

    private static List<Routes> getRoute(int counter, int closerAssemblyPoint, int[] parent, List<String> verticeNames) {
        List<Routes> route = new ArrayList<>();
        int current = closerAssemblyPoint;
        while (current != -1) {
            route.add(new Routes(verticeNames.get(current), verticeNames.get(current), 0)); // Assuming cost is not needed here
            current = parent[current];
        }
        Collections.reverse(route);
        return route;
    }

    private static String calculateRoute(List<Routes> route) {
        StringBuilder routePoints = new StringBuilder();
        for (Routes r : route) {
            routePoints.append(r.getRoute()).append(", ");
        }
        return routePoints.toString().trim();
    }
}
