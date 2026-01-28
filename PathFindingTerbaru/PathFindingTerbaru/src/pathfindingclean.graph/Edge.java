package pathfindingclean.graph;

public class Edge {
    public final Node to;
    public final double cost;

    public Edge(Node to, double cost) {
        this.to = to;
        this.cost = cost;
    }
}
