package pt.ipp.isep.dei.g312.domain.comparators;

public class Routes {
    String startingPoint;
    String route;
    int cost;

    public Routes (String startingPoint, String route, int cost) {
        this.startingPoint = startingPoint;
        this.route = route;
        this.cost = cost;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getRoute() {
        return route;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Starting Point: " + startingPoint + " Route: " + route + " Cost: " + cost;
    }
}
